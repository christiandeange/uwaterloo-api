package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Diet
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("diet_id")
  int mId;

  @SerializedName("diet_type")
  String mType;

  protected Diet(final Parcel in) {
    super(in);
    mId = in.readInt();
    mType = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mType);
  }

  public static final Creator<Diet> CREATOR = new Creator<Diet>() {
    @Override
    public Diet createFromParcel(final Parcel in) {
      return new Diet(in);
    }

    @Override
    public Diet[] newArray(final int size) {
      return new Diet[size];
    }
  };

  /**
   * Diet ID number
   */
  public int getId() {
    return mId;
  }

  /**
   * Diet type
   */
  public String getType() {
    return mType;
  }
}
