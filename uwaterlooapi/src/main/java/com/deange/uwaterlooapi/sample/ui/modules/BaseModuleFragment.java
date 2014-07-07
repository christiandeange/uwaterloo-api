package com.deange.uwaterlooapi.sample.ui.modules;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.SimpleResponse;
import com.deange.uwaterlooapi.sample.R;

public abstract class BaseModuleFragment<T extends SimpleResponse<V>, V> extends Fragment implements View.OnClickListener {

    private long mLastUpdated = 0;

    public BaseModuleFragment() {
        // Required constructor
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_module, container, false);
        final View contentView = getContentView(inflater, savedInstanceState);

        if (contentView != null) {
            ((ViewGroup) root.findViewById(R.id.container_content_view)).addView(contentView);
        }

        return root;
    }

    protected abstract View getContentView(final LayoutInflater inflater,
                                           final Bundle savedInstanceState);

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View root = getView();
        root.findViewById(R.id.refresh_button).setOnClickListener(this);

        // Show data when first displayed
        if (mLastUpdated == 0) {
            onRefreshRequested();
        }
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.refresh_button) {
            // Refresh manually requested
            onRefreshRequested();
        }
    }

    protected void onRefreshRequested() {
        mLastUpdated = System.currentTimeMillis();
        final UWaterlooApi api = ((SampleHostFragment) getParentFragment()).getApi();
        new LoadModuleDataTask().execute(api);
    }

    protected void onLoadFinished() {
        // Subclasses can override this if they want to
    }

    public abstract T onLoadData(final UWaterlooApi api);

    public abstract void onBindData(final Metadata metadata, final V data);

    private final class LoadModuleDataTask extends AsyncTask<UWaterlooApi, Void, T> {

        @Override
        protected T doInBackground(final UWaterlooApi... apis) {
            // Performed on a background thread, so network calls are performed here
            return onLoadData(apis[0]);
        }
        @Override
        protected void onPostExecute(final T data) {
            // Performed on the main thread, so view manipulation is performed here
            onLoadFinished();
            onBindData(data.getMetadata(), data.getData());
        }

    }
}
