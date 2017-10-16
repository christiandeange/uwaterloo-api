package com.deange.uwaterlooapi.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.AbstractModel;
import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

public class SimpleResponse<T extends AbstractModel>
    extends BaseResponse
    implements
    Parcelable {

  @SerializedName("data")
  T mData;

  protected SimpleResponse() {
  }

  protected SimpleResponse(final T data) {
    mData = data;
  }

  protected SimpleResponse(final Parcel in) {
    super(in);

    // noinspection unchecked
    final Class<T> clazz = (Class<T>) in.readSerializable();
    if (clazz != null) {
      mData = in.readParcelable(clazz.getClassLoader());
    }
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeSerializable(mData != null ? mData.getClass() : null);
    dest.writeParcelable(mData, flags);
  }

  @Override
  public T getData() {
    return mData;
  }

  public static final Creator<SimpleResponse> CREATOR = new Creator<SimpleResponse>() {
    @Override
    public SimpleResponse createFromParcel(final Parcel in) {
      return new SimpleResponse(in);
    }

    @Override
    public SimpleResponse[] newArray(final int size) {
      return new SimpleResponse[size];
    }
  };

}
