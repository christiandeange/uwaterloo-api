package com.deange.uwaterlooapi.model.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class EventLocation
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("id")
  int mId;

  @SerializedName("name")
  String mName;

  @SerializedName("street")
  String mStreet;

  @SerializedName("additional")
  String mAdditional;

  @SerializedName("city")
  String mCity;

  @SerializedName("province")
  String mProvince;

  @SerializedName("postal_code")
  String mPostalCode;

  @SerializedName("country")
  String mCountry;

  @SerializedName("latitude")
  float mLatitude;

  @SerializedName("longitude")
  float mLongitude;

  protected EventLocation(final Parcel in) {
    super(in);
    mId = in.readInt();
    mName = in.readString();
    mStreet = in.readString();
    mAdditional = in.readString();
    mCity = in.readString();
    mProvince = in.readString();
    mPostalCode = in.readString();
    mCountry = in.readString();
    mLatitude = in.readFloat();
    mLongitude = in.readFloat();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mName);
    dest.writeString(mStreet);
    dest.writeString(mAdditional);
    dest.writeString(mCity);
    dest.writeString(mProvince);
    dest.writeString(mPostalCode);
    dest.writeString(mCountry);
    dest.writeFloat(mLatitude);
    dest.writeFloat(mLongitude);
  }

  public static final Creator<EventLocation> CREATOR = new Creator<EventLocation>() {
    @Override
    public EventLocation createFromParcel(final Parcel in) {
      return new EventLocation(in);
    }

    @Override
    public EventLocation[] newArray(final int size) {
      return new EventLocation[size];
    }
  };

  /**
   * Unique id of location
   */
  public int getId() {
    return mId;
  }

  /**
   * Name of location
   */
  public String getName() {
    return mName;
  }

  /**
   * Street address of location
   */
  public String getStreet() {
    return mStreet;
  }

  /**
   * Additional information regarding street address of location
   */
  public String getAdditional() {
    return mAdditional;
  }

  /**
   * Name of city
   */
  public String getCity() {
    return mCity;
  }

  /**
   * Name of province in two-letter short form
   */
  public String getProvince() {
    return mProvince;
  }

  /**
   * Postal code "in L#L #L#" format
   */
  public String getPostalCode() {
    return mPostalCode;
  }

  /**
   * Full name of country
   */
  public String getCountry() {
    return mCountry;
  }

  /**
   * Event location latitude + longitude
   */
  public float[] getLocation() {
    return new float[]{mLatitude, mLongitude};
  }

}
