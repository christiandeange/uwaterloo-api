package com.deange.uwaterlooapi.sample.model;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.courses.CourseInfo;
import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.model.courses.ExamInfo;
import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo;

import org.parceler.Parcel;

@Parcel
public class CombinedCourseInfo extends BaseModel {

    private CourseInfo mCourseInfo;
    private PrerequisiteInfo mPrerequisites;
    private CourseSchedule mSchedule;
    private ExamInfo mExams;

    public CourseInfo getCourseInfo() {
        return mCourseInfo;
    }

    public void setCourseInfo(final CourseInfo courseInfo) {
        mCourseInfo = courseInfo;
    }

    public PrerequisiteInfo getPrerequisites() {
        return mPrerequisites;
    }

    public void setPrerequisites(final PrerequisiteInfo prerequisites) {
        mPrerequisites = prerequisites;
    }

    public CourseSchedule getSchedule() {
        return mSchedule;
    }

    public void setSchedule(final CourseSchedule schedule) {
        mSchedule = schedule;
    }

    public ExamInfo getExams() {
        return mExams;
    }

    public void setExams(final ExamInfo exams) {
        mExams = exams;
    }
}
