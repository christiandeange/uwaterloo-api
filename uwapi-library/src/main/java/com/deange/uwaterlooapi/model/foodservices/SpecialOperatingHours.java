package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.common.DateRange;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SpecialOperatingHours extends OperatingHours {

    @SerializedName("date")
    private String mDate;

    public Date getDate() {
        return Formatter.parseDate(mDate, DateRange.YMD);
    }

    public String getRawDate() {
        return mDate;
    }

}
