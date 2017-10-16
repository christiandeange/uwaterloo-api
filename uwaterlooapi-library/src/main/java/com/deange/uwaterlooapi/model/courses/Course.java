package com.deange.uwaterlooapi.model.courses;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Course
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("course_id")
  String mCourseId;

  @SerializedName("subject")
  String mSubject;

  @SerializedName("catalog_number")
  String mCatalogNumber;

  @SerializedName("title")
  String mTitle;

  @SerializedName("units")
  float mUnits;

  @SerializedName("description")
  String mDescription;

  @SerializedName("academic_level")
  String mAcademicLevel;

  protected Course(final Parcel in) {
    super(in);
    mCourseId = in.readString();
    mSubject = in.readString();
    mCatalogNumber = in.readString();
    mTitle = in.readString();
    mUnits = in.readFloat();
    mDescription = in.readString();
    mAcademicLevel = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mCourseId);
    dest.writeString(mSubject);
    dest.writeString(mCatalogNumber);
    dest.writeString(mTitle);
    dest.writeFloat(mUnits);
    dest.writeString(mDescription);
    dest.writeString(mAcademicLevel);
  }

  public static final Creator<Course> CREATOR = new Creator<Course>() {
    @Override
    public Course createFromParcel(final Parcel in) {
      return new Course(in);
    }

    @Override
    public Course[] newArray(final int size) {
      return new Course[size];
    }
  };

  /**
   * Registrar assigned course ID
   */
  public int getCourseId() {
    // Ensure it is not interpreted as octal
    return Integer.parseInt(mCourseId, 10);
  }

  /**
   * Requested subject acronym
   */
  public String getSubject() {
    return mSubject;
  }

  /**
   * Registrar assigned class number
   */
  public String getCatalogNumber() {
    return mCatalogNumber;
  }

  /**
   * Class name and title
   */
  public String getTitle() {
    return mTitle;
  }

  /**
   * Brief course description
   */
  public String getDescription() {
    return mDescription;
  }

  /**
   * Credit count for the mentioned course
   */
  public float getUnits() {
    return mUnits;
  }

  /**
   * Undergraduate or graduate course classification
   */
  public String getAcademicLevel() {
    return mAcademicLevel;
  }
}
