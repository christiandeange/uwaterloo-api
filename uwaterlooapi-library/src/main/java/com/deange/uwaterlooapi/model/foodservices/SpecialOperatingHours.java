package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SpecialOperatingHours
    extends OperatingHours
    implements
    Parcelable {

  @SerializedName("date")
  String mDate;

  protected SpecialOperatingHours(final Parcel in) {
    super(in);
    mDate = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mDate);
  }

  public static final Creator<SpecialOperatingHours> CREATOR =
      new Creator<SpecialOperatingHours>() {
        @Override
        public SpecialOperatingHours createFromParcel(final Parcel in) {
          return new SpecialOperatingHours(in);
        }

        @Override
        public SpecialOperatingHours[] newArray(final int size) {
          return new SpecialOperatingHours[size];
        }
      };

  /**
   * Y-m-d format date for the special case
   */
  public Date getDate() {
    return DateUtils.parseDate(mDate, DateUtils.YMD);
  }

  /**
   * Y-m-d format date for the special case as a string
   */
  public String getRawDate() {
    return mDate;
  }

}
