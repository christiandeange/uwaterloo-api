package com.deange.uwaterlooapi.sample.ui.modules.courses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deange.uwaterlooapi.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Responses;
import com.deange.uwaterlooapi.model.courses.Course;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;
import com.deange.uwaterlooapi.sample.model.responses.CombinedCourseInfoResponse;
import com.deange.uwaterlooapi.sample.net.Calls;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class CourseFragment
        extends BaseModuleFragment<CombinedCourseInfoResponse, CombinedCourseInfo> {

    private static final String KEY_COURSE_MODEL = "course";
    private static final String KEY_COURSE_SUBJECT = "subject";
    private static final String KEY_COURSE_CODE = "code";

    private static final int BEST_SIZE = Runtime.getRuntime().availableProcessors() * 2 - 1;
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(BEST_SIZE);

    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.tab_content) ViewPager mViewPager;
    private CourseInfoAdapter mAdapter;
    private CombinedCourseInfo mCourseData;

    public static Bundle newBundle(final Course model) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_COURSE_MODEL, model);
        return bundle;
    }

    public static Bundle newBundle(final String subject, final String code) {
        final Bundle bundle = new Bundle();
        bundle.putString(KEY_COURSE_SUBJECT, subject);
        bundle.putString(KEY_COURSE_CODE, code);
        return bundle;
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
        final View view = inflater.inflate(R.layout.view_tablayout_viewpager, parent, false);
        ButterKnife.bind(this, view);

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

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
    public float getToolbarElevationPx() {
        return 0;
    }

    @Override
    public Call<CombinedCourseInfoResponse> onLoadData(final UWaterlooApi api) {

        final Pair<String, String> courseSubject = getCourseSubject();
        final String subject = courseSubject.first;
        final String code = courseSubject.second;
        final CombinedCourseInfo info = new CombinedCourseInfo();
        final CombinedCourseInfoResponse response = new CombinedCourseInfoResponse(info);
        final Semaphore semaphore = new Semaphore(1 - 4);

        // General course info
        fetchCourseInfo(semaphore, () -> {
            final Responses.CoursesInfo infoResponse = Calls.unwrap(api.Courses.getCourseInfo(subject, code));
            info.setMetadata(infoResponse.getMetadata());
            info.setCourseInfo(infoResponse.getData());
        });

        // Prerequisite info
        fetchCourseInfo(semaphore,
                () -> info.setPrerequisites(Calls.unwrap(api.Courses.getPrerequisites(subject, code)).getData()));

        // Course scheduling info
        fetchCourseInfo(semaphore,
                () -> info.setSchedules(Calls.unwrap(api.Courses.getCourseSchedule(subject, code)).getData()));

        // Exam schedule info
        fetchCourseInfo(semaphore,
                () -> info.setExams(Calls.unwrap(api.Courses.getExamSchedule(subject, code)).getData()));

        try {
            // Wait until all data is loaded
            semaphore.acquire();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Calls.wrap(response);
    }

    @Override
    protected void onNoDataReturned() {
        final Pair<String, String> pair = getCourseSubject();
        final String courseSubject = pair.first + " " + pair.second;
        final String text = getString(R.string.course_no_info_available, courseSubject);
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();

        getActivity().finish();
    }

    @Override
    public void onBindData(final Metadata metadata, final CombinedCourseInfo data) {
        mCourseData = data;
        mAdapter = new CourseInfoAdapter(getActivity(), data);

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public String getContentType() {
        return ModuleType.COURSE;
    }

    @Override
    public String getToolbarTitle() {
        final Pair<String, String> subject = getCourseSubject();
        return subject.first + " " + subject.second;
    }

    private
    @Nullable
    Course getCourse() {
        return getArguments().getParcelable(KEY_COURSE_MODEL);
    }

    private Pair<String, String> getCourseSubject() {
        final Course course = getCourse();
        if (course != null) {
            return Pair.create(course.getSubject(), course.getCatalogNumber());
        }

        final Bundle args = getArguments();
        return Pair.create(args.getString(KEY_COURSE_SUBJECT), args.getString(KEY_COURSE_CODE));
    }

    private void fetchCourseInfo(
            final Semaphore semaphore,
            final InfoFetcher fetcher) {

        EXECUTOR.execute(() -> {
            try {
                fetcher.fetch();
            } finally {
                semaphore.release();
            }
        });

    }

    private interface InfoFetcher {
        void fetch();
    }

}
