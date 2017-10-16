package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Menu
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("date")
  String mDate;

  @SerializedName("day")
  String mDayOfWeek;

  @SerializedName("meals")
  Meals mMeals;

  @SerializedName("notes")
  String mNotes;

  protected Menu(final Parcel in) {
    super(in);
    mDate = in.readString();
    mDayOfWeek = in.readString();
    mMeals = in.readParcelable(Meals.class.getClassLoader());
    mNotes = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mDate);
    dest.writeString(mDayOfWeek);
    dest.writeParcelable(mMeals, flags);
    dest.writeString(mNotes);
  }

  public static final Creator<Menu> CREATOR = new Creator<Menu>() {
    @Override
    public Menu createFromParcel(final Parcel in) {
      return new Menu(in);
    }

    @Override
    public Menu[] newArray(final int size) {
      return new Menu[size];
    }
  };

  /**
   * Date of the menu (Y-m-d)
   */
  public Date getDate() {
    return DateUtils.parseDate(mDate, DateUtils.YMD);
  }

  /**
   * Date of the menu (Y-m-d) as a string
   */
  public String getRawDate() {
    return mDate;
  }

  /**
   * Day of the week
   */
  public String getDayOfWeek() {
    return mDayOfWeek;
  }

  /**
   * All the meals for the day
   */
  public Meals getMeals() {
    return mMeals;
  }

  /**
   * Additional announcements for the day
   */
  public String getNotes() {
    return mNotes;
  }
}
