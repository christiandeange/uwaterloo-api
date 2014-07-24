package com.deange.uwaterlooapi.sample.model;

import android.content.Context;

public abstract class FragmentInfo  {

    private final Context mContext;

    public FragmentInfo(final Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    public abstract String getActionBarTitle();

}
