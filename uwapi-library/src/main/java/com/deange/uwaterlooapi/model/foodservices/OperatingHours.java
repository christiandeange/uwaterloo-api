package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class OperatingHours extends BaseModel {

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
