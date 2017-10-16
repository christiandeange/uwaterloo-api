package com.deange.uwaterlooapi.model.buildings;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Building
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("building_id")
  String mBuildingId;

  @SerializedName("building_code")
  String mBuildingCode;

  @SerializedName("building_name")
  String mBuildingName;

  @SerializedName("alternate_names")
  List<String> mAlternateNames;

  @SerializedName("latitude")
  float mLatitude;

  @SerializedName("longitude")
  float mLongitude;

  @SerializedName("building_sections")
  List<BuildingSection> mBuildingSections;

  protected Building(final Parcel in) {
    super(in);
    mBuildingId = in.readString();
    mBuildingCode = in.readString();
    mBuildingName = in.readString();
    mAlternateNames = in.createStringArrayList();
    mLatitude = in.readFloat();
    mLongitude = in.readFloat();
    mBuildingSections = in.createTypedArrayList(BuildingSection.CREATOR);
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mBuildingId);
    dest.writeString(mBuildingCode);
    dest.writeString(mBuildingName);
    dest.writeStringList(mAlternateNames);
    dest.writeFloat(mLatitude);
    dest.writeFloat(mLongitude);
    dest.writeTypedList(mBuildingSections);
  }

  public static final Creator<Building> CREATOR = new Creator<Building>() {
    @Override
    public Building createFromParcel(final Parcel in) {
      return new Building(in);
    }

    @Override
    public Building[] newArray(final int size) {
      return new Building[size];
    }
  };

  /**
   * Official unique building number
   */
  public String getBuildingId() {
    return mBuildingId;
  }

  /**
   * Official unique building code
   */
  public String getBuildingCode() {
    return mBuildingCode;
  }

  /**
   * Official unique building name
   */
  public String getBuildingName() {
    return mBuildingName;
  }

  /**
   * Alternate building names
   */
  public List<String> getAlternateNames() {
    return mAlternateNames;
  }

  /**
   * Latitude + longitude of building location
   */
  public float[] getLocation() {
    return new float[]{mLatitude, mLongitude};
  }

  /**
   * List of building sections
   */
  public List<BuildingSection> getBuildingSections() {
    return mBuildingSections;
  }

  /**
   * Check to see if building coordinates are provided
   */
  public boolean hasLocation() {
    return mLatitude != 0 && mLongitude != 0;
  }
}
