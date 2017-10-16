package com.deange.uwaterlooapi.model.terms;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoSession
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("id")
  int mId;

  @SerializedName("employer")
  String mEmployer;

  @SerializedName("date")
  String mDate;

  @SerializedName("start_time")
  String mStartTime;

  @SerializedName("end_time")
  String mEndTime;

  @SerializedName("location")
  String mLocation;

  @SerializedName("website")
  String mWebsite;

  @SerializedName("audience")
  List<String> mAudience;

  @SerializedName("programs")
  String mPrograms;

  @SerializedName("description")
  String mDescription;

  protected InfoSession(final Parcel in) {
    super(in);
    mId = in.readInt();
    mEmployer = in.readString();
    mDate = in.readString();
    mStartTime = in.readString();
    mEndTime = in.readString();
    mLocation = in.readString();
    mWebsite = in.readString();
    mAudience = in.createStringArrayList();
    mPrograms = in.readString();
    mDescription = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mEmployer);
    dest.writeString(mDate);
    dest.writeString(mStartTime);
    dest.writeString(mEndTime);
    dest.writeString(mLocation);
    dest.writeString(mWebsite);
    dest.writeStringList(mAudience);
    dest.writeString(mPrograms);
    dest.writeString(mDescription);
  }

  public static final Creator<InfoSession> CREATOR = new Creator<InfoSession>() {
    @Override
    public InfoSession createFromParcel(final Parcel in) {
      return new InfoSession(in);
    }

    @Override
    public InfoSession[] newArray(final int size) {
      return new InfoSession[size];
    }
  };

  /**
   * Information session id
   */
  public int getId() {
    return mId;
  }

  /**
   * Name of employer hosting session
   */
  public String getEmployer() {
    return mEmployer;
  }

  /**
   * Date of session
   */
  public String getDate() {
    return mDate;
  }

  /**
   * Start time of session
   */
  public String getStartTime() {
    return mStartTime;
  }

  /**
   * End time of session
   */
  public String getEndTime() {
    return mEndTime;
  }

  /**
   * Location of session
   */
  public String getLocation() {
    return mLocation;
  }

  /**
   * Employer's website
   */
  public String getWebsite() {
    return mWebsite;
  }

  /**
   * List of intended programs for student audience
   */
  public List<String> getAudience() {
    return mAudience;
  }

  /**
   * Programs of study relevant to employer
   */
  public String getPrograms() {
    return mPrograms;
  }

  /**
   * Description of employer
   */
  public String getDescription() {
    return mDescription;
  }
}
