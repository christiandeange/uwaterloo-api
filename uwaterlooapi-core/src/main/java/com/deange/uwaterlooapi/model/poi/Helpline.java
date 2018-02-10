package com.deange.uwaterlooapi.model.poi;

import android.os.Parcel;
import android.os.Parcelable;

public class Helpline
    extends BasicPointOfInterest
    implements
    Parcelable {

  protected Helpline(final Parcel in) {
    super(in);
  }

  public static final Creator<Helpline> CREATOR = new Creator<Helpline>() {
    @Override
    public Helpline createFromParcel(final Parcel in) {
      return new Helpline(in);
    }

    @Override
    public Helpline[] newArray(final int size) {
      return new Helpline[size];
    }
  };

}
