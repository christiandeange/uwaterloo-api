package com.deange.uwaterlooapi.sample.ui.modules.courses;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.courses.Course;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;

import java.util.List;

public class CourseAdapter
        extends ModuleAdapter
        implements
        SectionIndexer {

    private final String[] mSections;
    private final List<Course> mCourses;

    public CourseAdapter(
            final Context context,
            final List<Course> courses,
            final ModuleListItemListener listener) {
        super(context, listener);
        mCourses = courses;

        mSections = new String[mCourses.size()];
        for (int i = 0; i < mCourses.size(); i++) {
            mSections[i] = mCourses.get(i).getCatalogNumber();
        }
    }

    @Override
    public void bindView(final Context context, final int position, final View view) {
        final Course course = getItem(position);
        final String courseCode = course.getSubject() + " " + course.getCatalogNumber();

        ((TextView) view.findViewById(android.R.id.text1)).setText(courseCode);
        ((TextView) view.findViewById(android.R.id.text2)).setText(course.getTitle());
    }

    @Override
    public int getCount() {
        return mCourses.size();
    }

    @Override
    public Course getItem(final int position) {
        return mCourses.get(position);
    }

    @Override
    public int getListItemLayoutId() {
        return R.layout.simple_two_line_card_item;
    }

    @Override
    public Object[] getSections() {
        return mSections;
    }

    @Override
    public int getPositionForSection(final int sectionIndex) {
        return sectionIndex;
    }

    @Override
    public int getSectionForPosition(final int position) {
        return position;
    }
}
