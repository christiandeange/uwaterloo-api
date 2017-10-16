package com.deange.uwaterlooapi.model.poi;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public abstract class BasicPointOfInterest
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("name")
  String mName;

  @SerializedName("description")
  String mDescription;

  @SerializedName("note")
  String mNote;

  @SerializedName("latitude")
  float mLatitude;

  @SerializedName("longitude")
  float mLongitude;

  protected BasicPointOfInterest(final Parcel in) {
    super(in);
    mName = in.readString();
    mDescription = in.readString();
    mNote = in.readString();
    mLatitude = in.readFloat();
    mLongitude = in.readFloat();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mName);
    dest.writeString(mDescription);
    dest.writeString(mNote);
    dest.writeFloat(mLatitude);
    dest.writeFloat(mLongitude);
  }

  /**
   * Name of the location
   */
  public String getName() {
    return mName;
  }

  /**
   * Location description
   */
  public String getDescription() {
    return mDescription;
  }

  /**
   * Any additional notes
   */
  public String getNote() {
    return mNote;
  }

  /**
   * Location [latitude, longitude] coordinates of the location
   */
  public float[] getLocation() {
    return new float[]{mLatitude, mLongitude};
  }

}
