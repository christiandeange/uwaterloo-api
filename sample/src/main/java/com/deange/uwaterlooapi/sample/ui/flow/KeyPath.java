package com.deange.uwaterlooapi.sample.ui.flow;

import android.os.Parcel;
import android.os.Parcelable;
import com.squareup.coordinators.CoordinatorProvider;
import flow.path.Path;

public abstract class KeyPath extends Path
    implements CoordinatorProvider, HasLayout, Parcelable {

  @Override
  public int describeContents() {
    // Default to empty implementation
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    // Default to empty implementation
  }
}
