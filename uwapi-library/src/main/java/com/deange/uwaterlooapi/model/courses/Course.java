package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Course extends BaseModel {

    @SerializedName("course_id")
    String mCourseId;

    @SerializedName("subject")
    String mSubject;

    @SerializedName("catalog_number")
    String mCatalogNumber;

    @SerializedName("title")
    String mTitle;

    @SerializedName("units")
    float mUnits;

    @SerializedName("description")
    String mDescription;

    @SerializedName("academic_level")
    String mAcademicLevel;

    /**
     * Registrar assigned course ID
     */
    public int getCourseId() {
        // Ensure it is not interpreted as octal
        return Integer.parseInt(mCourseId, 10);
    }

    /**
     * Requested subject acronym
     */
    public String getSubject() {
        return mSubject;
    }

    /**
     * Registrar assigned class number
     */
    public String getCatalogNumber() {
        return mCatalogNumber;
    }

    /**
     * Class name and title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Brief course description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Credit count for the mentioned course
     */
    public float getUnits() {
        return mUnits;
    }

    /**
     * Undergraduate or graduate course classification
     */
    public String getAcademicLevel() {
        return mAcademicLevel;
    }
}
