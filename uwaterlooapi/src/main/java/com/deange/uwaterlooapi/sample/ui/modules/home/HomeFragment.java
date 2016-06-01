package com.deange.uwaterlooapi.sample.ui.modules.home;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.Analytics;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.common.UpperCaseTextWatcher;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.courses.CourseFragment;
import com.deange.uwaterlooapi.sample.ui.modules.courses.CoursesFragment;
import com.deange.uwaterlooapi.sample.ui.modules.courses.SubjectAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.weather.WeatherFragment;
import com.deange.uwaterlooapi.sample.utils.PlatformUtils;
import com.deange.uwaterlooapi.sample.utils.SimpleTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class HomeFragment
        extends Fragment
        implements
        AdapterView.OnItemClickListener {

    private final TextWatcher mCourseTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(final Editable s) {
            if (mSubjectPicker == null || mNumberPicker == null) {
                return;
            }

            final String subject = mSubjectPicker.getText().toString().trim();
            final String number = mNumberPicker.getText().toString().trim();
            final boolean validSubject = mAdapter.getSubjects().contains(subject);

            mSearchButton.setEnabled(validSubject);
            if (!validSubject) {
                return;
            }

            final String buttonText;
            if (TextUtils.isEmpty(number)) {
                buttonText = getString(R.string.home_quick_course_search_subject, subject);

            } else {
                buttonText = getString(R.string.home_quick_course_search_course, subject + " " + number);
            }

            mSearchButton.setText(buttonText);
        }
    };

    private float mElevation;
    private Toolbar mToolbar;
    private SubjectAdapter mAdapter;

    @BindView(R.id.home_course_subject) AutoCompleteTextView mSubjectPicker;
    @BindView(R.id.home_course_number) EditText mNumberPicker;
    @BindView(R.id.home_course_search) Button mSearchButton;
    @BindView(R.id.home_cards_parent) ViewGroup mCardsParent;

    private NearbyLocationsFragment mNearbyLocationsFragment;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(null);

        Analytics.view(ModuleType.HOME);
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        ButterKnife.bind(this, view);

        mCardsParent.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        mElevation = mToolbar.getElevation();
        mToolbar.setElevation(0f);

        mAdapter = new SubjectAdapter(getActivity());
        mSubjectPicker.setAdapter(mAdapter);
        mSubjectPicker.setOnItemClickListener(this);
        mSubjectPicker.addTextChangedListener(mCourseTextWatcher);
        mSubjectPicker.addTextChangedListener(new UpperCaseTextWatcher(mSubjectPicker));

        mNumberPicker.addTextChangedListener(mCourseTextWatcher);
        mNumberPicker.addTextChangedListener(new UpperCaseTextWatcher(mNumberPicker));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mNearbyLocationsFragment = (NearbyLocationsFragment)
                getChildFragmentManager().findFragmentById(R.id.home_nearby_locations_fragment);
    }

    @Override
    public void onDestroyView() {
        mToolbar.setElevation(mElevation);

        super.onDestroyView();
    }

    @OnClick(R.id.home_weather_selectable)
    public void onWeatherClicked() {
        startActivity(ModuleHostActivity.getStartIntent(getContext(), WeatherFragment.class));
    }

    @OnClick(R.id.home_course_search)
    public void onCourseSearchClicked() {
        final String subject = mSubjectPicker.getText().toString().trim();
        final String code = mNumberPicker.getText().toString().trim();

        final Intent intent;
        if (!TextUtils.isEmpty(code)) {
            intent = ModuleHostActivity.getStartIntent(
                    getContext(),
                    CourseFragment.class,
                    CourseFragment.newBundle(subject, code));
        } else {
            intent = ModuleHostActivity.getStartIntent(
                    getContext(),
                    CoursesFragment.class,
                    CoursesFragment.newBundle(subject));
        }

        startActivity(intent);
    }

    @OnEditorAction({ R.id.home_course_subject, R.id.home_course_number })
    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            mNumberPicker.requestFocus();
            return true;

        } else if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onCourseSearchClicked();
            return true;
        }

        return false;
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        mNumberPicker.requestFocus();
    }
}
