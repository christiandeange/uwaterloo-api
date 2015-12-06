package com.deange.uwaterlooapi.sample.ui.modules.buildings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.MapActivity;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

@ModuleFragment(path = "/buildings/*")
public class BuildingFragment
        extends BaseMapFragment<Response.BuildingEntity, Building> {

    public static final String TAG = BuildingFragment.class.getSimpleName();

    private View mEmptyView;
    private ViewGroup mRoot;

    private Building mBuilding;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        mRoot = (ViewGroup) inflater.inflate(R.layout.fragment_building, null);

        mEmptyView = mRoot.findViewById(R.id.building_empty_view);

        return mRoot;
    }

    @Override
    public Building onLoadData() {
        return getModel();
    }

    @Override
    public void onBindData(final Metadata metadata, final Building data) {
        mBuilding = data;

        ((TextView) mRoot.findViewById(R.id.building_name)).setText(data.getBuildingName());

        if (mMapView.getMap() != null) {
            showLocation();
        }
    }

    @Override
    public String getContentType() {
        return ModuleType.BUILDING;
    }

    private void showLocation() {
        if (mBuilding == null) {
            return;
        }

        final float[] location = mBuilding.getLocation();

        if (location[0] == 0 && location[1] == 0) {
            mMapView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);

        } else {
            mMapView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);

            final LatLng buildingLocation = new LatLng(location[0], location[1]);
            final GoogleMap map = mMapView.getMap();

            map.clear();
            map.setIndoorEnabled(false);
            map.setMyLocationEnabled(false);
            map.setOnMapClickListener(this);
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            map.getUiSettings().setAllGesturesEnabled(false);
            map.getUiSettings().setZoomControlsEnabled(false);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(buildingLocation, 18));
        }
    }

    @Override
    public int getMapViewId() {
        return R.id.building_map;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        showLocation();
    }

    @Override
    public void onMapClick(final LatLng latLng) {
        startActivity(MapActivity.getMapActivityIntent(getActivity(), mBuilding));
    }
}
