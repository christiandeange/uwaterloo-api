package com.deange.uwaterlooapi.sample;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.deange.uwaterlooapi.sample.utils.FontUtils;
import com.deange.uwaterlooapi.utils.CollectionsPolicy;

import net.danlew.android.joda.JodaTimeAndroid;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends MultiDexApplication {

    private static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "onCreate()");

        // Initialize the crash reporting wrapper and analytics reporting wrapper
        CrashReporting.init(this);
        Analytics.init(this);

        // For response objects that contain a collection,
        // ensure they return unmodifiable copies of the data so the underlying model is immutable
        CollectionsPolicy.setPolicy(CollectionsPolicy.UNMODIFIABLE);

        // Set up Calligraphy library
        FontUtils.init(this);

        // Joda Time config
        JodaTimeAndroid.init(this);
    }
}
