package com.deange.uwaterlooapi.model.buildings;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class BuildingSection
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("section_name")
  String mSectionName;

  @SerializedName("latitude")
  float mLatitude;

  @SerializedName("longitude")
  float mLongitude;

  protected BuildingSection(final Parcel in) {
    super(in);
    mSectionName = in.readString();
    mLatitude = in.readFloat();
    mLongitude = in.readFloat();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mSectionName);
    dest.writeFloat(mLatitude);
    dest.writeFloat(mLongitude);
  }

  public static final Creator<BuildingSection> CREATOR = new Creator<BuildingSection>() {
    @Override
    public BuildingSection createFromParcel(final Parcel in) {
      return new BuildingSection(in);
    }

    @Override
    public BuildingSection[] newArray(final int size) {
      return new BuildingSection[size];
    }
  };

  /**
   * Name of section
   */
  public String getSectionName() {
    return mSectionName;
  }

  /**
   * Latitude + longitude of building section location
   */
  public float[] getLocation() {
    return new float[]{mLatitude, mLongitude};
  }

  @Override
  public String toString() {
    return mSectionName;
  }
}
