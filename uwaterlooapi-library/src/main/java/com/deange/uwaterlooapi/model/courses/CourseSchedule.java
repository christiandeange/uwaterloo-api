package com.deange.uwaterlooapi.model.courses;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class CourseSchedule
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("subject")
  String mSubject;

  @SerializedName("catalog_number")
  String mCatalogNumber;

  @SerializedName("units")
  float mUnits;

  @SerializedName("title")
  String mTitle;

  @SerializedName("note")
  String mNotes;

  @SerializedName("class_number")
  int mClassNumber;

  @SerializedName("section")
  String mSection;

  @SerializedName("campus")
  String mCampus;

  @SerializedName("associated_class")
  int mAssociatedClassId;

  @SerializedName("related_component_1")
  String mRelatedComponent1;

  @SerializedName("related_component_2")
  String mRelatedComponent2;

  @SerializedName("enrollment_capacity")
  int mEnrollmentCapacity;

  @SerializedName("enrollment_total")
  int mEnrollmentTotal;

  @SerializedName("waiting_capacity")
  int mWaitingCapacity;

  @SerializedName("waiting_total")
  int mWaitingTotal;

  @SerializedName("topic")
  String mTopic;

  @SerializedName("reserves")
  List<Reserve> mReserves;

  @SerializedName("classes")
  List<Class> mClasses;

  @SerializedName("held_with")
  List<String> mHeldWith;

  @SerializedName("term")
  int mTerm;

  @SerializedName("academic_level")
  String mAcademicLevel;

  @SerializedName("last_updated")
  String mLastUpdated;

  protected CourseSchedule(final Parcel in) {
    super(in);
    mSubject = in.readString();
    mCatalogNumber = in.readString();
    mUnits = in.readFloat();
    mTitle = in.readString();
    mNotes = in.readString();
    mClassNumber = in.readInt();
    mSection = in.readString();
    mCampus = in.readString();
    mAssociatedClassId = in.readInt();
    mRelatedComponent1 = in.readString();
    mRelatedComponent2 = in.readString();
    mEnrollmentCapacity = in.readInt();
    mEnrollmentTotal = in.readInt();
    mWaitingCapacity = in.readInt();
    mWaitingTotal = in.readInt();
    mTopic = in.readString();
    mReserves = in.createTypedArrayList(Reserve.CREATOR);
    mClasses = in.createTypedArrayList(Class.CREATOR);
    mHeldWith = in.createStringArrayList();
    mTerm = in.readInt();
    mAcademicLevel = in.readString();
    mLastUpdated = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mSubject);
    dest.writeString(mCatalogNumber);
    dest.writeFloat(mUnits);
    dest.writeString(mTitle);
    dest.writeString(mNotes);
    dest.writeInt(mClassNumber);
    dest.writeString(mSection);
    dest.writeString(mCampus);
    dest.writeInt(mAssociatedClassId);
    dest.writeString(mRelatedComponent1);
    dest.writeString(mRelatedComponent2);
    dest.writeInt(mEnrollmentCapacity);
    dest.writeInt(mEnrollmentTotal);
    dest.writeInt(mWaitingCapacity);
    dest.writeInt(mWaitingTotal);
    dest.writeString(mTopic);
    dest.writeTypedList(mReserves);
    dest.writeTypedList(mClasses);
    dest.writeStringList(mHeldWith);
    dest.writeInt(mTerm);
    dest.writeString(mAcademicLevel);
    dest.writeString(mLastUpdated);
  }

  public static final Creator<CourseSchedule> CREATOR = new Creator<CourseSchedule>() {
    @Override
    public CourseSchedule createFromParcel(final Parcel in) {
      return new CourseSchedule(in);
    }

    @Override
    public CourseSchedule[] newArray(final int size) {
      return new CourseSchedule[size];
    }
  };

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
   * Credit count for the mentioned course
   */
  public float getUnits() {
    return mUnits;
  }

  /**
   * Class name and title
   */
  public String getTitle() {
    return mTitle;
  }

  /**
   * Additional notes regarding enrollment for the given term
   */
  public String getNotes() {
    return mNotes;
  }

  /**
   * Associated term specific class enrollment number
   */
  public int getClassNumber() {
    return mClassNumber;
  }

  /**
   * Class instruction and number
   */
  public String getSection() {
    return mSection;
  }

  /**
   * Name of the campus the course is being offered
   */
  public String getCampus() {
    return mCampus;
  }

  /**
   * ID of the associated class
   */
  public int getAssociatedClassId() {
    return mAssociatedClassId;
  }

  /**
   * Name of the first related course component
   */
  public String getRelatedComponent1() {
    return mRelatedComponent1;
  }

  /**
   * Name of the second related course component
   */
  public String getRelatedComponent2() {
    return mRelatedComponent2;
  }

  /**
   * Class enrollment capacity
   */
  public int getEnrollmentCapacity() {
    return mEnrollmentCapacity;
  }

  /**
   * Total current class enrollment
   */
  public int getEnrollmentTotal() {
    return mEnrollmentTotal;
  }

  /**
   * Class waiting capacity
   */
  public int getWaitingCapacity() {
    return mWaitingCapacity;
  }

  /**
   * Total current waiting students
   */
  public int getWaitingTotal() {
    return mWaitingTotal;
  }

  /**
   * Class discussion topic
   */
  public String getTopic() {
    return mTopic;
  }

  /**
   * Course specific enrollment reservation data
   */
  public List<Reserve> getReserves() {
    return mReserves;
  }

  /**
   * Schedule data
   */
  public List<Class> getClasses() {
    return mClasses;
  }

  /**
   * A list of classes the course is held with
   */
  public List<String> getHeldWith() {
    return mHeldWith;
  }

  /**
   * 4 digit term representation
   */
  public int getTerm() {
    return mTerm;
  }

  /**
   * Undergraduate or graduate course classification
   */
  public String getAcademicLevel() {
    return mAcademicLevel;
  }

  /**
   * ISO8601 timestamp of when the data was last updated
   */
  public Date getLastUpdated() {
    return DateUtils.parseDate(mLastUpdated, DateUtils.ISO8601);
  }

  /**
   * ISO8601 timestamp of when the data was last updated as a string
   */
  public String getRawLastUpdated() {
    return mLastUpdated;
  }

}
