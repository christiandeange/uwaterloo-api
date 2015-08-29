package com.deange.uwaterlooapi.sample.ui.modules.courses;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;
import com.deange.uwaterlooapi.sample.ui.modules.courses.views.CourseInfoView;

public class CourseInfoAdapter extends PagerAdapter {

    private final Context mContext;
    private final CombinedCourseInfo mInfo;
    private final String[] mTitles;

    public CourseInfoAdapter(final Context context, final CombinedCourseInfo info) {
        mContext = context;
        mInfo = info;

        mTitles = mContext.getResources().getStringArray(R.array.course_info_titles);
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;

        switch (position) {
            case 0:  view = instantiateInfoView(); break;
            case 1:  view = instantiateInfoView(); break;
            case 2:  view = instantiateInfoView(); break;
            case 3:  view = instantiateInfoView(); break;
            default: view = null; break; // will not happen
        }

        container.addView(view);
        return view;
    }

    private View instantiateInfoView() {
        final CourseInfoView view = new CourseInfoView(mContext);
        view.bind(mInfo.getCourseInfo());
        return view;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
