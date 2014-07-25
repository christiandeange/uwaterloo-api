package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamInfo extends BaseModel {

    @SerializedName("course")
    private String mCourse;

    @SerializedName("sections")
    private List<ExamSection> mSections;

    public String getCourse() {
        return mCourse;
    }

    public List<ExamSection> getSections() {
        return CollectionUtils.applyPolicy(mSections);
    }
}
