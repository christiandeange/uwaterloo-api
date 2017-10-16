package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.DateRange;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuInfo
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("date")
  DateRange mDateRange;

  @SerializedName("outlets")
  List<Outlet> mOutlets;

  protected MenuInfo(final Parcel in) {
    super(in);
    mDateRange = in.readParcelable(DateRange.class.getClassLoader());
    mOutlets = in.createTypedArrayList(Outlet.CREATOR);
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeParcelable(mDateRange, flags);
    dest.writeTypedList(mOutlets);
  }

  public static final Creator<MenuInfo> CREATOR = new Creator<MenuInfo>() {
    @Override
    public MenuInfo createFromParcel(final Parcel in) {
      return new MenuInfo(in);
    }

    @Override
    public MenuInfo[] newArray(final int size) {
      return new MenuInfo[size];
    }
  };

  /**
   * Menu date object
   */
  public DateRange getDateRange() {
    return mDateRange;
  }

  /**
   * Available outlets
   */
  public List<Outlet> getOutlets() {
    return mOutlets;
  }

}
