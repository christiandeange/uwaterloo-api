package com.deange.uwaterlooapi.model.resources;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Printer
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("printer")
  String mPrinterName;

  @SerializedName("ad")
  String mActiveDirectory;

  @SerializedName("server")
  String mServer;

  @SerializedName("comment")
  String mComments;

  @SerializedName("driver")
  String mDriver;

  @SerializedName("room")
  String mRoom;

  @SerializedName("faculty")
  String mFaculty;

  protected Printer(final Parcel in) {
    super(in);
    mPrinterName = in.readString();
    mActiveDirectory = in.readString();
    mServer = in.readString();
    mComments = in.readString();
    mDriver = in.readString();
    mRoom = in.readString();
    mFaculty = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mPrinterName);
    dest.writeString(mActiveDirectory);
    dest.writeString(mServer);
    dest.writeString(mComments);
    dest.writeString(mDriver);
    dest.writeString(mRoom);
    dest.writeString(mFaculty);
  }

  public static final Creator<Printer> CREATOR = new Creator<Printer>() {
    @Override
    public Printer createFromParcel(final Parcel in) {
      return new Printer(in);
    }

    @Override
    public Printer[] newArray(final int size) {
      return new Printer[size];
    }
  };

  /**
   * Name of the printer
   */
  public String getPrinterName() {
    return mPrinterName;
  }

  /**
   * Printers active directory id
   */
  public String getActiveDirectory() {
    return mActiveDirectory;
  }

  /**
   * Printer server name
   */
  public String getServer() {
    return mServer;
  }

  /**
   * Additional comments on the printer
   */
  public String getComments() {
    return mComments;
  }

  /**
   * Printer driver information
   */
  public String getDriverInfo() {
    return mDriver;
  }

  /**
   * Printer's physical room location
   */
  public String getRoom() {
    return mRoom;
  }

  /**
   * Faculty the printer belongs to
   */
  public String getFaculty() {
    return mFaculty;
  }
}
