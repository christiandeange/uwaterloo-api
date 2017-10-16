package com.deange.uwaterlooapi.model.courses;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseInfo
    extends Course
    implements
    Parcelable {

  @SerializedName("instructions")
  List<String> mInstructions;

  @SerializedName("prerequisites")
  String mPrerequisites;

  @SerializedName("antirequisites")
  String mAntirequisites;

  @SerializedName("corequisites")
  String mCorequisites;

  @SerializedName("crosslistings")
  String mCrosslistings;

  @SerializedName("terms_offered")
  List<String> mTermsOffered;

  @SerializedName("notes")
  String mNotes;

  @SerializedName("offerings")
  CourseLocations mCourseLocations;

  @SerializedName("needs_department_consent")
  boolean mNeedsDepartmentConsent;

  @SerializedName("needs_instructor_consent")
  boolean mNeedsInstructorConsent;

  @SerializedName("extra")
  List<String> mExtraInfo;

  @SerializedName("calendar_year")
  String mYear;

  @SerializedName("url")
  String mUrl;

  protected CourseInfo(final Parcel in) {
    super(in);
    mInstructions = in.createStringArrayList();
    mPrerequisites = in.readString();
    mAntirequisites = in.readString();
    mCorequisites = in.readString();
    mCrosslistings = in.readString();
    mTermsOffered = in.createStringArrayList();
    mNotes = in.readString();
    mCourseLocations = in.readParcelable(CourseLocations.class.getClassLoader());
    mNeedsDepartmentConsent = in.readByte() != 0;
    mNeedsInstructorConsent = in.readByte() != 0;
    mExtraInfo = in.createStringArrayList();
    mYear = in.readString();
    mUrl = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeStringList(mInstructions);
    dest.writeString(mPrerequisites);
    dest.writeString(mAntirequisites);
    dest.writeString(mCorequisites);
    dest.writeString(mCrosslistings);
    dest.writeStringList(mTermsOffered);
    dest.writeString(mNotes);
    dest.writeParcelable(mCourseLocations, flags);
    dest.writeByte((byte) (mNeedsDepartmentConsent ? 1 : 0));
    dest.writeByte((byte) (mNeedsInstructorConsent ? 1 : 0));
    dest.writeStringList(mExtraInfo);
    dest.writeString(mYear);
    dest.writeString(mUrl);
  }

  public static final Creator<CourseInfo> CREATOR = new Creator<CourseInfo>() {
    @Override
    public CourseInfo createFromParcel(final Parcel in) {
      return new CourseInfo(in);
    }

    @Override
    public CourseInfo[] newArray(final int size) {
      return new CourseInfo[size];
    }
  };

  /**
   * Instruction types for the course (LEC, TUT, LAB etc)
   */
  public List<String> getInstructions() {
    return mInstructions;
  }

  /**
   * Prerequisite listing for the course
   */
  public String getPrerequisites() {
    return mPrerequisites;
  }

  /**
   * Antirequisite listing for the course
   */
  public String getAntirequisites() {
    return mAntirequisites;
  }

  /**
   * Corequisite listing for the course
   */
  public String getCorequisites() {
    return mCorequisites;
  }

  /**
   * Crosslisted courses
   */
  public String getCrosslistings() {
    return mCrosslistings;
  }

  /**
   * List of terms that the course is offered
   */
  public List<String> getTermsOffered() {
    return mTermsOffered;
  }

  /**
   * Additional notes on the course
   */
  public String getNotes() {
    return mNotes;
  }

  /**
   * Test for where a course is offered.
   * <p/>
   * Must be one of the constants defined in {@link CourseLocations}.
   *
   * @see CourseLocations#ONLINE
   * @see CourseLocations#ONLINE_ONLY
   * @see CourseLocations#ST_JEROME
   * @see CourseLocations#ST_JEROME_ONLY
   * @see CourseLocations#RENISON
   * @see CourseLocations#RENISON_ONLY
   * @see CourseLocations#CONGRAD_GREBEL
   * @see CourseLocations#CONGRAD_GREBEL_ONLY
   */
  public boolean isOfferedAt(final String offering) {
    return mCourseLocations.isOfferedAt(offering);
  }

  /**
   * Does enrollment require the department's permission
   */
  public boolean isDepartmentConsentRequired() {
    return mNeedsDepartmentConsent;
  }

  /**
   * Does enrollment require instructor's consent
   */
  public boolean isInstructorConsentRequired() {
    return mNeedsInstructorConsent;
  }

  /**
   * Any additional information associated with the course
   */
  public List<String> getExtraInfo() {
    return mExtraInfo;
  }

  /**
   * Last active year the course was offered
   */
  public String getYear() {
    return mYear;
  }

  /**
   * Last active year the course was offered
   * <p/>
   * NOTE: EXPERIMENTAL. NO GUARANTEES MADE ABOUT VALIDITY
   */
  public int[] getYearRange() {

    if (TextUtils.isEmpty(mYear) || mYear.length() != 4) {
      // fail fast
      return null;
    }

    final String first = "20" + mYear.substring(0, 2);
    final String second = "20" + mYear.substring(2, 4);

    try {
      return new int[]{Integer.parseInt(first), Integer.parseInt(second)};

    } catch (final NumberFormatException e) {
      Log.w("CourseInfo", "Unexpected academic year: \'" + mYear + "\'", e);
      return null;
    }
  }

  /**
   * Course URL on the course calendar
   */
  public String getUrl() {
    return mUrl;
  }
}
