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

    public String getCourseId() {
        return mCourseId;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getCatalogNumber() {
        return mCatalogNumber;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public float getUnits() {
        return mUnits;
    }

    public String getAcademicLevel() {
        return mAcademicLevel;
    }
}
