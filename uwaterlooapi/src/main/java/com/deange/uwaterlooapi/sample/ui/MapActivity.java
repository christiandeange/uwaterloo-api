package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.buildings.BuildingSection;
import com.deange.uwaterlooapi.sample.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MapActivity
        extends BaseActivity
        implements
        AdapterView.OnItemSelectedListener {

    private static final String TAG = MapActivity.class.getSimpleName();
    private static final String KEY_BUILDING = "building";

    private MapView mMapView;

    public static Intent getMapActivityIntent(final Context from, final Building building) {
        final Intent intent = new Intent(from, MapActivity.class);
        intent.putExtra(KEY_BUILDING, Parcels.wrap(building));
        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mMapView = (MapView) findViewById(R.id.building_map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                final Building building = getBuilding();
                showLocation(building.getBuildingName(), building.getLocation());
            }
        });

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
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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
        final GoogleMap map = mMapView.getMap();

        map.clear();
        map.setMyLocationEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(buildingLocation, 17));
        map.addMarker(new MarkerOptions()
                .title(buildingName)
                .position(buildingLocation));
    }

    private Building getBuilding() {
        return Parcels.unwrap(getIntent().getParcelableExtra(KEY_BUILDING));
    }

}
