package com.deange.uwaterlooapi.sample.ui.modules.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.SimpleListResponse;
import com.deange.uwaterlooapi.model.common.SimpleResponse;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;

import java.util.Collections;
import java.util.List;

public abstract class BaseListModuleFragment<T extends SimpleListResponse<V>, V extends BaseModel>
        extends BaseModuleFragment<T, V> {

    private ListView mListView;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {

        final View root = inflater.inflate(getLayoutId(), null);

        mListView = (ListView) root.findViewById(android.R.id.list);
        if (mListView == null) {
            throw new IllegalStateException("ListView must have id android.R.id.list");
        }

        return root;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setAdapter(getAdapter());
    }

    protected int getLayoutId() {
        return R.layout.fragment_simple_listview;
    }

    public ListView getListView() {
        return mListView;
    }

    public void notifyDataSetChanged() {
        if (mListView.getAdapter() instanceof BaseAdapter) {
            ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public final void onBindData(final Metadata metadata, final V data) {
        onBindData(metadata, Collections.singletonList(data));
    }

    public abstract ModuleAdapter getAdapter();
}
