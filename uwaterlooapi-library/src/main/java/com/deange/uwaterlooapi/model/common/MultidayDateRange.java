package com.deange.uwaterlooapi.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MultidayDateRange
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("start")
  String mStart;

  @SerializedName("end")
  String mEnd;

  @SerializedName("start_day")
  String mStartDay;

  @SerializedName("end_day")
  String mEndDay;

  @SerializedName("start_time")
  String mStartTime;

  @SerializedName("end_time")
  String mEndTime;

  @SerializedName("start_date")
  String mStartDate;

  @SerializedName("end_date")
  String mEndDate;

  protected MultidayDateRange(final Parcel in) {
    super(in);
    mStart = in.readString();
    mEnd = in.readString();
    mStartDay = in.readString();
    mEndDay = in.readString();
    mStartTime = in.readString();
    mEndTime = in.readString();
    mStartDate = in.readString();
    mEndDate = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mStart);
    dest.writeString(mEnd);
    dest.writeString(mStartDay);
    dest.writeString(mEndDay);
    dest.writeString(mStartTime);
    dest.writeString(mEndTime);
    dest.writeString(mStartDate);
    dest.writeString(mEndDate);
  }

  public static final Creator<MultidayDateRange> CREATOR = new Creator<MultidayDateRange>() {
    @Override
    public MultidayDateRange createFromParcel(final Parcel in) {
      return new MultidayDateRange(in);
    }

    @Override
    public MultidayDateRange[] newArray(final int size) {
      return new MultidayDateRange[size];
    }
  };

  /**
   * ISO 8601 formatted start date
   */
  public Date getStart() {
    return DateUtils.parseDate(mStart);
  }

  /**
   * ISO 8601 formatted start date as a string
   */
  public String getRawStart() {
    return mStart;
  }

  /**
   * ISO 8601 formatted end date
   */
  public Date getEnd() {
    return DateUtils.parseDate(mEnd);
  }

  /**
   * ISO 8601 formatted end date as a string
   */
  public String getRawEnd() {
    return mEnd;
  }

  /**
   * Full name of day of week for start day, eg: Saturday
   */
  public String getStartDay() {
    return mStartDay;
  }

  /**
   * Full name of day of week for end day, eg: Saturday
   */
  public String getEndDay() {
    return mEndDay;
  }

  /**
   * HH:MM:SS 24 formatted start time, eg: 04:00:00
   */
  public String getStartTime() {
    return mStartTime;
  }

  /**
   * YYYY-MM-DD formatted end time, eg: 04:00:00
   */
  public String getEndTime() {
    return mEndTime;
  }

  /**
   * YYYY-MM-DD formatted start date, eg: 2014-09-27
   */
  public String getStartDate() {
    return mStartDate;
  }

  /**
   * YYYY-MM-DD formatted end date, eg: 2014-09-27
   */
  public String getEndDate() {
    return mEndDate;
  }
}
