package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;
import com.deange.uwaterlooapi.sample.ui.modules.courses.CourseSpan;
import com.deange.uwaterlooapi.sample.utils.Joiner;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import butterknife.Bind;

public class PrerequisitesView extends BaseCourseView {

    @Bind(R.id.prerequisite_info_description) TextView mDescription;
    @Bind(R.id.prerequisite_info_courses) TextView mCourses;

    public PrerequisitesView(final Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_prerequisite_info;
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

        final List<String> allCourses = getAllCourses(prerequisiteInfo);
        mCourses.setVisibility(allCourses.isEmpty() ? View.GONE : View.VISIBLE);

        final String courseList = Joiner.on("    \t").join(allCourses);

        final Spannable spannable = Spannable.Factory.getInstance().newSpannable(courseList);
        for (final String course : allCourses) {
            int end = 0;
            while (end < course.length() && Character.isAlphabetic(course.charAt(end))) {
                end++;
            }

            final String subject = course.substring(0, end);
            final String code = course.substring(end);

            final int start = courseList.indexOf(course);
            spannable.setSpan(new CourseSpan(subject, code), start, start + course.length(), 0);
        }

        mCourses.setMovementMethod(LinkMovementMethod.getInstance());
        mCourses.setText(spannable);
    }

    private List<String> getAllCourses(final PrerequisiteInfo info) {
        final Set<String> courses = new HashSet<>();
        final Queue<PrerequisiteInfo.PrerequisiteGroup> groups = new ArrayDeque<>();
        final PrerequisiteInfo.PrerequisiteGroup initialGroup = info.getPrerequisiteGroup();

        if (initialGroup != null) {
            groups.add(initialGroup);
            while (!groups.isEmpty()) {
                final PrerequisiteInfo.PrerequisiteGroup group = groups.poll();

                courses.addAll(group.getOptions());
                groups.addAll(group.getSubOptions());
            }
        }

        final ArrayList<String> courseList = new ArrayList<>(courses);
        Collections.sort(courseList);
        return courseList;
    }

}
