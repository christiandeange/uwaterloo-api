package com.deange.uwaterlooapi.sample.ui.modules.baseflow;

import android.os.Parcelable;
import android.support.annotation.Nullable;

public abstract class ModuleKey<T extends Parcelable> extends Key {

  @Nullable
  public abstract T model();

}
