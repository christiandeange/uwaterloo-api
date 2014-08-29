package com.deange.uwaterlooapi.sample.ui.modules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;

public class ApiMethodsAdapter extends ArrayAdapter<String> {

    final LayoutInflater mInflater;
    final int mResId;

    public ApiMethodsAdapter(final Context context, final int resource, final String[] objects) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
        mResId = resource;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final View view;
        if (convertView == null) {
            view = mInflater.inflate(mResId, parent, false);
        } else {
            view = convertView;
        }

        final TextView text = (TextView) view.findViewById(android.R.id.text1);
        final ImageView image = (ImageView) view.findViewById(R.id.item_api_method_icon);

        final String endpoint = getItem(position);
        final ModuleInfo info = ModuleResolver.getFragmentInfo(endpoint);
        text.setText(endpoint);

        if (info != null) {
            image.setImageResource(info.icon);
        }

        return view;

    }
}
