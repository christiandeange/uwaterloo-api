package com.deange.uwaterlooapi.sample.ui.modules.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.deange.uwaterlooapi.model.common.SimpleResponse;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;

import java.util.List;

public abstract class BaseListModuleFragment<T extends SimpleResponse<List<V>>, V>
        extends BaseModuleFragment<T, List<V>> implements SwipeRefreshLayout.OnRefreshListener {

    private ListView mListView;
    private SwipeRefreshLayout mSwipeLayout;

    private long mLastUpdate;
    private final Handler mHandler = new Handler();

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_base_list, null);

        mListView = (ListView) root.findViewById(R.id.list_view);

        mSwipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.fragment_swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(
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

    public ListView getListView() {
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
//        mSwipeLayout.setRefreshing(true);
        mLastUpdate = System.currentTimeMillis();

        super.onRefreshRequested();
    }

    public abstract ModuleAdapter getAdapter();
}
