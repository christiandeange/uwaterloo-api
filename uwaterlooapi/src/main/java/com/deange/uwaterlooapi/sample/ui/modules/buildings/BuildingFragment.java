package com.deange.uwaterlooapi.sample.ui.modules.buildings;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import com.deange.uwaterlooapi.sample.ui.MapActivity;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.PropertyLayout;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class BuildingFragment extends BaseModuleFragment<Response.BuildingEntity, Building> implements GoogleMap.OnMapClickListener {

    public static final String TAG = BuildingFragment.class.getSimpleName();
    public static final String ARG_BUILDING_CODE = "building_code";

    private SupportMapFragment mMapFragment;
    private ViewGroup mRoot;
    private Building mBuilding;

    public static Bundle newBundle(final String buildingCode) {
        Bundle args = new Bundle();
        args.putString(ARG_BUILDING_CODE, buildingCode);
        return args;
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        mRoot = (ViewGroup) inflater.inflate(R.layout.fragment_building, null);

        PropertyLayout.resize((ViewGroup) mRoot.findViewById(R.id.property_parent));

        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentByTag(TAG);
        if (mMapFragment != null) {
            transaction.remove(mMapFragment);
        }

        mMapFragment = SupportMapFragment.newInstance();
        transaction.add(R.id.building_map, mMapFragment, TAG).commit();

        MapsInitializer.initialize(getActivity());

        return mRoot;
    }

    @Override
    public Response.BuildingEntity onLoadData(final UWaterlooApi api) {
        return api.Buildings.getBuilding(getArguments().getString(ARG_BUILDING_CODE));
    }

    @Override
    public void onBindData(final Metadata metadata, final Building data) {
        mBuilding = data;

        ((TextView) mRoot.findViewById(R.id.building_name)).setText(data.getBuildingName());
        ((TextView) mRoot.findViewById(R.id.building_code)).setText(data.getBuildingCode());
        ((TextView) mRoot.findViewById(R.id.building_id)).setText(data.getBuildingId());
        ((TextView) mRoot.findViewById(R.id.building_alternate_names)).setText(
                Joiner.on('\n').join(data.getAlternateNames()));

        showLocation();
    }

    private void showLocation() {
        final float[] location = mBuilding.getLocation();
        final LatLng buildingLocation = new LatLng(location[0], location[1]);
        final GoogleMap map = mMapFragment.getMap();

        if (map == null) {
            // Ugly hack
            new UpdateMapTask().execute();

        } else {
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
    public void onMapClick(final LatLng latLng) {
        startActivity(MapActivity.getMapActivityIntent(getActivity(), mBuilding));
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

    private final class UpdateMapTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(final Void... voids) {

            int count = 0;

            try {
                // Retry for 5s at most, every 500ms
                while (mMapFragment.getMap() == null && count < 10) {
                    count++;
                    Thread.sleep(500);
                }
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

            return count;
        }

        @Override
        protected void onPostExecute(final Integer waitPeriods) {
            Log.v(TAG, "Map (may have) loaded after " + waitPeriods + " wait period(s)");
            if (getActivity() != null) {
                showLocation();
            }
        }

    }
}
