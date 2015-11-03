package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.courses.CourseInfo;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;

public class CourseInfoView extends BaseCourseView {

    private TextView mTitle;
    private TextView mDescription;

    public CourseInfoView(final Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_course_info;
    }

    @Override
    protected void findViews() {
        mTitle = (TextView) findViewById(R.id.course_info_title);
        mDescription = (TextView) findViewById(R.id.course_info_description);
    }

    @Override
    public void bind(final CombinedCourseInfo info) {
        final CourseInfo courseInfo = info.getCourseInfo();

        mTitle.setText(courseInfo.getTitle());
        mDescription.setText(courseInfo.getDescription());
    }
}
