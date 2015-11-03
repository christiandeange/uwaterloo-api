package com.deange.uwaterlooapi.sample.ui.modules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleInfo;
import com.deange.uwaterlooapi.annotations.ModuleMap;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.ui.view.ModuleListItem;

public class ApiMethodsAdapter extends ArrayAdapter<String>
        implements View.OnClickListener {

    final LayoutInflater mInflater;
    private final ModuleListItemListener mListener;

    public ApiMethodsAdapter(
            final Context context,
            final String[] objects,
            final ModuleListItemListener listener) {
        super(context, 0, objects);
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final String endpoint = getItem(position);
        final ModuleInfo info = ModuleMap.getFragmentInfo(endpoint);

        final View view;
        if (convertView == null) {
            final int layoutId = (info == null || !info.isBase || info.layout == 0)
                    ? R.layout.list_item_string_adapter
                    : info.layout;
            view = mInflater.inflate(layoutId, parent, false);
        } else {
            view = convertView;
        }

        final View selectable = view.findViewById(R.id.selectable);
        if (selectable != null) {
            selectable.setTag(position);
            selectable.setOnClickListener(this);
        }

        // TODO Remove soon
        if (!(view instanceof ModuleListItem)) {
            ((TextView) view.findViewById(android.R.id.text1)).setText(endpoint);
        }

        return view;
    }

    @Override
    public boolean isEnabled(final int position) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();
        if (mListener != null) {
            mListener.onItemClicked(position);
        }
    }

}
