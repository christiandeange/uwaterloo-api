package com.deange.uwaterlooapi.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SimpleListResponse<T extends Parcelable>
    extends BaseResponse
    implements
    Parcelable {

  @SerializedName("data")
  List<T> mData;

  protected SimpleListResponse() {
  }

  protected SimpleListResponse(final Parcel in) {
    super(in);

    final int size = in.readInt();
    if (size != -1) {
      mData = new ArrayList<>(size);
      for (int i = 0; i < size; ++i) {
        // noinspection unchecked
        final Class<T> clazz = (Class<T>) in.readSerializable();
        if (clazz != null) {
          final T data = in.readParcelable(clazz.getClassLoader());
          mData.add(data);
        }
      }
    }
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);

    if (mData == null) {
      dest.writeInt(-1);
    } else {
      dest.writeInt(mData.size());
      for (T data : mData) {
        dest.writeSerializable(data != null ? data.getClass() : null);
        dest.writeParcelable(data, flags);
      }
    }
  }

  public static final Creator<SimpleListResponse> CREATOR = new Creator<SimpleListResponse>() {
    @Override
    public SimpleListResponse createFromParcel(final Parcel in) {
      return new SimpleListResponse(in);
    }

    @Override
    public SimpleListResponse[] newArray(final int size) {
      return new SimpleListResponse[size];
    }
  };

  @Override
  public List<T> getData() {
    return mData;
  }

}
