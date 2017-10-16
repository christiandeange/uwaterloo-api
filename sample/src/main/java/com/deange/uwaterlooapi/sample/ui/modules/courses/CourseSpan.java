package com.deange.uwaterlooapi.sample.ui.modules.courses;

import android.content.Context;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.view.View;

import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;

public class CourseSpan
    extends ClickableSpan {

  private final String mSubject;
  private final String mCode;

  public CourseSpan(final String subject, final String code) {
    mSubject = subject;
    mCode = code;
  }

  @Override
  public void onClick(final View view) {
    final Context context = view.getContext();

    final Intent intent = ModuleHostActivity.getStartIntent(
        context,
        CourseFragment.class,
        CourseFragment.newBundle(mSubject, mCode));

    context.startActivity(intent);
  }
}
