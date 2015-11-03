package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;

public class PrerequisitesView extends BaseCourseView {

    private TextView mDescription;

    public PrerequisitesView(final Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_prerequisite_info;
    }

    @Override
    protected void findViews() {
        mDescription = (TextView) findViewById(R.id.prerequisite_info_description);
    }

    @Override
    public void bind(final CombinedCourseInfo info) {
        final PrerequisiteInfo prerequisiteInfo = info.getPrerequisites();

        final String prerequisites = prerequisiteInfo.getPrerequisites();
        if (TextUtils.isEmpty(prerequisites)) {
            mDescription.setText(R.string.course_prerequisites_none);
        } else {
            mDescription.setText(prerequisites);
        }
    }
}
