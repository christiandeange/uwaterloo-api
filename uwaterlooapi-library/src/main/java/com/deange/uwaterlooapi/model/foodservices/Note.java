package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Note
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("date")
  String mDate;

  @SerializedName("outlet_id")
  int mOutletId;

  @SerializedName("outlet_name")
  String mOutletName;

  @SerializedName("note")
  String mNote;

  protected Note(final Parcel in) {
    super(in);
    mDate = in.readString();
    mOutletId = in.readInt();
    mOutletName = in.readString();
    mNote = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mDate);
    dest.writeInt(mOutletId);
    dest.writeString(mOutletName);
    dest.writeString(mNote);
  }

  public static final Creator<Note> CREATOR = new Creator<Note>() {
    @Override
    public Note createFromParcel(final Parcel in) {
      return new Note(in);
    }

    @Override
    public Note[] newArray(final int size) {
      return new Note[size];
    }
  };

  /**
   * Outlet ID as per /foodservices/outlets
   */
  public int getOutletId() {
    return mOutletId;
  }

  /**
   * Outlet name as per /foodservices/outlets
   */
  public String getOutletName() {
    return mOutletName;
  }

  /**
   * Note
   */
  public String getNote() {
    return mNote;
  }

  /**
   * Menu date object
   */
  public Date getDate() {
    return DateUtils.parseDate(mDate, DateUtils.YMD);
  }

  /**
   * Menu date object as a string
   */
  public String getRawDate() {
    return mDate;
  }
}
