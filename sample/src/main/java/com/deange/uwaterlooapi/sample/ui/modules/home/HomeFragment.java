package com.deange.uwaterlooapi.sample.ui.modules.home;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.common.UpperCaseTextWatcher;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.ModuleKey;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.Screen;
import com.deange.uwaterlooapi.sample.ui.modules.courses.CourseFragment;
import com.deange.uwaterlooapi.sample.ui.modules.courses.CoursesFragment;
import com.deange.uwaterlooapi.sample.ui.modules.courses.SubjectAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.weather.WeatherFragment;
import com.deange.uwaterlooapi.sample.utils.SimpleTextWatcher;
import com.google.auto.value.AutoValue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class HomeFragment
    extends Screen<HomeFragment.Key>
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

  @Override
  public void onViewAttached(final View view) {
    getActivity().setTitle(null);

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
  }

  @Override
  protected void onViewDetached() {
    mToolbar.setElevation(mElevation);
  }

  @OnClick(R.id.home_weather_selectable)
  public void onWeatherClicked() {
    getContext().startActivity(
        ModuleHostActivity.getStartIntent(getContext(), WeatherFragment.class));
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

    getContext().startActivity(intent);
  }

  @OnEditorAction({R.id.home_course_subject, R.id.home_course_number})
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
  public void onItemClick(
      final AdapterView<?> parent,
      final View view,
      final int position,
      final long id) {
    mNumberPicker.requestFocus();
  }

  @Override
  public String getContentType() {
    return ModuleType.HOME;
  }

  @AutoValue
  public static abstract class Key extends ModuleKey {
    public static Key create() {
      return new AutoValue_HomeFragment_Key();
    }

    @Override
    public int layout() {
      return R.layout.fragment_home;
    }
  }
}
