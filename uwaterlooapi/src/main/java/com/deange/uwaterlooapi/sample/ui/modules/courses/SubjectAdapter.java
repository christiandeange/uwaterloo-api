package com.deange.uwaterlooapi.sample.ui.modules.courses;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.courses.Course;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.common.ContainsFilter;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;

import java.util.Arrays;
import java.util.List;

public class SubjectAdapter
        extends ArrayAdapter<String> {

    private Filter mFilter;

    public SubjectAdapter(final Context context) {
        super(context, android.R.layout.simple_list_item_1);

        final String[] subjects = context.getResources().getStringArray(R.array.course_subjects);
        mFilter = new ContainsFilter(this, Arrays.asList(subjects));
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
