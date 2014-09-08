package com.deange.uwaterlooapi.sample.ui.modules.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.deange.uwaterlooapi.model.common.SimpleResponse;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.StickyModuleAdapter;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public abstract class BaseStickyListModuleFragment<T extends SimpleResponse<List<V>>, V>
        extends BaseModuleFragment<T, List<V>> implements SwipeRefreshLayout.OnRefreshListener {

    private StickyListHeadersListView mListView;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {

        final View root = inflater.inflate(getLayoutId(), null);

        mListView = (StickyListHeadersListView) root.findViewById(android.R.id.list);
        if (mListView == null) {
            throw new IllegalStateException("ListView must have id android.R.id.list");
        }

        mSwipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.fragment_swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_orange_dark);

        return root;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setAdapter(getAdapter());
    }

    protected int getLayoutId() {
        return R.layout.fragment_base_sticky_list;
    }

    public StickyListHeadersListView getListView() {
        return mListView;
    }

    public void notifyDataSetChanged() {
        if (mListView.getAdapter() instanceof BaseAdapter) {
            ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    protected void changeLoadingVisibility(final boolean show) {
        mSwipeLayout.setRefreshing(show);
        mSwipeLayout.setEnabled(!show);
    }

    @Override
    public void onRefresh() {
        onRefreshRequested();
    }

    @Override
    protected void onRefreshRequested() {
        super.onRefreshRequested();
    }

    public abstract StickyModuleAdapter getAdapter();
}
