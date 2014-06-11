package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class OperatingHours extends BaseModel {

    public static final String SUNDAY = "sunday";
    public static final String MONDAY = "monday";
    public static final String TUESDAY = "tuesday";
    public static final String WEDNESDAY = "wednesday";
    public static final String THURSDAY = "thursday";
    public static final String FRIDAY = "friday";
    public static final String SATURDAY = "saturday";

    public static final String TIME_FORMAT = "hh:mm";

    @SerializedName("opening_hour")
    private String mOpeningHour;

    @SerializedName("closing_hour")
    private String mClosingHour;

    @SerializedName("is_closed")
    private boolean mClosedAllDay;

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
