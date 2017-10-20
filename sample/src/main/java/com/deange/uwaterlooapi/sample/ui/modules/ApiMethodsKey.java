package com.deange.uwaterlooapi.sample.ui.modules;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.ModuleKey;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.Screen;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.ScreenProvider;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class ApiMethodsKey
    extends ModuleKey
    implements
    Parcelable,
    ScreenProvider {

  public static ApiMethodsKey create(final List<String> endpoints) {
    return new AutoValue_ApiMethodsKey(endpoints);
  }

  abstract List<String> endpoints();

  @Override
  @NonNull
  public Screen screen() {
    return new ApiMethodsFragment();
  }

  @Override
  public int layout() {
    return R.layout.fragment_api_methods;
  }
}
