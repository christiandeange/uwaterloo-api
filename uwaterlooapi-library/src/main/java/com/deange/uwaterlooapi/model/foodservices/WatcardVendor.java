package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class WatcardVendor
    extends BaseModel {

  @SerializedName("vendor_id")
  int mId;

  @SerializedName("vendor_name")
  String mName;

  protected WatcardVendor(final Parcel in) {
    super(in);
    mId = in.readInt();
    mName = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mName);
  }

  public static final Creator<WatcardVendor> CREATOR = new Creator<WatcardVendor>() {
    @Override
    public WatcardVendor createFromParcel(final Parcel in) {
      return new WatcardVendor(in);
    }

    @Override
    public WatcardVendor[] newArray(final int size) {
      return new WatcardVendor[size];
    }
  };

  /**
   * Outlet ID number
   */
  public int getId() {
    return mId;
  }

  /**
   * Vendor name
   */
  public String getName() {
    return mName;
  }

}
