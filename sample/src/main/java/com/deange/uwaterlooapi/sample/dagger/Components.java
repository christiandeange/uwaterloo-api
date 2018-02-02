package com.deange.uwaterlooapi.sample.dagger;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.deange.uwaterlooapi.sample.MainApplication;

public class Components {

  private Components() {
    throw new AssertionError();
  }

  public static AppComponent component(final View view) {
    return component(view.getContext());
  }

  public static AppComponent component(final Fragment fragment) {
    return component(fragment.getContext());
  }

  public static AppComponent component(final Context context) {
    return ((MainApplication) context.getApplicationContext()).getAppComponent();
  }
}
