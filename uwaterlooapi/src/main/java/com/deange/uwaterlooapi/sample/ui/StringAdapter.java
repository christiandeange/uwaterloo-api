package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;

import java.util.List;

public class StringAdapter extends ArrayAdapter {

    private int mViewId = R.layout.list_item_string_adapter;
    private int mDropdownId = R.layout.list_item_string_adapter;

    public StringAdapter(final Context context, final List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        return doGetView(position, convertView, parent, mViewId);
    }

    @Override
    public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
        return doGetView(position, convertView, parent, mDropdownId);
    }

    public void setViewLayoutId(final int viewId) {
        mViewId = viewId;
    }

    public void setDropdownLayoutId(final int dropdownId) {
        mDropdownId = dropdownId;
    }

    private View doGetView(final int position, final View convertView, final ViewGroup parent,
                           final int layoutResId) {
        final View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(layoutResId, null);
        } else {
            view = convertView;
        }

        final TextView textView;
        if (view instanceof TextView) {
            textView = (TextView) view;
        } else {
            textView = (TextView) view.findViewById(android.R.id.text1);
        }

        textView.setText(String.valueOf(getItem(position)));

        return view;
    }
}
