package com.deange.uwaterlooapi.model;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class BaseModel
    extends AbstractModel
    implements
    Parcelable {

  public BaseModel() {
  }

  protected BaseModel(final Parcel in) {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
  }
}
