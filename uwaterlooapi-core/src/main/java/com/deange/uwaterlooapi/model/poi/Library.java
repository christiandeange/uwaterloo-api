package com.deange.uwaterlooapi.model.poi;

import android.os.Parcel;
import android.os.Parcelable;

public class Library
    extends BasicPointOfInterest
    implements
    Parcelable {

  protected Library(final Parcel in) {
    super(in);
  }

  public static final Creator<Library> CREATOR = new Creator<Library>() {
    @Override
    public Library createFromParcel(final Parcel in) {
      return new Library(in);
    }

    @Override
    public Library[] newArray(final int size) {
      return new Library[size];
    }
  };

}
