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

    public String getOpeningHour() {
        return mOpeningHour;
    }

    public String getClosingHour() {
        return mClosingHour;
    }

    public boolean isClosedAllDay() {
        return mClosedAllDay;
    }
}
