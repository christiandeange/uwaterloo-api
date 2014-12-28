package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ModuleAdapter extends BaseAdapter {

    protected Context mContext;

    public ModuleAdapter(final Context context) {
        super();
        mContext = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = newView(mContext, position, parent);
        } else {
            v = convertView;
        }
        bindView(mContext, position, v);
        return v;
    }

    @Override
    public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = newDropDownView(mContext, position, parent);
        } else {
            v = convertView;
        }
        bindView(mContext, position, v);
        return v;
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    public View newDropDownView(final Context context, final int position, final ViewGroup parent) {
        return newView(context, position, parent);
    }

    public View newView(final Context context, final int position, final ViewGroup parent) {
        return LayoutInflater.from(context).inflate(getListItemLayoutId(), null);
    }

    public int getListItemLayoutId() {
        return android.R.layout.simple_list_item_1;
    }

    public abstract void bindView(final Context context, final int position, final View view);
}
