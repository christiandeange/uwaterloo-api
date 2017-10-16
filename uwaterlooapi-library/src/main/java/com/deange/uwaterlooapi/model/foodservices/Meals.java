package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meals
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("lunch")
  List<Meal> mLunch;

  @SerializedName("dinner")
  List<Meal> mDinner;

  protected Meals(final Parcel in) {
    super(in);
    mLunch = in.createTypedArrayList(Meal.CREATOR);
    mDinner = in.createTypedArrayList(Meal.CREATOR);
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeTypedList(mLunch);
    dest.writeTypedList(mDinner);
  }

  public static final Creator<Meals> CREATOR = new Creator<Meals>() {
    @Override
    public Meals createFromParcel(final Parcel in) {
      return new Meals(in);
    }

    @Override
    public Meals[] newArray(final int size) {
      return new Meals[size];
    }
  };

  /**
   * Lunch menu items
   */
  public List<Meal> getLunch() {
    return mLunch;
  }

  /**
   * Dinner menu items
   */
  public List<Meal> getDinner() {
    return mDinner;
  }
}
