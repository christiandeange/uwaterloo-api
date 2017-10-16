package com.deange.uwaterlooapi.model.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Website
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("title")
  String mTitle;

  @SerializedName("url")
  String mUrl;

  protected Website(final Parcel in) {
    super(in);
    mTitle = in.readString();
    mUrl = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mTitle);
    dest.writeString(mUrl);
  }

  public static final Creator<Website> CREATOR = new Creator<Website>() {
    @Override
    public Website createFromParcel(final Parcel in) {
      return new Website(in);
    }

    @Override
    public Website[] newArray(final int size) {
      return new Website[size];
    }
  };

  public String getTitle() {
    return mTitle;
  }

  public String getUrl() {
    return mUrl;
  }

}
