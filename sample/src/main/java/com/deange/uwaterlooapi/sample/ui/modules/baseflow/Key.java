package com.deange.uwaterlooapi.sample.ui.modules.baseflow;

import android.os.Parcelable;
import android.support.annotation.LayoutRes;

public abstract class Key
    implements
    Parcelable {

  @LayoutRes
  public abstract int layout();

  @Override
  public String toString() {
    final Class<?> clazz = getClass();
    final Class<?> parentClazz = getClass().getEnclosingClass();

    if (parentClazz != null) {
      return parentClazz.getSimpleName() + "." + clazz.getSimpleName();
    } else {
      return clazz.getSimpleName();
    }
  }
}
