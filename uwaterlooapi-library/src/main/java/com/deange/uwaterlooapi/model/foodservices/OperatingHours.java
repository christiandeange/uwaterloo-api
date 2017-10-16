package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class OperatingHours
    extends BaseModel
    implements
    Parcelable {

  public static final String SUNDAY = "sunday";
  public static final String MONDAY = "monday";
  public static final String TUESDAY = "tuesday";
  public static final String WEDNESDAY = "wednesday";
  public static final String THURSDAY = "thursday";
  public static final String FRIDAY = "friday";
  public static final String SATURDAY = "saturday";

  // Corresponds to Calendar.DAY_OF_WEEK values
  public static final List<String> WEEKDAYS = Arrays.asList(
      null, SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
  );

  public static final String TIME_FORMAT = "HH:mm";

  @SerializedName("opening_hour")
  String mOpeningHour;

  @SerializedName("closing_hour")
  String mClosingHour;

  @SerializedName("is_closed")
  boolean mClosedAllDay;

  protected OperatingHours() {
  }

  protected OperatingHours(final Parcel in) {
    super(in);
    mOpeningHour = in.readString();
    mClosingHour = in.readString();
    mClosedAllDay = in.readByte() != 0;
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mOpeningHour);
    dest.writeString(mClosingHour);
    dest.writeByte((byte) (mClosedAllDay ? 1 : 0));
  }

  public static final Creator<OperatingHours> CREATOR = new Creator<OperatingHours>() {
    @Override
    public OperatingHours createFromParcel(final Parcel in) {
      return new OperatingHours(in);
    }

    @Override
    public OperatingHours[] newArray(final int size) {
      return new OperatingHours[size];
    }
  };

  public static OperatingHours create(
      final String openingHour,
      final String closingHour,
      final boolean closedAllDay) {
    final OperatingHours operatingHours = new OperatingHours();
    operatingHours.mOpeningHour = openingHour;
    operatingHours.mClosingHour = closingHour;
    operatingHours.mClosedAllDay = closedAllDay;
    return operatingHours;
  }

  /**
   * Locations opening time {@link #TIME_FORMAT (H:i format)}
   */
  public String getOpeningHour() {
    return mOpeningHour;
  }

  /**
   * Locations closing time {@link #TIME_FORMAT (H:i format)}
   */
  public String getClosingHour() {
    return mClosingHour;
  }

  /**
   * If the location is closed on that day
   */
  public boolean isClosedAllDay() {
    return mClosedAllDay;
  }
}
