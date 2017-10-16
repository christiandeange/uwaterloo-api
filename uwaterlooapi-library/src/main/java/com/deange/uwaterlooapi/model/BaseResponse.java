package com.deange.uwaterlooapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public abstract class BaseResponse
    implements
    Parcelable {

  public BaseResponse() {
  }

  @SerializedName("meta")
  Metadata mMetadata;

  protected BaseResponse(final Parcel in) {
    mMetadata = in.readParcelable(Metadata.class.getClassLoader());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeParcelable(mMetadata, flags);
  }

  public Metadata getMetadata() {
    return mMetadata;
  }

  public abstract Object getData();

}
