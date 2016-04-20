package com.deange.uwaterlooapi.sample.ui.modules.poi;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.poi.ATM;
import com.deange.uwaterlooapi.model.poi.BasicPointOfInterest;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedPointsOfInterestInfo;
import com.deange.uwaterlooapi.sample.model.CombinedPointsOfInterestInfoResponse;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseMapFragment;
import com.deange.uwaterlooapi.sample.utils.LocationUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import butterknife.Bind;
import butterknife.ButterKnife;

@ModuleFragment(
        path = "/poi/atms",
        layout = R.layout.module_poi
)
public class PointsOfInterestFragment
        extends BaseMapFragment<CombinedPointsOfInterestInfoResponse, CombinedPointsOfInterestInfo>
        implements
        GoogleMap.OnMarkerClickListener, LayersDialog.OnLayersSelectedListener {

    private static final String TAG = "PointsOfInterestFragment";

    private static final int BEST_SIZE = Runtime.getRuntime().availableProcessors() * 2 - 1;
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(BEST_SIZE);

    @Bind(R.id.points_of_interest_info) ViewGroup mInfoRoot;

    private CombinedPointsOfInterestInfo mResponse;
    private int mFlags = LayersDialog.FLAG_ALL;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_points_of_interest, null);

        ButterKnife.bind(this, view);

        mInfoRoot.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_poi_layers, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_layers) {
            LayersDialog.showDialog(getContext(), mFlags, this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLayersSelected(final int flags) {
        mFlags = flags;
        showPointsOfInterestInfo();
    }

    @Override
    public CombinedPointsOfInterestInfoResponse onLoadData(final UWaterlooApi api) {
        final CombinedPointsOfInterestInfo info = new CombinedPointsOfInterestInfo();
        final CombinedPointsOfInterestInfoResponse response = new CombinedPointsOfInterestInfoResponse(info);
        final Semaphore semaphore = new Semaphore(1 - /* TODO */ 1);

        // ATMs
        fetchPointOfInterestInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                info.setATMs(api.PointsOfInterest.getATMs().getData());
            }
        });

        try {
            // Wait until all data is loaded
            semaphore.acquire();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    @Override
    public void onBindData(final Metadata metadata, final CombinedPointsOfInterestInfo data) {
        mResponse = data;

        if (mMapView.getMap() != null) {
            showPointsOfInterestInfo();
        }
    }

    private void showPointsOfInterestInfo() {
        final GoogleMap map = mMapView.getMap();
        map.clear();

        if ((mFlags & LayersDialog.FLAG_ATM) != 0) {
            for (final ATM atm : mResponse.getATMs()) {
                map.addMarker(new MarkerOptions()
                        .position(LocationUtils.getLatLng(atm.getLocation()))
                        .icon(BitmapDescriptorFactory.fromResource(getIcon(atm)))
                        .title(atm.getName()));
            }
        }
    }

    @Override
    protected void onRefreshRequested() {
        hideInfoView();
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        onMapClick(latLng);
    }

    @Override
    public void onMapClick(final LatLng latLng) {
        hideInfoView();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        marker.showInfoWindow();

        for (final ATM atm : mResponse.getATMs()) {
            if (LocationUtils.getLatLng(atm.getLocation()).equals(marker.getPosition())) {
                onPointOfInterestInfoRequested(atm);
                return true;
            }
        }

        return false;
    }

    private void hideInfoView() {
        if (mInfoRoot.getVisibility() == View.VISIBLE) {
            final Animation animOut = AnimationUtils.loadAnimation(getContext(), R.anim.top_out);
            animOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(final Animation animation) {
                }

                @Override
                public void onAnimationRepeat(final Animation animation) {
                }

                @Override
                public void onAnimationEnd(final Animation animation) {
                    if (mInfoRoot != null) {
                        mInfoRoot.setVisibility(View.GONE);
                    }
                }
            });
            mInfoRoot.startAnimation(animOut);
        }
    }

    private void onPointOfInterestInfoRequested(final BasicPointOfInterest poi) {
        if (mInfoRoot.getVisibility() == View.GONE) {
            final Animation animIn = AnimationUtils.loadAnimation(getContext(), R.anim.top_in);
            mInfoRoot.startAnimation(animIn);
            mInfoRoot.setVisibility(View.VISIBLE);
        }

        final String title = poi.getName();
        String description = poi.getDescription();
        if (TextUtils.isEmpty(description)) {
            description = poi.getNote();
        }

        ((TextView) mInfoRoot.findViewById(android.R.id.text1)).setText(title);
        ((TextView) mInfoRoot.findViewById(android.R.id.text2)).setText(description);
    }

    private void fetchPointOfInterestInfo(
            final Semaphore semaphore,
            final InfoFetcher fetcher) {

        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    fetcher.fetch();
                } finally {
                    semaphore.release();
                }
            }
        });

    }

    private int getIcon(final BasicPointOfInterest poi) {
        if (poi instanceof ATM) {
            final ATM atm = (ATM) poi;
            final String name = atm.getName();
            if (name.equalsIgnoreCase("CIBC")) {
                return R.drawable.ic_bank_cibc;
            } else if (name.equalsIgnoreCase("BMO")) {
                return R.drawable.ic_bank_bmo;
            } else if (name.equalsIgnoreCase("TD")) {
                return R.drawable.ic_bank_td;
            } else if (name.equalsIgnoreCase("RBC")) {
                return R.drawable.ic_bank_rbc;
            } else if (name.equalsIgnoreCase("Scotiabank")) {
                return R.drawable.ic_bank_scotiabank;
            } else {
                return R.drawable.ic_local_atm;
            }
        } else {
            return R.drawable.cloud_off;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        final LatLngBounds.Builder builder = LatLngBounds.builder();
        builder.include(new LatLng(43.473655, -80.556242));
        builder.include(new LatLng(43.465495, -80.537446));

        final LatLngBounds bounds = builder.build();
        final int padding = (int) (16 * getResources().getDisplayMetrics().density);

        map.setIndoorEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnMapLongClickListener(this);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

        if (mResponse != null) {
            showPointsOfInterestInfo();
        }
    }

    @Override
    public String getContentType() {
        return ModuleType.POI;
    }

    @Override
    public int getMapViewId() {
        return R.id.points_of_interest_map;
    }

    private interface InfoFetcher {
        void fetch();
    }

}
