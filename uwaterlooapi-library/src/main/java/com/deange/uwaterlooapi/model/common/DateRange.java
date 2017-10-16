package com.deange.uwaterlooapi.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DateRange
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("week")
  int mWeek;

  @SerializedName("year")
  int mYear;

  @SerializedName("start")
  String mStart;

  @SerializedName("end")
  String mEnd;

  protected DateRange(final Parcel in) {
    super(in);
    mWeek = in.readInt();
    mYear = in.readInt();
    mStart = in.readString();
    mEnd = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mWeek);
    dest.writeInt(mYear);
    dest.writeString(mStart);
    dest.writeString(mEnd);
  }

  public static final Creator<DateRange> CREATOR = new Creator<DateRange>() {
    @Override
    public DateRange createFromParcel(final Parcel in) {
      return new DateRange(in);
    }

    @Override
    public DateRange[] newArray(final int size) {
      return new DateRange[size];
    }
  };

  /**
   * Requested week
   */
  public int getWeek() {
    return mWeek;
  }

  /**
   * Requested year
   */
  public int getYear() {
    return mYear;
  }

  /**
   * Starting day of the menu (Y-m-d)
   */
  public Date getStart() {
    return DateUtils.parseDate(mStart);
  }

  /**
   * Ending day of the menu (Y-m-d)
   */
  public Date getEnd() {
    return DateUtils.parseDate(mEnd);
  }

  /**
   * Starting day of the menu (Y-m-d) as a string
   */
  public String getRawStartDate() {
    return mStart;
  }

  /**
   * Ending day of the menu (Y-m-d) as a string
   */
  public String getRawEndDate() {
    return mEnd;
  }
}
