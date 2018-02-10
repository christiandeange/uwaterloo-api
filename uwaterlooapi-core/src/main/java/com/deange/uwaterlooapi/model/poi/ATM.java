package com.deange.uwaterlooapi.model.poi;

import android.os.Parcel;
import android.os.Parcelable;

public class ATM
    extends BasicPointOfInterest
    implements
    Parcelable {

  protected ATM(final Parcel in) {
    super(in);
  }

  public static final Creator<ATM> CREATOR = new Creator<ATM>() {
    @Override
    public ATM createFromParcel(final Parcel in) {
      return new ATM(in);
    }

    @Override
    public ATM[] newArray(final int size) {
      return new ATM[size];
    }
  };
}
