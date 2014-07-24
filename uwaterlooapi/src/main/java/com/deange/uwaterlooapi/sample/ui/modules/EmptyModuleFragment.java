package com.deange.uwaterlooapi.sample.ui.modules;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.SimpleResponse;
import com.deange.uwaterlooapi.sample.model.FragmentInfo;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;

public class EmptyModuleFragment extends BaseModuleFragment<SimpleResponse<Void>, Void> {

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        return null;
    }

    @Override
    public SimpleResponse<Void> onLoadData(final UWaterlooApi api) {
        return null;
    }

    @Override
    public void onBindData(final Metadata metadata, final Void data) {

    }

    @Override
    public FragmentInfo getFragmentInfo(final Context context) {
        return null;
    }
}
