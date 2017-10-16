package com.deange.uwaterlooapi.model.resources;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GooseNest
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("id")
  String mId;

  @SerializedName("location")
  String mLocationDescription;

  @SerializedName("latitude")
  float mLatitude;

  @SerializedName("longitude")
  float mLongitude;

  @SerializedName("updated")
  String mUpdated;

  protected GooseNest(final Parcel in) {
    super(in);
    mId = in.readString();
    mLocationDescription = in.readString();
    mLatitude = in.readFloat();
    mLongitude = in.readFloat();
    mUpdated = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mId);
    dest.writeString(mLocationDescription);
    dest.writeFloat(mLatitude);
    dest.writeFloat(mLongitude);
    dest.writeString(mUpdated);
  }

  public static final Creator<GooseNest> CREATOR = new Creator<GooseNest>() {
    @Override
    public GooseNest createFromParcel(final Parcel in) {
      return new GooseNest(in);
    }

    @Override
    public GooseNest[] newArray(final int size) {
      return new GooseNest[size];
    }
  };

  /**
   * Goose Nest ID
   */
  public String getId() {
    return mId;
  }

  /**
   * Human-readable description of goose nest location
   */
  public String getLocationDescription() {
    return mLocationDescription;
  }

  /**
   * Latitude + longitude of goose nest location
   */
  public float[] getLocation() {
    return new float[]{mLatitude, mLongitude};
  }

  /**
   * ISO 8601 time-stamp of last update
   */
  public Date getUpdatedDate() {
    return DateUtils.parseDate(mUpdated);
  }

  /**
   * ISO 8601 time-stamp of last update as a string
   */
  public String getRawUpdatedDate() {
    return mUpdated;
  }
}
