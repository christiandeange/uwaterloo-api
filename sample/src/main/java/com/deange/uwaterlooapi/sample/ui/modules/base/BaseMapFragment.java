package com.deange.uwaterlooapi.sample.ui.modules.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

public abstract class BaseMapFragment<T extends BaseResponse, V extends BaseModel>
        extends BaseModuleFragment<T, V>
        implements
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener {

    private static final String KEY_MAP_BUNDLE = "map_state";

    protected MapView mMapView;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        if (view != null) {
            final Bundle mapViewBundle = savedInstanceState != null
                    ? savedInstanceState.getBundle(KEY_MAP_BUNDLE)
                    : null;

            mMapView = (MapView) getView().findViewById(getMapViewId());
            mMapView.onCreate(mapViewBundle);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
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
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        final Bundle mapViewBundle = new Bundle();
        outState.putBundle(KEY_MAP_BUNDLE, mapViewBundle);
        mMapView.onSaveInstanceState(mapViewBundle);
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
    public void onMapLongClick(final LatLng latLng) {
        onMapClick(latLng);
    }

    public abstract @IdRes int getMapViewId();

}
