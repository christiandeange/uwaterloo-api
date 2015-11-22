package com.deange.uwaterlooapi.sample.model;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.courses.CourseInfo;
import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.model.courses.ExamInfo;
import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class CombinedCourseInfo extends BaseModel {

    private Metadata mMetadata;
    private CourseInfo mCourseInfo;
    private PrerequisiteInfo mPrerequisites;
    private List<CourseSchedule> mSchedules;
    private ExamInfo mExams;

    public Metadata getMetadata() {
        return mMetadata;
    }

    public void setMetadata(Metadata metadata) {
        mMetadata = metadata;
    }

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

    public List<CourseSchedule> getSchedules() {
        return mSchedules;
    }

    public void setSchedules(final List<CourseSchedule> schedules) {
        mSchedules = new ArrayList<>(schedules);
    }

    public ExamInfo getExams() {
        return mExams;
    }

    public void setExams(final ExamInfo exams) {
        mExams = exams;
    }
}
