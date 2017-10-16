package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Outlet
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("outlet_id")
  int mId;

  @SerializedName("outlet_name")
  String mName;

  @SerializedName("menu")
  List<Menu> mMenu;

  @SerializedName("has_breakfast")
  int mBreakfast;

  @SerializedName("has_lunch")
  int mLunch;

  @SerializedName("has_dinner")
  int mDinner;

  protected Outlet(final Parcel in) {
    super(in);
    mId = in.readInt();
    mName = in.readString();
    mMenu = in.createTypedArrayList(Menu.CREATOR);
    mBreakfast = in.readInt();
    mLunch = in.readInt();
    mDinner = in.readInt();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mName);
    dest.writeTypedList(mMenu);
    dest.writeInt(mBreakfast);
    dest.writeInt(mLunch);
    dest.writeInt(mDinner);
  }

  public static final Creator<Outlet> CREATOR = new Creator<Outlet>() {
    @Override
    public Outlet createFromParcel(final Parcel in) {
      return new Outlet(in);
    }

    @Override
    public Outlet[] newArray(final int size) {
      return new Outlet[size];
    }
  };

  /**
   * Foodservices ID for the outlet
   */
  public int getId() {
    return mId;
  }

  /**
   * Name of the outlet
   */
  public String getName() {
    return mName;
  }

  /**
   * The outlet menu list
   * <p/>
   * This field is only set for getMenu() request
   */
  public List<Menu> getMenu() {
    return mMenu;
  }

  /**
   * If serves breakfast
   * <p/>
   * This field is only set for getOutlets() request
   */
  public boolean servesBreakfast() {
    return convertBool(mBreakfast);
  }

  /**
   * If serves lunch
   * <p/>
   * This field is only set for getOutlets() request
   */
  public boolean servesLunch() {
    return convertBool(mLunch);
  }

  /**
   * If serves dinner
   * <p/>
   * This field is only set for getOutlets() request
   */
  public boolean servesDinner() {
    return convertBool(mDinner);
  }

  private static boolean convertBool(final int i) {
    return i != 0;
  }
}
