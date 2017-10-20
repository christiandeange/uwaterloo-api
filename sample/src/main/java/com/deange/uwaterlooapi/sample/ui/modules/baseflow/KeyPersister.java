package com.deange.uwaterlooapi.sample.ui.modules.baseflow;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import flow.KeyParceler;

public class KeyPersister
    implements
    KeyParceler {

  @NonNull
  @Override
  public Parcelable toParcelable(@NonNull final Object key) {
    return (Parcelable) key;
  }

  @NonNull
  @Override
  public Object toKey(@NonNull final Parcelable parcelable) {
    return parcelable;
  }

}
