package com.deange.uwaterlooapi.sample;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.deange.uwaterlooapi.sample.dagger.AppComponent;
import com.deange.uwaterlooapi.sample.dagger.AppScope;
import com.deange.uwaterlooapi.sample.utils.FontUtils;

import net.danlew.android.joda.JodaTimeAndroid;

import mortar.MortarScope;
import pl.tajchert.nammu.Nammu;

import static com.deange.uwaterlooapi.sample.dagger.scoping.MortarContextFactory.createRootScope;

public class MainApplication
    extends MultiDexApplication {

  private static final String TAG = MainApplication.class.getSimpleName();

  private final AppComponent mAppComponent;
  private final MortarScope mRootScope;

  public MainApplication() {
    // Must be done in the constructor since FirebaseService.onCreate()
    // is instantiated before the application goes through onCreate()
    mAppComponent = AppComponent.create(this);
    mRootScope = createRootScope(mAppComponent, AppScope.class.getSimpleName());
  }

  @Override
  public void onCreate() {
    super.onCreate();

    Log.v(TAG, "onCreate()");

    // Initialize the crash reporting wrapper and analytics reporting wrapper
    CrashReporting.init(this);
    Analytics.init(this);

    // Permissions library
    Nammu.init(this);

    // Set up Calligraphy library
    FontUtils.init(this);

    // Joda Time config
    JodaTimeAndroid.init(this);
  }

  @Override
  public Object getSystemService(String name) {
    return mRootScope.hasService(name)
        ? mRootScope.getService(name)
        : super.getSystemService(name);
  }

  public AppComponent getAppComponent() {
    return mAppComponent;
  }
}
