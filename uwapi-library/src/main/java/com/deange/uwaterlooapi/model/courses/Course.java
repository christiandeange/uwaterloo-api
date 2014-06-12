package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Course extends BaseModel {

    @SerializedName("course_id")
    private String mCourseId;

    @SerializedName("subject")
    private String mSubject;

    @SerializedName("catalog_number")
    private String mCatalogNumber;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("units")
    private float mUnits;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("academic_level")
    private String mAcademicLevel;

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
