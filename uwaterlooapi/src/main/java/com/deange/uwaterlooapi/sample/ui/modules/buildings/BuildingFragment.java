package com.deange.uwaterlooapi.sample.ui.modules.buildings;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.FragmentInfo;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BuildingFragment extends BaseModuleFragment<Response.BuildingEntity, Building> {

    public static final String ARG_BUILDING_CODE = "building_code";

    private TextView mBuildingNameView;
    private TextView mBuildingCodeView;
    private ViewGroup mMapLayout;
    private SupportMapFragment mMapFragment;
    private Building mBuilding;

    public static Bundle newBundle(final String buildingCode) {
        Bundle args = new Bundle();
        args.putString(ARG_BUILDING_CODE, buildingCode);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_building, null);

        mBuildingNameView = (TextView) root.findViewById(R.id.building_name);
        mBuildingCodeView = (TextView) root.findViewById(R.id.building_code);
        mMapLayout = (ViewGroup) root.findViewById(R.id.building_map);

        showMap();

        return root;
    }

    @Override
    public Response.BuildingEntity onLoadData(final UWaterlooApi api) {
        return api.Buildings.getBuilding(getArguments().getString(ARG_BUILDING_CODE));
    }

    @Override
    public void onBindData(final Metadata metadata, final Building data) {
        mBuilding = data;
        mBuildingNameView.setText(mBuilding.getBuildingName());
        mBuildingCodeView.setText(mBuilding.getBuildingCode());
        mMapLayout.setVisibility(View.VISIBLE);

        showMap();
        final GoogleMap map = mMapFragment.getMap();

        final float[] location = mBuilding.getLocation();
        final LatLng buildingLocation = new LatLng(location[0], location[1]);

        map.setMyLocationEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(buildingLocation, 17));
        map.addMarker(new MarkerOptions()
                .title(mBuilding.getBuildingName())
                .snippet(mBuilding.getBuildingCode())
                .position(buildingLocation));
    }

    private void showMap() {

        if (mMapFragment != null) {
            return;
        }

        final GoogleMapOptions options = new GoogleMapOptions()
                .mapType(GoogleMap.MAP_TYPE_HYBRID);
        mMapFragment = SupportMapFragment.newInstance(options);

        getChildFragmentManager()
                .beginTransaction()
                .add(mMapLayout.getId(), mMapFragment)
                .commit();
    }

    @Override
    public FragmentInfo getFragmentInfo(final Context context) {
        return new Info(context);
    }

    public final class Info extends FragmentInfo {

        public Info(final Context context) {
            super(context);
        }

        @Override
        public String getActionBarTitle() {
            return getContext().getString(R.string.api_buildings);
        }
    }
}
