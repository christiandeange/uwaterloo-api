package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Meal
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("product_id")
  int mId;

  @SerializedName("product_name")
  String mName;

  @SerializedName("diet_type")
  String mDietType;

  protected Meal(final Parcel in) {
    super(in);
    mId = in.readInt();
    mName = in.readString();
    mDietType = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mName);
    dest.writeString(mDietType);
  }

  public static final Creator<Meal> CREATOR = new Creator<Meal>() {
    @Override
    public Meal createFromParcel(final Parcel in) {
      return new Meal(in);
    }

    @Override
    public Meal[] newArray(final int size) {
      return new Meal[size];
    }
  };

  /**
   * The ID of the meal
   */
  public int getId() {
    return mId;
  }

  /**
   * The name of the meal
   */
  public String getName() {
    return mName;
  }

  /**
   * The diet type of the meal
   */
  public String getDietType() {
    return mDietType;
  }
}
