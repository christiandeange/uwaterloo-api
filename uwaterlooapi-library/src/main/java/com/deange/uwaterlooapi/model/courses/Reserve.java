package com.deange.uwaterlooapi.model.courses;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Reserve
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("reserve_group")
  String mReserveGroup;

  @SerializedName("enrollment_capacity")
  int mEnrollmentCapacity;

  @SerializedName("enrollment_total")
  int mEnrollmentTotal;

  protected Reserve(final Parcel in) {
    super(in);
    mReserveGroup = in.readString();
    mEnrollmentCapacity = in.readInt();
    mEnrollmentTotal = in.readInt();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mReserveGroup);
    dest.writeInt(mEnrollmentCapacity);
    dest.writeInt(mEnrollmentTotal);
  }

  public static final Creator<Reserve> CREATOR = new Creator<Reserve>() {
    @Override
    public Reserve createFromParcel(final Parcel in) {
      return new Reserve(in);
    }

    @Override
    public Reserve[] newArray(final int size) {
      return new Reserve[size];
    }
  };

  /**
   * Name of the reserved group
   */
  public String getReserveGroup() {
    return mReserveGroup;
  }

  /**
   * Total enrollment capacity of the group
   */
  public int getEnrollmentCapacity() {
    return mEnrollmentCapacity;
  }

  /**
   * Total reserve enrollment
   */
  public int getEnrollmentTotal() {
    return mEnrollmentTotal;
  }
}
