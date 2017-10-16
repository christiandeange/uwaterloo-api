package com.deange.uwaterlooapi.model.poi;

import android.os.Parcel;
import android.os.Parcelable;

public class GreyhoundStop
    extends BasicPointOfInterest
    implements
    Parcelable {

  protected GreyhoundStop(final Parcel in) {
    super(in);
  }

  public static final Creator<GreyhoundStop> CREATOR = new Creator<GreyhoundStop>() {
    @Override
    public GreyhoundStop createFromParcel(final Parcel in) {
      return new GreyhoundStop(in);
    }

    @Override
    public GreyhoundStop[] newArray(final int size) {
      return new GreyhoundStop[size];
    }
  };

}
