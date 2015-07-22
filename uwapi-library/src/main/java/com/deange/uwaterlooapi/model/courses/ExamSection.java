package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class ExamSection extends BaseModel {

    @SerializedName("section")
    String mSection;

    @SerializedName("day")
    String mDay;

    @SerializedName("date")
    String mDate;

    @SerializedName("start_time")
    String mStartTime;

    @SerializedName("end_time")
    String mEndTime;

    @SerializedName("location")
    String mLocation;

    @SerializedName("notes")
    String mNotes;

    /**
     * Exam section number
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Day of the exam
     */
    public String getDay() {
        return mDay;
    }

    /**
     * ISO8601 exam date representation
     */
    public Date getDate() {
        return Formatter.parseDate(mDate, Formatter.YMD);
    }

    /**
     * ISO8601 exam date representation as a string
     */
    public String getRawDate() {
        return mDate;
    }

    /**
     * Exam starting time
     */
    public String getStartTime() {
        return mStartTime;
    }

    /**
     * Exam ending time
     */
    public String getEndTime() {
        return mEndTime;
    }

    /**
     * Exam location
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Additional notes regarding the section
     */
    public String getNotes() {
        return mNotes;
    }
}
