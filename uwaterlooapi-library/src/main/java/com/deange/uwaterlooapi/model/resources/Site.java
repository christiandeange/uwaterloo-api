package com.deange.uwaterlooapi.model.resources;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Site
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("name")
  String mName;

  @SerializedName("slug")
  String mSlug;

  @SerializedName("url")
  String mUrl;

  @SerializedName("group_code")
  String mGroupCode;

  @SerializedName("unit_code")
  String mUnitCode;

  @SerializedName("unit_short_name")
  String mUnitShortName;

  @SerializedName("owner_type")
  String mOwnerType;

  protected Site(final Parcel in) {
    super(in);
    mName = in.readString();
    mSlug = in.readString();
    mUrl = in.readString();
    mGroupCode = in.readString();
    mUnitCode = in.readString();
    mUnitShortName = in.readString();
    mOwnerType = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mName);
    dest.writeString(mSlug);
    dest.writeString(mUrl);
    dest.writeString(mGroupCode);
    dest.writeString(mUnitCode);
    dest.writeString(mUnitShortName);
    dest.writeString(mOwnerType);
  }

  public static final Creator<Site> CREATOR = new Creator<Site>() {
    @Override
    public Site createFromParcel(final Parcel in) {
      return new Site(in);
    }

    @Override
    public Site[] newArray(final int size) {
      return new Site[size];
    }
  };

  /**
   * The name of the site
   */
  public String getName() {
    return mName;
  }

  /**
   * A url-safe identifier for this site
   */
  public String getSlug() {
    return mSlug;
  }

  /**
   * A link to the site
   */
  public String getUrl() {
    return mUrl;
  }

  /**
   * The faculty code (if any) associated with the site
   */
  public String getGroupCode() {
    return mGroupCode;
  }

  /**
   * The department code (if any) associated with the site
   */
  public String getUnitCode() {
    return mUnitCode;
  }

  /**
   * The user-friendly name of the department associated with the site
   */
  public String getUnitShortName() {
    return mUnitShortName;
  }

  /**
   * Identifier for the site owner
   */
  public String getOwnerType() {
    return mOwnerType;
  }
}
