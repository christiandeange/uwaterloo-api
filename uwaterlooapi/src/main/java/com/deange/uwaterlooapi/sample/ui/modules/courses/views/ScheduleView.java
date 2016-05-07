package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.widget.ListView;

import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class ScheduleView extends BaseCourseView {

    @BindView(R.id.schedule_list_view) ListView mListView;

    public ScheduleView(final Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_schedule_info;
    }

    @Override
    public void bind(final CombinedCourseInfo info) {
        final List<CourseSchedule> schedules = info.getSchedules();

        Collections.sort(schedules, new Comparator<CourseSchedule>() {
            @Override
            public int compare(final CourseSchedule lhs, final CourseSchedule rhs) {
                return lhs.getSection().compareToIgnoreCase(rhs.getSection());
            }
        });

        mListView.setAdapter(new ScheduleAdapter(getContext(), schedules));
    }
}
