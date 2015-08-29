package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.courses.CourseInfo;
import com.deange.uwaterlooapi.sample.R;

public class CourseInfoView extends FrameLayout {

    private final TextView mTitle;
    private final TextView mDescription;

    public CourseInfoView(final Context context) {
        this(context, null);
    }

    public CourseInfoView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseInfoView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayoutParams(generateDefaultLayoutParams());
        inflate(getContext(), R.layout.view_course_info, this);

        mTitle = (TextView) findViewById(R.id.course_info_title);
        mDescription = (TextView) findViewById(R.id.course_info_description);
    }

    public void bind(final CourseInfo info) {
        mTitle.setText(info.getTitle());
        mDescription.setText(info.getDescription());
    }

}
