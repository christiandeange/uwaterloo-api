package com.deange.uwaterlooapi.sample.ui.modules.courses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.courses.Course;
import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfoResponse;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.modules.foodservices.MenuDayAdapter;

import org.parceler.Parcels;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class CourseFragment
        extends BaseModuleFragment<CombinedCourseInfoResponse, CombinedCourseInfo> {

    private static final String KEY_COURSE_MODEL = "course";
    private static final int BEST_SIZE = Runtime.getRuntime().availableProcessors() * 2 - 1;
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(BEST_SIZE);

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CourseInfoAdapter mAdapter;
    private CombinedCourseInfo mCourseData;

    public static Bundle newBundle(final Course model) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_COURSE_MODEL, Parcels.wrap(model));
        return bundle;
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.view_tablayout_viewpager, null);

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager = (ViewPager) view.findViewById(R.id.tab_content);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_open_browser, menu);
        syncRefreshMenuItem(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_browser) {
            final Uri url = Uri.parse(mCourseData.getCourseInfo().getUrl());
            startActivity(new Intent(Intent.ACTION_VIEW, url));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public CombinedCourseInfoResponse onLoadData(final UWaterlooApi api) {

        final Course course = getCourse();
        final String subject = course.getSubject();
        final String code = course.getCatalogNumber();
        final CombinedCourseInfo info = new CombinedCourseInfo();
        final CombinedCourseInfoResponse response = new CombinedCourseInfoResponse(info);
        final Semaphore semaphore = new Semaphore(1 - 4);

        // General course info
        fetchCourseInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                info.setCourseInfo(api.Courses.getCourseInfo(subject, code).getData());
            }
        });

        // Prerequisite info
        fetchCourseInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                info.setPrerequisites(api.Courses.getPrerequisites(subject, code).getData());
            }
        });

        // Course scheduling info
        fetchCourseInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                final List<CourseSchedule> schedules =
                        api.Courses.getCourseSchedule(subject, code).getData();
                info.setSchedule(
                        (schedules == null || schedules.isEmpty()) ? null : schedules.get(0));
            }
        });

        // Exam schedule info
        fetchCourseInfo(semaphore, new InfoFetcher() {
            @Override
            public void fetch() {
                info.setExams(api.Courses.getExamSchedule(subject, code).getData());
            }
        });

        try {
            // Wait until all data is loaded
            semaphore.acquire();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    @Override
    public void onBindData(final Metadata metadata, final CombinedCourseInfo data) {
        mCourseData = data;
        mAdapter = new CourseInfoAdapter(getActivity(), data);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public String getToolbarTitle() {
        final Course course = getCourse();
        return course.getSubject() + " " + course.getCatalogNumber();
    }

    private Course getCourse() {
        return Parcels.unwrap(getArguments().getParcelable(KEY_COURSE_MODEL));
    }

    private void fetchCourseInfo(
            final Semaphore semaphore,
            final InfoFetcher fetcher) {

        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    fetcher.fetch();
                } finally {
                    semaphore.release();
                }
            }
        });

    }

    private interface InfoFetcher {
        void fetch();
    }

}
