package com.deange.uwaterlooapi.sample.ui.modules.parking;

import android.animation.LayoutTransition;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.parking.ParkingLot;
import com.deange.uwaterlooapi.sample.CrashReporting;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.Colors;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseMapFragment;
import com.deange.uwaterlooapi.sample.utils.DateUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

@ModuleFragment(
        path = "/parking/watpark",
        layout = R.layout.module_parking
)
public class ParkingFragment
        extends BaseMapFragment<Response.Parking, ParkingLot> {

    private static final String TAG = "ParkingFragment";

    @Bind(R.id.parking_lot_info) ViewGroup mInfoRoot;
    @BindColor(R.color.uw_yellow) int mPrimaryColor;

    private List<ParkingLot> mResponse;
    private ParkingLot mSelected;

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
        final View view = inflater.inflate(R.layout.fragment_parking, parent, false);

        ButterKnife.bind(this, view);

        mInfoRoot.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        return view;
    }

    @Override
    public Response.Parking onLoadData(final UWaterlooApi api) {
        return api.Parking.getParkingInfo();
    }

    @Override
    public void onBindData(final Metadata metadata, final List<ParkingLot> data) {
        mResponse = data;

        for (final ParkingLot parkingLot : mResponse) {
            final String lotName = parkingLot.getLotName();
            if (!ParkingLots.isSupported(lotName)) {
                CrashReporting.report(new IllegalArgumentException("Unknown parking lot '" + lotName + "'"));
            }
        }

        if (mMapView.getMap() != null) {
            showLotInfo();
        }
    }

    private void showLotInfo() {
        final GoogleMap map = mMapView.getMap();

        redrawPolygons();

        final LatLngBounds.Builder builder = LatLngBounds.builder();
        for (final ParkingLot parkingLot : mResponse) {
            final List<LatLng> points = ParkingLots.getPoints(parkingLot.getLotName());
            for (final LatLng point : points) {
                builder.include(point);
            }
        }
        final LatLngBounds bounds = builder.build();
        final int padding = (int) (16 * getResources().getDisplayMetrics().density);

        map.setIndoorEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
    }

    private void redrawPolygons() {
        final GoogleMap map = mMapView.getMap();
        map.clear();

        for (final ParkingLot parkingLot : mResponse) {
            final PolygonOptions polygon = ParkingLots.getShape(parkingLot.getLotName());

            final float taken = parkingLot.getPercentFilled();
            if (taken < 0.75f) {
                polygon.fillColor(Colors.mask(0x80, Colors.GREEN_500));
            } else if (taken < 0.90f) {
                polygon.fillColor(Colors.mask(0x80, Colors.YELLOW_500));
            } else {
                polygon.fillColor(Colors.mask(0x80, Colors.RED_500));
            }

            polygon.strokeColor(parkingLot == mSelected
                    ? Colors.mask(0xFF, mPrimaryColor)
                    : Colors.mask(0xFF, Color.BLACK)
            );

            map.addPolygon(polygon);
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
        boolean found = false;
        final float py = (float) latLng.latitude;
        final float px = (float) latLng.longitude;

        mSelected = null;
        for (final ParkingLot parkingLot : mResponse) {
            final GoogleMap map = mMapView.getMap();
            final List<LatLng> points = ParkingLots.getPoints(parkingLot.getLotName());
            if (!found && ParkingLots.isInPoly(px, py, points)) {
                mSelected = parkingLot;
                onParkingLotInfoRequested(parkingLot);
                found = true;
            }
        }

        redrawPolygons();

        // No parking lot clicked on
        if (!found) {
            hideInfoView();
        }
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

    private void onParkingLotInfoRequested(final ParkingLot parkingLot) {
        final LatLngBounds.Builder builder = LatLngBounds.builder();
        final List<LatLng> points = ParkingLots.getPoints(parkingLot.getLotName());
        for (final LatLng point : points) {
            builder.include(point);
        }

        final LatLngBounds bounds = builder.build();
        final int padding = (getResources().getDisplayMetrics().widthPixels / 4);
        mMapView.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

        if (mInfoRoot.getVisibility() == View.GONE) {
            final Animation animIn = AnimationUtils.loadAnimation(getContext(), R.anim.top_in);
            mInfoRoot.startAnimation(animIn);
            mInfoRoot.setVisibility(View.VISIBLE);
        }

        final String lotName = getString(
                R.string.parking_lot_name,
                parkingLot.getLotName());

        final String filledSpots = getString(
                R.string.parking_lot_spots_filled,
                parkingLot.getCurrentCount(),
                parkingLot.getCapacity());

        final String title = lotName + " (" + filledSpots + ")";
        final String date = getString(
                R.string.parking_last_updated,
                DateUtils.getTimeDifference(getResources(), parkingLot.getLastUpdated().getTime()).toLowerCase());

        ((TextView) mInfoRoot.findViewById(android.R.id.text1)).setText(title);
        ((TextView) mInfoRoot.findViewById(android.R.id.text2)).setText(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (mResponse != null) {
            showLotInfo();
        }
    }

    @Override
    public String getContentType() {
        return ModuleType.PARKING;
    }

    @Override
    public int getMapViewId() {
        return R.id.parking_map;
    }
}
