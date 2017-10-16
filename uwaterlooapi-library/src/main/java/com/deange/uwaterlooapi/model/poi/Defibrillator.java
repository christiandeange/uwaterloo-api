package com.deange.uwaterlooapi.model.poi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Defibrillator
    extends BasicPointOfInterest
    implements
    Parcelable {

  @SerializedName("level")
  String mLevel;

  @SerializedName("room")
  String mRoom;

  @SerializedName("image")
  String mImage;

  protected Defibrillator(final Parcel in) {
    super(in);
    mLevel = in.readString();
    mRoom = in.readString();
    mImage = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mLevel);
    dest.writeString(mRoom);
    dest.writeString(mImage);
  }

  public static final Creator<Defibrillator> CREATOR = new Creator<Defibrillator>() {
    @Override
    public Defibrillator createFromParcel(final Parcel in) {
      return new Defibrillator(in);
    }

    @Override
    public Defibrillator[] newArray(final int size) {
      return new Defibrillator[size];
    }
  };

  public String getLevel() {
    return mLevel;
  }

  public String getRoom() {
    return mRoom;
  }

  public String getImage() {
    return mImage;
  }
}
