package com.deange.uwaterlooapi.model.parking;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ParkingLot
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("lot_name")
  String mLotName;

  @SerializedName("latitude")
  float mLatitude;

  @SerializedName("longitude")
  float mLongitude;

  @SerializedName("capacity")
  int mCapacity;

  @SerializedName("current_count")
  int mCurrentCount;

  @SerializedName("percent_filled")
  int mPercentFilled;

  @SerializedName("last_updated")
  String mLastUpdated;

  protected ParkingLot(final Parcel in) {
    super(in);
    mLotName = in.readString();
    mLatitude = in.readFloat();
    mLongitude = in.readFloat();
    mCapacity = in.readInt();
    mCurrentCount = in.readInt();
    mPercentFilled = in.readInt();
    mLastUpdated = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mLotName);
    dest.writeFloat(mLatitude);
    dest.writeFloat(mLongitude);
    dest.writeInt(mCapacity);
    dest.writeInt(mCurrentCount);
    dest.writeInt(mPercentFilled);
    dest.writeString(mLastUpdated);
  }

  public static final Creator<ParkingLot> CREATOR = new Creator<ParkingLot>() {
    @Override
    public ParkingLot createFromParcel(final Parcel in) {
      return new ParkingLot(in);
    }

    @Override
    public ParkingLot[] newArray(final int size) {
      return new ParkingLot[size];
    }
  };

  /**
   * Name of the parking lot
   */
  public String getLotName() {
    return mLotName;
  }

  /**
   * Location [latitude, longitude] coordinates
   */
  public float[] getLocation() {
    return new float[]{mLatitude, mLongitude};
  }

  /**
   * Capacity of the parking lot
   */
  public int getCapacity() {
    return mCapacity;
  }

  /**
   * Current count of the number of cars in the parking lot
   */
  public int getCurrentCount() {
    return mCurrentCount;
  }

  /**
   * Percentage of which the parking lot is filled, rounded to the nearest integer
   */
  public int getPercentFilledRounded() {
    return mPercentFilled;
  }

  /**
   * Percentage of which the parking lot is filled
   */
  public float getPercentFilled() {
    return (float) mCurrentCount / (float) mCapacity;
  }

  /**
   * Time which the `current_count` was last updated
   */
  public Date getLastUpdated() {
    return DateUtils.parseDate(mLastUpdated);
  }

  /**
   * Time which the `current_count` was last updated as a string
   */
  public String getLastUpdatedRaw() {
    return mLastUpdated;
  }
}
