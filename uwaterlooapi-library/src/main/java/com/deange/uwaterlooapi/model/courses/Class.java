package com.deange.uwaterlooapi.model.courses;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Class
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("date")
  ClassDate mDate;

  @SerializedName("location")
  Location mLocation;

  @SerializedName("instructors")
  List<String> mInstructors;

  protected Class(final Parcel in) {
    super(in);
    mDate = in.readParcelable(ClassDate.class.getClassLoader());
    mLocation = in.readParcelable(Location.class.getClassLoader());
    mInstructors = in.createStringArrayList();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeParcelable(mDate, flags);
    dest.writeParcelable(mLocation, flags);
    dest.writeStringList(mInstructors);
  }

  public static final Creator<Class> CREATOR = new Creator<Class>() {
    @Override
    public Class createFromParcel(final Parcel in) {
      return new Class(in);
    }

    @Override
    public Class[] newArray(final int size) {
      return new Class[size];
    }
  };

  /**
   * Date for course schedule
   */
  public ClassDate getDate() {
    return mDate;
  }

  /**
   * Name of the building for the class location
   */
  public String getBuilding() {
    return mLocation == null ? null : mLocation.mBuilding;
  }

  /**
   * Room number from the building for the class location
   */
  public String getRoom() {
    return mLocation == null ? null : mLocation.mRoom;
  }

  /**
   * Names of instructors teaching the course
   */
  public List<String> getInstructors() {
    return mInstructors;
  }

  public static final class Location
      implements
      Parcelable {

    public Location() {
    }

    @SerializedName("building")
    String mBuilding;

    @SerializedName("room")
    String mRoom;

    protected Location(final Parcel in) {
      mBuilding = in.readString();
      mRoom = in.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
      dest.writeString(mBuilding);
      dest.writeString(mRoom);
    }

    @Override
    public int describeContents() {
      return 0;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
      @Override
      public Location createFromParcel(final Parcel in) {
        return new Location(in);
      }

      @Override
      public Location[] newArray(final int size) {
        return new Location[size];
      }
    };
  }
}
