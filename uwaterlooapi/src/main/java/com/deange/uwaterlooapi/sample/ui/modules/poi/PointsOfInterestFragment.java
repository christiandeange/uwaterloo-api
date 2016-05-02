package com.deange.uwaterlooapi.sample.ui.modules.poi;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.poi.ATM;
import com.deange.uwaterlooapi.model.poi.BasicPointOfInterest;
import com.deange.uwaterlooapi.model.poi.Defibrillator;
import com.deange.uwaterlooapi.model.poi.GreyhoundStop;
import com.deange.uwaterlooapi.model.poi.Helpline;
import com.deange.uwaterlooapi.model.poi.Library;
import com.deange.uwaterlooapi.model.poi.Photosphere;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedPointsOfInterestInfo;
import com.deange.uwaterlooapi.sample.model.CombinedPointsOfInterestInfoResponse;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseMapFragment;
import com.deange.uwaterlooapi.sample.utils.IntentUtils;
import com.deange.uwaterlooapi.sample.utils.LocationUtils;
import com.deange.uwaterlooapi.sample.utils.MapManager;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import butterknife.Bind;
import butterknife.ButterKnife;

@ModuleFragment(
        path = "/poi",
        layout = R.layout.module_poi
)
public class PointsOfInterestFragment
        extends BaseMapFragment<CombinedPointsOfInterestInfoResponse, CombinedPointsOfInterestInfo>
        implements
        GoogleMap.OnMarkerClickListener,
        LayersDialog.OnLayersSelectedListener {

    private static final String TAG = "PointsOfInterestFragment";

    private static final int BEST_SIZE = Runtime.getRuntime().availableProcessors() * 2 - 1;
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(BEST_SIZE);

    @Bind(R.id.points_of_interest_info) ViewGroup mInfoRoot;
    @Bind(R.id.points_of_interest_info_icon) ImageView mViewInBrowserBton;
    @Bind(android.R.id.text1) TextView mTitle;
    @Bind(android.R.id.text2) TextView mDescription;

    private CombinedPointsOfInterestInfo mResponse;
    private int mFlags = 0;

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
        final View view = inflater.inflate(R.layout.fragment_points_of_interest, parent, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LayersDialog.showDialog(getContext(), mFlags, this);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_poi_layers, menu);
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
        final Semaphore semaphore = new Semaphore(1 - LayersDialog.LAYERS_COUNT);

        // ATMs
        fetchPointOfInterestInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                info.setATMs(api.PointsOfInterest.getATMs().getData());
            }
        });

        // Greyhound stops
        fetchPointOfInterestInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                info.setGreyhounds(api.PointsOfInterest.getGreyhoundStops().getData());
            }
        });

        // Photospheres
        fetchPointOfInterestInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                // Disable Photospheres API due to bug
                info.setPhotospheres(new ArrayList<Photosphere>() /*api.PointsOfInterest.getPhotospheres().getData()*/);
            }
        });

        // Helplines
        fetchPointOfInterestInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                info.setHelplines(api.PointsOfInterest.getHelplines().getData());
            }
        });

        // Libraries
        fetchPointOfInterestInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                info.setLibraries(api.PointsOfInterest.getLibraries().getData());
            }
        });

        // Defibrillators
        fetchPointOfInterestInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                info.setDefibrillators(api.PointsOfInterest.getDefibrillators().getData());
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
        mMapView.getMap().clear();

        addMarkersIfEnabled(mResponse.getATMs(), LayersDialog.FLAG_ATM);
        addMarkersIfEnabled(mResponse.getGreyhounds(), LayersDialog.FLAG_GREYHOUND);
        addMarkersIfEnabled(mResponse.getPhotospheres(), LayersDialog.FLAG_PHOTOSPHERE);
        addMarkersIfEnabled(mResponse.getHelplines(), LayersDialog.FLAG_HELPLINE);
        addMarkersIfEnabled(mResponse.getLibraries(), LayersDialog.FLAG_LIBRARY);
        addMarkersIfEnabled(mResponse.getDefibrillators(), LayersDialog.FLAG_DEFIBRILLATOR);
    }

    private void addMarkersIfEnabled(final List<? extends BasicPointOfInterest> items, final int flag) {
        final GoogleMap map = mMapView.getMap();

        if ((mFlags & flag) != 0) {
            for (final BasicPointOfInterest item : items) {
                map.addMarker(new MarkerOptions()
                        .position(LocationUtils.getLatLng(item.getLocation()))
                        .icon(BitmapDescriptorFactory.fromResource(getIcon(item)))
                        .title(item.getName()));
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

        final LatLng latLng = marker.getPosition();
        final BasicPointOfInterest poi = matchPointByLocation(mResponse.getAllPointsOfInterest(), latLng);
        final String url = getUrl(poi);

        if (url != null) {
            mViewInBrowserBton.setVisibility(View.VISIBLE);
            mViewInBrowserBton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    IntentUtils.openBrowser(getContext(), url);
                }
            });

        } else {
            mViewInBrowserBton.setVisibility(View.GONE);
            mViewInBrowserBton.setOnClickListener(null);
        }

        return (poi != null);
    }

    private <T extends BasicPointOfInterest> T matchPointByLocation(final Iterable<T> items, final LatLng latLng) {
        for (final T poi : items) {
            if (LocationUtils.getLatLng(poi.getLocation()).equals(latLng)) {
                onPointOfInterestInfoRequested(poi);
                return poi;
            }
        }

        return null;
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

        ViewUtils.setText(mTitle, title);
        ViewUtils.setText(mDescription, description);
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
            if ("CIBC".equalsIgnoreCase(name)) {
                return R.drawable.ic_bank_cibc;
            } else if ("BMO".equalsIgnoreCase(name)) {
                return R.drawable.ic_bank_bmo;
            } else if ("TD".equalsIgnoreCase(name)) {
                return R.drawable.ic_bank_td;
            } else if ("RBC".equalsIgnoreCase(name)) {
                return R.drawable.ic_bank_rbc;
            } else if ("Scotiabank".equalsIgnoreCase(name)) {
                return R.drawable.ic_bank_scotiabank;
            } else {
                return R.drawable.ic_local_atm;
            }

        } else if (poi instanceof GreyhoundStop) {
            return R.drawable.ic_poi_greyhound;

        } else if (poi instanceof Photosphere) {
            return R.drawable.ic_poi_photosphere;

        } else if (poi instanceof Helpline) {
            return R.drawable.ic_alert;

        } else if (poi instanceof Library) {
            return R.drawable.ic_poi_library_badge;

        } else if (poi instanceof Defibrillator) {
            return R.drawable.ic_poi_defibrillator;

        } else {
            return R.drawable.ic_about;
        }
    }

    private String getUrl(final BasicPointOfInterest poi) {
        if (poi instanceof Photosphere) {
            return ((Photosphere) poi).getUrl();

        } else if (poi instanceof Library) {
            return poi.getNote();

        } else {
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        final LatLngBounds bounds = LatLngBounds.builder()
                                                .include(new LatLng(43.473655, -80.556242))
                                                .include(new LatLng(43.465495, -80.537446))
                                                .build();

        final int padding = (int) (16 * getResources().getDisplayMetrics().density);

        map.setIndoorEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnMapLongClickListener(this);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

        MapManager.setLocationEnabled(getActivity(), map);

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
