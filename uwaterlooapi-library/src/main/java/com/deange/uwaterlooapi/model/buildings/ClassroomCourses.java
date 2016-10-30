package com.deange.uwaterlooapi.model.buildings;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class ClassroomCourses extends BaseModel {

    @SerializedName("class_number")
    int mClassNumber;

    @SerializedName("subject")
    String mSubject;

    @SerializedName("catalog_number")
    String mCatalogNumber;

    @SerializedName("title")
    String mTitle;

    @SerializedName("section")
    String mSection;

    @SerializedName("weekdays")
    String mWeekdays;

    @SerializedName("start_time")
    String mStartTime;

    @SerializedName("end_time")
    String mEndTime;

    @SerializedName("start_date")
    String mStartDate;

    @SerializedName("end_date")
    String mEndDate;

    @SerializedName("enrollment_total")
    int mTotalEnrollment;

    @SerializedName("instructors")
    List<String> mInstructors;

    @SerializedName("building")
    String mBuilding;

    @SerializedName("room")
    String mRoom;

    @SerializedName("term")
    int mTerm;

    @SerializedName("last_updated")
    String mUpdated;

    /**
     * Class Number
     */
    public int getClassNumber() {
        return mClassNumber;
    }

    /**
     * Course subject code
     */
    public String getSubject() {
        return mSubject;
    }

    /**
     * Catalog number
     */
    public String getCatalogNumber() {
        return mCatalogNumber;
    }

    /**
     * Course title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Course section number
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Course class days
     */
    public String getWeekdays() {
        return mWeekdays;
    }

    /**
     * Start time
     */
    public String getStartTime() {
        return mStartTime;
    }

    /**
     * End time
     */
    public String getEndTime() {
        return mEndTime;
    }

    /**
     * Start date
     */
    public String getStartDate() {
        return mStartDate;
    }

    /**
     * End date
     */
    public String getEndDate() {
        return mEndDate;
    }

    /**
     * Number of students currently enrolled in the section
     */
    public int getTotalEnrollment() {
        return mTotalEnrollment;
    }

    /**
     * List of instructors the individual meet
     */
    public List<String> getInstructors() {
        return CollectionUtils.applyPolicy(mInstructors);
    }

    /**
     * Building code of building where the individual meet is held
     */
    public String getBuilding() {
        return mBuilding;
    }

    /**
     * Room where the individual meet is held
     */
    public String getRoom() {
        return mRoom;
    }

    /**
     * Particular 4-month period within which sessions are defined
     */
    public int getTerm() {
        return mTerm;
    }

    /**
     * Server time at last update (in ISO 8601 format)
     */
    public Date getUpdated() {
        return Formatter.parseDate(mUpdated);
    }

    /**
     * Server time at last update (in ISO 8601 format) as a string
     */
    public String getRawUpdated() {
        return mUpdated;
    }
}
