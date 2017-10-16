package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Announcement
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("date")
  String mDate;

  @SerializedName("ad_text")
  String mText;

  protected Announcement(final Parcel in) {
    super(in);
    mDate = in.readString();
    mText = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mDate);
    dest.writeString(mText);
  }

  public static final Creator<Announcement> CREATOR = new Creator<Announcement>() {
    @Override
    public Announcement createFromParcel(final Parcel in) {
      return new Announcement(in);
    }

    @Override
    public Announcement[] newArray(final int size) {
      return new Announcement[size];
    }
  };

  /**
   * Advertisement date object
   */
  public Date getDate() {
    return DateUtils.parseDate(mDate, DateUtils.YMD);
  }

  /**
   * Advertisement date object as a string
   */
  public String getRawDate() {
    return mDate;
  }

  /**
   * Advertisement text
   */
  public String getText() {
    return mText;
  }
}
