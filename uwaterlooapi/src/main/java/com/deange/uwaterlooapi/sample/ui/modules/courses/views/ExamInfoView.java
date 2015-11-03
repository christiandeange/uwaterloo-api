package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.util.AttributeSet;

import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.model.courses.ExamInfo;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;

public class ExamInfoView extends BaseCourseView {

    public ExamInfoView(final Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void findViews() {

    }

    @Override
    public void bind(final CombinedCourseInfo info) {
        final ExamInfo examInfo = info.getExams();
    }
}
