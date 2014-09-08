package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public abstract class StickyModuleAdapter extends ModuleAdapter implements StickyListHeadersAdapter {

    private Context mContext;

    public StickyModuleAdapter(final Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View getHeaderView(final int position, final View convertView, final ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = LayoutInflater.from(mContext).inflate(getHeaderLayoutId(), null);
        } else {
            v = convertView;
        }
        bindHeaderView(mContext, position, v);
        return v;
    }

    public int getHeaderLayoutId() {
        return R.layout.list_item_header;
    }

    public abstract void bindHeaderView(final Context context, final int position, final View view);
}
