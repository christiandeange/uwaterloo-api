package com.deange.uwaterlooapi.model.poi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Photosphere
    extends BasicPointOfInterest
    implements
    Parcelable {

  @SerializedName("url")
  String mUrl;

  protected Photosphere(final Parcel in) {
    super(in);
    mUrl = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mUrl);
  }

  public static final Creator<Photosphere> CREATOR = new Creator<Photosphere>() {
    @Override
    public Photosphere createFromParcel(final Parcel in) {
      return new Photosphere(in);
    }

    @Override
    public Photosphere[] newArray(final int size) {
      return new Photosphere[size];
    }
  };

  /**
   * The URL of the photosphere
   */
  public String getUrl() {
    return mUrl;
  }
}
