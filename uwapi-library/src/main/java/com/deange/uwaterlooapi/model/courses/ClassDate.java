package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class ClassDate extends BaseModel {

    @SerializedName("start_time")
    private String mStartTime;

    @SerializedName("end_time")
    private String mEndTime;

    @SerializedName("weekdays")
    private String mWeekdays;

    @SerializedName("start_date")
    private String mStartDate;

    @SerializedName("end_date")
    private String mEndDate;

    @SerializedName("is_tba")
    private boolean mIsTBA;

    @SerializedName("is_cancelled")
    private boolean mIsCancelled;

    @SerializedName("is_closed")
    private boolean mIsClosed;

    /**
     * 24 hour class starting time
     */
    public String getStartTime() {
        return mStartTime;
    }

    /**
     * 24 hour class ending time
     */
    public String getEndTime() {
        return mEndTime;
    }

    /**
     * Weekdays the course is offered
     */
    public String getWeekdays() {
        return mWeekdays;
    }

    /**
     * Additional starting date for course
     */
    public String getStartDate() {
        return mStartDate;
    }

    /**
     * Additional ending date for course
     */
    public String getEndDate() {
        return mEndDate;
    }

    /**
     * If the course schedule is TBA
     */
    public boolean isTBA() {
        return mIsTBA;
    }

    /**
     * If the course is cancelled for the term
     */
    public boolean isCancelled() {
        return mIsCancelled;
    }

    /**
     * If the course is closed for the term
     */
    public boolean isClosed() {
        return mIsClosed;
    }
}
