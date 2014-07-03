package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MultidayDateRange extends BaseModel {

    @SerializedName("start")
    private String mStart;

    @SerializedName("end")
    private String mEnd;

    @SerializedName("start_day")
    private String mStartDay;

    @SerializedName("end_day")
    private String mEndDay;

    @SerializedName("start_time")
    private String mStartTime;

    @SerializedName("end_time")
    private String mEndTime;

    @SerializedName("start_date")
    private String mStartDate;

    @SerializedName("end_date")
    private String mEndDate;

    /**
     * ISO 8601 formatted start date
     */
    public Date getStart() {
        return Formatter.parseDate(mStart);
    }

    /**
     * ISO 8601 formatted start date as a string
     */
    public String getRawStart() {
        return mStart;
    }

    /**
     * ISO 8601 formatted end date
     */
    public Date getEnd() {
        return Formatter.parseDate(mEnd);
    }

    /**
     * ISO 8601 formatted end date as a string
     */
    public String getRawEnd() {
        return mEnd;
    }

    /**
     * Full name of day of week for start day, eg: Saturday
     */
    public String getStartDay() {
        return mStartDay;
    }

    /**
     * Full name of day of week for end day, eg: Saturday
     */
    public String getEndDay() {
        return mEndDay;
    }

    /**
     * HH:MM:SS 24 formatted start time, eg: 04:00:00
     */
    public String getStartTime() {
        return mStartTime;
    }

    /**
     * YYYY-MM-DD formatted end time, eg: 04:00:00
     */
    public String getEndTime() {
        return mEndTime;
    }

    /**
     * YYYY-MM-DD formatted start date, eg: 2014-09-27
     */
    public String getStartDate() {
        return mStartDate;
    }

    /**
     * YYYY-MM-DD formatted end date, eg: 2014-09-27
     */
    public String getEndDate() {
        return mEndDate;
    }
}
