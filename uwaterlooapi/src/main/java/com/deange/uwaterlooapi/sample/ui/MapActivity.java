package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.buildings.BuildingSection;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.GsonController;
import com.deange.uwaterlooapi.sample.utils.Parceller;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = MapActivity.class.getSimpleName();
    private static final String KEY_BUILDING = "building";
    private static final String KEY_OPTIONS = "options";
    private static final String KEY_INDOORS = "indoors";

    private SupportMapFragment mMapFragment;

    public static Intent getMapActivityIntent(final Context from, final Building building) {
        return getMapActivityIntent(from, building, null, false);
    }

    public static Intent getMapActivityIntent(final Context from, final Building building,
                                              final GoogleMapOptions options,
                                              final boolean showIndoorMaps) {
        final Intent intent = new Intent(from, MapActivity.class);
        intent.putExtra(KEY_BUILDING, Parceller.parcel(building));
        intent.putExtra(KEY_OPTIONS, options);
        intent.putExtra(KEY_INDOORS, showIndoorMaps);
        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_map);

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (mMapFragment != null) {
            transaction.remove(mMapFragment);
        }

        final GoogleMapOptions options = getOptions();
        mMapFragment = (options == null)
                ? SupportMapFragment.newInstance()
                : SupportMapFragment.newInstance(options);
        transaction.add(R.id.building_map, mMapFragment, TAG).commit();

        MapsInitializer.initialize(this);

        final Spinner placesView = (Spinner) findViewById(R.id.building_marker_view);
        final Building building = getBuilding();

        if (building == null || building.getBuildingSections().isEmpty()) {
            ((View) placesView.getParent()).setVisibility(View.GONE);

        } else {
            // Add the building itself to the list
            final List<Object> sections = new ArrayList<Object>(building.getBuildingSections());
            sections.add(0, building.getBuildingName());
            final StringAdapter adapter = new StringAdapter(this, sections);
            adapter.setViewLayoutId(android.R.layout.simple_spinner_item);
            placesView.setAdapter(adapter);
            placesView.setOnItemSelectedListener(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Building building = getBuilding();
        showLocation(building.getBuildingName(), building.getLocation());
    }

    @Override
    public void onItemSelected(final AdapterView<?> adapterView, final View view,
                               final int position, final long id) {
        final Building building = getBuilding();
        if (position == 0) {
            showLocation(building.getBuildingName(), building.getLocation());

        } else {
            final BuildingSection section = building.getBuildingSections().get(position - 1);
            showLocation(section.getSectionName(), section.getLocation());
        }
    }

    @Override
    public void onNothingSelected(final AdapterView<?> parent) {
    }

    private void showLocation(final String buildingName, final float[] location) {
        final LatLng buildingLocation = new LatLng(location[0], location[1]);
        final GoogleMap map = mMapFragment.getMap();

        if (map == null) {
            new UpdateMapTask(buildingName, location).execute();

        } else {
            map.clear();
            map.setMyLocationEnabled(false);
            map.setIndoorEnabled(shouldShowIndoors());
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(buildingLocation, 17));
            map.addMarker(new MarkerOptions()
                    .title(buildingName)
                    .position(buildingLocation));
        }
    }

    private Building getBuilding() {
        return Parceller.unparcel(getIntent().getStringExtra(KEY_BUILDING));
    }

    private GoogleMapOptions getOptions() {
        return getIntent().getParcelableExtra(KEY_OPTIONS);
    }

    private boolean shouldShowIndoors() {
        return getIntent().getBooleanExtra(KEY_INDOORS, false);
    }

    private final class UpdateMapTask extends AsyncTask<Void, Void, Integer> {

        private final String mName;
        private final float[] mLocation;

        public UpdateMapTask(final String name, final float[] location) {
            mName = name;
            mLocation = location;
        }

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
            showLocation(mName, mLocation);
        }

    }
}
