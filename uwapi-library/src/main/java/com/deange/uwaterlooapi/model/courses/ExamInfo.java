package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ExamInfo extends BaseModel {

    @SerializedName("course")
    String mCourse;

    @SerializedName("sections")
    List<ExamSection> mSections;

    public String getCourse() {
        return mCourse;
    }

    public List<ExamSection> getSections() {
        return CollectionUtils.applyPolicy(mSections);
    }
}
