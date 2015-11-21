package com.deange.uwaterlooapi.sample;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.deange.uwaterlooapi.utils.CollectionsPolicy;

import net.danlew.android.joda.JodaTimeAndroid;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends MultiDexApplication {

    private static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "onCreate()");

        // For response objects that contain a collection,
        // ensure they return unmodifiable copies of the data so the underlying model is immutable
        CollectionsPolicy.setPolicy(CollectionsPolicy.UNMODIFIABLE);

        // Set up Calligraphy library
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Gotham-Book.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());

        // Joda Time config
        JodaTimeAndroid.init(this);
    }
}
