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

    public StringAdapter(final Context context, final List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_string_adapter, null);
        } else {
            view = convertView;
        }

        ((TextView) view).setText(String.valueOf(getItem(position)));

        return view;
    }
}
