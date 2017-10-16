package com.deange.uwaterlooapi.model.resources;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Tutor
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("subject")
  String mSubject;

  @SerializedName("catalog_number")
  String mCatalogNumber;

  @SerializedName("title")
  String mTitle;

  @SerializedName("tutors_count")
  int mTutorsCount;

  @SerializedName("contact_url")
  String mContactUrl;

  protected Tutor(final Parcel in) {
    super(in);
    mSubject = in.readString();
    mCatalogNumber = in.readString();
    mTitle = in.readString();
    mTutorsCount = in.readInt();
    mContactUrl = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mSubject);
    dest.writeString(mCatalogNumber);
    dest.writeString(mTitle);
    dest.writeInt(mTutorsCount);
    dest.writeString(mContactUrl);
  }

  public static final Creator<Tutor> CREATOR = new Creator<Tutor>() {
    @Override
    public Tutor createFromParcel(final Parcel in) {
      return new Tutor(in);
    }

    @Override
    public Tutor[] newArray(final int size) {
      return new Tutor[size];
    }
  };

  /**
   * Subject acronym
   */
  public String getSubject() {
    return mSubject;
  }

  /**
   * Course catalog number
   */
  public String getCatalogNumber() {
    return mCatalogNumber;
  }

  /**
   * Course title
   */
  public String getTitle() {
    return mTitle;
  }

  /**
   * Total number of tutors available for that course
   */
  public int getTutorsCount() {
    return mTutorsCount;
  }

  /**
   * Link to get tutor contact details
   */
  public String getContactUrl() {
    return mContactUrl;
  }
}
