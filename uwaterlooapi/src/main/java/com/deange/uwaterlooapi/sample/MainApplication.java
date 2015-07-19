package com.deange.uwaterlooapi.sample;

import android.app.Application;
import android.util.Log;

import com.deange.uwaterlooapi.utils.CollectionsPolicy;

public class MainApplication extends Application {

    private static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "onCreate()");

        // For response objects that contain a collection,
        // ensure they return unmodifiable copies of the data so the underlying model is immutable
        CollectionsPolicy.setPolicy(CollectionsPolicy.UNMODIFIABLE);
    }
}
