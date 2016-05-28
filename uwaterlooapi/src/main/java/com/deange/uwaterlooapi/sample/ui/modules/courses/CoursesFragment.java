package com.deange.uwaterlooapi.sample.ui.modules.courses;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.courses.Course;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.common.UpperCaseTextWatcher;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

@ModuleFragment(
        path = "/courses/*",
        layout = R.layout.module_courses
)
public class CoursesFragment
        extends BaseListModuleFragment<Response.Courses, Course>
        implements
        ModuleListItemListener,
        AdapterView.OnItemClickListener,
        TextView.OnEditorActionListener {

    private static final String KEY_COURSE_SUBJECT = "subject";

    private final Runnable mResetListViewRunnable = new Runnable() {
        @Override
        public void run() {
            getListView().setSelectionFromTop(0, 0);
        }
    };
    private final List<Course> mResponse = new ArrayList<>();

    @BindView(R.id.course_picker_view) AutoCompleteTextView mCoursePicker;

    public static <V extends BaseModel> Bundle newBundle(final String subject) {
        final Bundle bundle = new Bundle();
        bundle.putString(KEY_COURSE_SUBJECT, subject);
        return bundle;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_courses;
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
        final View view = super.getContentView(inflater, parent);

        ButterKnife.bind(this, view);

        mCoursePicker.setAdapter(new SubjectAdapter(getActivity()));
        mCoursePicker.setOnItemClickListener(this);
        mCoursePicker.addTextChangedListener(new UpperCaseTextWatcher(mCoursePicker));
        mCoursePicker.setOnEditorActionListener(this);

        // May have a preloaded course passed in
        mCoursePicker.setText(getSubject());

        return view;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_courses);
    }

    @Override
    public ModuleAdapter getAdapter() {
        return new CourseAdapter(getActivity(), this);
    }

    @Override
    public Call<Response.Courses> onLoadData(final UWaterlooApi api) {
        final String course = mCoursePicker.getText().toString();

        postDelayed(mResetListViewRunnable, ANIMATION_DURATION);

        if (TextUtils.isEmpty(course)) {
            return null;

        } else {
            return api.Courses.getCourseInfo(course);
        }
    }

    @Override
    protected void onNullResponseReceived() {
        // Occurs when no search query is entered
    }

    @Override
    public void onBindData(final Metadata metadata, final List<Course> data) {
        mResponse.clear();
        mResponse.addAll(data);

        if (metadata.getStatus() == 204) {
            if (getActivity() != null) {
                final String courseSubject = mCoursePicker.getText().toString();
                final String text = getString(R.string.course_no_info_available, courseSubject);
                Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
            }

        } else {
            Collections.sort(mResponse, new Comparator<Course>() {
                @Override
                public int compare(final Course lhs, final Course rhs) {
                    final String firstStr = lhs.getCatalogNumber();
                    final String secondStr = rhs.getCatalogNumber();
                    final int first = extractNumbers(lhs.getCatalogNumber());
                    final int second = extractNumbers(rhs.getCatalogNumber());

                    final int catalogNumberComp = Double.compare(first, second);
                    return (catalogNumberComp != 0) ? catalogNumberComp : firstStr.compareTo(secondStr);
                }
            });

            mCoursePicker.clearFocus();
            ViewUtils.hideKeyboard(getActivity());
        }

        getListView().setFastScrollEnabled(true);
        getListView().setFastScrollAlwaysVisible(true);
        notifyDataSetChanged();
    }

    @Override
    public String getContentType() {
        return ModuleType.COURSES;
    }

    private static int extractNumbers(final String catalog) {
        return Integer.parseInt(catalog.replaceAll("\\D+", ""));
    }

    // This is a destructive call, use wisely!
    private String getSubject() {
        final Bundle args = getArguments();
        if (args != null) {
            final String subject = args.getString(KEY_COURSE_SUBJECT);
            args.remove(KEY_COURSE_SUBJECT);
            return subject;

        } else {
            return null;
        }
    }

    @Override
    public void onItemClicked(final int position) {
        // Remove focus from the list item
        getListView().requestFocus();

        showModule(CourseFragment.class, CourseFragment.newBundle(mResponse.get(position)));
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        // Subject clicked from AutoCompleteTextView
        doRefresh();
    }

    @Override
    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mCoursePicker.dismissDropDown();

            doRefresh();
            return true;
        }

        return false;
    }

    public class CourseAdapter
            extends ModuleAdapter {

        public CourseAdapter(final Context context, final ModuleListItemListener listener) {
            super(context, listener);
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
            return mResponse.size();
        }

        @Override
        public Course getItem(final int position) {
            return mResponse.get(position);
        }

        @Override
        public int getListItemLayoutId() {
            return R.layout.simple_two_line_card_item;
        }
    }
}
