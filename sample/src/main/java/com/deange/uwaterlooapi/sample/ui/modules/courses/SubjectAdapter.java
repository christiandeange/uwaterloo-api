package com.deange.uwaterlooapi.sample.ui.modules.courses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.common.ContainsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubjectAdapter
    extends ArrayAdapter<String> {

  private Filter mFilter;
  private List<String> mSubjects;

  public SubjectAdapter(final Context context) {
    super(context, android.R.layout.simple_list_item_1);

    mSubjects = Arrays.asList(context.getResources().getStringArray(R.array.course_subjects));
    mFilter = new ContainsFilter(this, mSubjects);
  }

  public List<String> getSubjects() {
    return Collections.unmodifiableList(mSubjects);
  }

  @NonNull
  @Override
  public Filter getFilter() {
    return mFilter;
  }
}
