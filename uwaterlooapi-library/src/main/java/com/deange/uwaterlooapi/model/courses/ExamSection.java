package com.deange.uwaterlooapi.model.courses;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ExamSection
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("section")
  String mSection;

  @SerializedName("day")
  String mDay;

  @SerializedName("date")
  String mDate;

  @SerializedName("start_time")
  String mStartTime;

  @SerializedName("end_time")
  String mEndTime;

  @SerializedName("location")
  String mLocation;

  @SerializedName("notes")
  String mNotes;

  protected ExamSection(final Parcel in) {
    super(in);
    mSection = in.readString();
    mDay = in.readString();
    mDate = in.readString();
    mStartTime = in.readString();
    mEndTime = in.readString();
    mLocation = in.readString();
    mNotes = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mSection);
    dest.writeString(mDay);
    dest.writeString(mDate);
    dest.writeString(mStartTime);
    dest.writeString(mEndTime);
    dest.writeString(mLocation);
    dest.writeString(mNotes);
  }

  public static final Creator<ExamSection> CREATOR = new Creator<ExamSection>() {
    @Override
    public ExamSection createFromParcel(final Parcel in) {
      return new ExamSection(in);
    }

    @Override
    public ExamSection[] newArray(final int size) {
      return new ExamSection[size];
    }
  };

  /**
   * Exam section number
   */
  public String getSection() {
    return mSection;
  }

  /**
   * Day of the exam
   */
  public String getDay() {
    return mDay;
  }

  /**
   * ISO8601 exam date representation
   */
  public Date getDate() {
    return DateUtils.parseDate(mDate, DateUtils.YMD);
  }

  /**
   * ISO8601 exam date representation as a string
   */
  public String getRawDate() {
    return mDate;
  }

  /**
   * Exam starting time
   */
  public String getStartTime() {
    return mStartTime;
  }

  /**
   * Exam ending time
   */
  public String getEndTime() {
    return mEndTime;
  }

  /**
   * Exam location
   */
  public String getLocation() {
    return mLocation;
  }

  /**
   * Additional notes regarding the section
   */
  public String getNotes() {
    return mNotes;
  }
}
