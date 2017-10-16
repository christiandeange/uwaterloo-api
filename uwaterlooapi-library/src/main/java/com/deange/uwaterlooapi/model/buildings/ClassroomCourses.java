package com.deange.uwaterlooapi.model.buildings;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class ClassroomCourses
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("class_number")
  int mClassNumber;

  @SerializedName("subject")
  String mSubject;

  @SerializedName("catalog_number")
  String mCatalogNumber;

  @SerializedName("title")
  String mTitle;

  @SerializedName("section")
  String mSection;

  @SerializedName("weekdays")
  String mWeekdays;

  @SerializedName("start_time")
  String mStartTime;

  @SerializedName("end_time")
  String mEndTime;

  @SerializedName("start_date")
  String mStartDate;

  @SerializedName("end_date")
  String mEndDate;

  @SerializedName("enrollment_total")
  int mTotalEnrollment;

  @SerializedName("instructors")
  List<String> mInstructors;

  @SerializedName("building")
  String mBuilding;

  @SerializedName("room")
  String mRoom;

  @SerializedName("term")
  int mTerm;

  @SerializedName("last_updated")
  String mUpdated;

  protected ClassroomCourses(final Parcel in) {
    super(in);
    mClassNumber = in.readInt();
    mSubject = in.readString();
    mCatalogNumber = in.readString();
    mTitle = in.readString();
    mSection = in.readString();
    mWeekdays = in.readString();
    mStartTime = in.readString();
    mEndTime = in.readString();
    mStartDate = in.readString();
    mEndDate = in.readString();
    mTotalEnrollment = in.readInt();
    mInstructors = in.createStringArrayList();
    mBuilding = in.readString();
    mRoom = in.readString();
    mTerm = in.readInt();
    mUpdated = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mClassNumber);
    dest.writeString(mSubject);
    dest.writeString(mCatalogNumber);
    dest.writeString(mTitle);
    dest.writeString(mSection);
    dest.writeString(mWeekdays);
    dest.writeString(mStartTime);
    dest.writeString(mEndTime);
    dest.writeString(mStartDate);
    dest.writeString(mEndDate);
    dest.writeInt(mTotalEnrollment);
    dest.writeStringList(mInstructors);
    dest.writeString(mBuilding);
    dest.writeString(mRoom);
    dest.writeInt(mTerm);
    dest.writeString(mUpdated);
  }

  public static final Creator<ClassroomCourses> CREATOR = new Creator<ClassroomCourses>() {
    @Override
    public ClassroomCourses createFromParcel(final Parcel in) {
      return new ClassroomCourses(in);
    }

    @Override
    public ClassroomCourses[] newArray(final int size) {
      return new ClassroomCourses[size];
    }
  };

  /**
   * Class Number
   */
  public int getClassNumber() {
    return mClassNumber;
  }

  /**
   * Course subject code
   */
  public String getSubject() {
    return mSubject;
  }

  /**
   * Catalog number
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
   * Course section number
   */
  public String getSection() {
    return mSection;
  }

  /**
   * Course class days
   */
  public String getWeekdays() {
    return mWeekdays;
  }

  /**
   * Start time
   */
  public String getStartTime() {
    return mStartTime;
  }

  /**
   * End time
   */
  public String getEndTime() {
    return mEndTime;
  }

  /**
   * Start date
   */
  public String getStartDate() {
    return mStartDate;
  }

  /**
   * End date
   */
  public String getEndDate() {
    return mEndDate;
  }

  /**
   * Number of students currently enrolled in the section
   */
  public int getTotalEnrollment() {
    return mTotalEnrollment;
  }

  /**
   * List of instructors the individual meet
   */
  public List<String> getInstructors() {
    return mInstructors;
  }

  /**
   * Building code of building where the individual meet is held
   */
  public String getBuilding() {
    return mBuilding;
  }

  /**
   * Room where the individual meet is held
   */
  public String getRoom() {
    return mRoom;
  }

  /**
   * Particular 4-month period within which sessions are defined
   */
  public int getTerm() {
    return mTerm;
  }

  /**
   * Server time at last update (in ISO 8601 format)
   */
  public Date getUpdated() {
    return DateUtils.parseDate(mUpdated);
  }

  /**
   * Server time at last update (in ISO 8601 format) as a string
   */
  public String getRawUpdated() {
    return mUpdated;
  }
}
