package com.deange.uwaterlooapi.sample.ui.modules.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.SimpleResponse;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.utils.Parceller;

//import android.view.ViewAnimationUtils;

public abstract class BaseModuleFragment<T extends SimpleResponse<V>, V> extends Fragment
        implements View.OnTouchListener {

    public static final long MINIMUM_UPDATE_DURATION = 1000;
    public static final long ANIMATION_DURATION = 300;

    private static final String KEY_DATA = "data";
    private static final String KEY_LAST_UPDATED = "last_updated";

    private long mLastUpdate = 0;
    private ViewGroup mLoadingLayout;

    private final Handler mHandler = new Handler();
    private T mLastResponse;
    private LoadModuleDataTask mTask;

    public BaseModuleFragment() {
        // Required constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof ModuleHostActivity)) {
            throw new RuntimeException("Parent activity not an instance of ModuleHostActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mTask != null) {
            mTask.cancel(true);
            mTask = null;
        }
    }

    public void showFragment(final BaseModuleFragment fragment, final boolean addToBackStack,
                             final Bundle arguments) {
        ((ModuleHostActivity) getActivity()).showFragment(fragment, addToBackStack, arguments);
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_module, container, false);
        final View contentView = getContentView(inflater, savedInstanceState);

        mLoadingLayout = (ViewGroup) root.findViewById(R.id.loading_layout);
        mLoadingLayout.setOnTouchListener(this);

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

        if (savedInstanceState != null) {
            mLastUpdate = savedInstanceState.getLong(KEY_LAST_UPDATED);
            mLastResponse = Parceller.unparcel(savedInstanceState.getString(KEY_DATA));
        }

        // Deliver the response if we still have one, otherwise load the data
        // (usually from coming back from another activity or rotating)
        if (mLastResponse != null) {
            deliverResponse(mLastResponse);

        } else if (mLastUpdate == 0) {
            onRefreshRequested();
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_base_module, menu);

        final MenuItem refreshItem = menu.findItem(R.id.menu_refresh);
        refreshItem.setVisible(!isRefreshing());
        refreshItem.setEnabled(!isRefreshing());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            // Refresh manually requested
            onRefreshRequested();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_LAST_UPDATED, mLastUpdate);
        outState.putString(KEY_DATA, Parceller.parcel(mLastResponse));
    }

    protected void onRefreshRequested() {
        changeLoadingVisibilityInternal(true);

        mLastUpdate = System.currentTimeMillis();
        final UWaterlooApi api = ((ModuleHostActivity) getActivity()).getApi();
        mTask = new LoadModuleDataTask();
        mTask.execute(api);
    }

    private void changeLoadingVisibilityInternal(final boolean show) {

        // Allow the refresh menu item to be updated
        getActivity().supportInvalidateOptionsMenu();

        changeLoadingVisibility(show);
    }

    protected void changeLoadingVisibility(final boolean show) {

        final View loadingLayout = mLoadingLayout;
        if (mLastUpdate == 0) {
            loadingLayout.setVisibility(View.VISIBLE);
            return;
        }

        if (show) {
            loadingLayout.setVisibility(View.VISIBLE);
        }

        final AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                loadingLayout.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(final Animator animation) {
                loadingLayout.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final int full = Math.max(loadingLayout.getWidth(), loadingLayout.getHeight());
            final int startRadius = (show) ? 0 : full;
            final int finalRadius = (show) ? full : 0;
            final int centerX = (loadingLayout.getLeft() + loadingLayout.getRight()) / 2;
            final int centerY = (loadingLayout.getTop() + loadingLayout.getBottom()) / 2;
            final Animator anim = ViewAnimationUtils.createCircularReveal(
                    loadingLayout, centerX, centerY, startRadius, finalRadius);
            anim.setDuration(ANIMATION_DURATION);
            anim.addListener(listener);
            anim.start();

        } else {
            loadingLayout.animate()
                    .alpha(show ? 1 : 0)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(listener)
                    .start();
        }

    }

    @Override
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        // Loading layout intercepts all touch events
        return true;
    }

    public boolean isRefreshing() {
        return mTask != null;
    }

    protected void onContentShown() {
        // Can be overriden by subclasses
    }

    protected void onLoadFinished() {
        // We want to keep the refresh UI up for *at least* MINIMUM_UPDATE_DURATION
        // Otherwise it looks very choppy and overall not a pleasant look
        final long now = System.currentTimeMillis();
        final long delay = MINIMUM_UPDATE_DURATION - (now - mLastUpdate);

        mTask = null;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    // Ensure we haven't been detached
                    changeLoadingVisibilityInternal(false);
                    onContentShown();
                }
            }
        }, delay);
    }

    protected void onNullResponseReceived() {
        Toast.makeText(getActivity(), "Received no data", Toast.LENGTH_SHORT).show();
    }

    private void deliverResponse(final T data) {
        if (data == null || data.getData() == null) {
            onNullResponseReceived();
        } else {
            onBindData(data.getMetadata(), data.getData());
        }

        if (getActivity() != null) {
            ((ModuleHostActivity) getActivity()).refreshActionBar();
        }
    }

    public String getToolbarTitle() {
        return getString(R.string.app_name);
    }

    public abstract T onLoadData(final UWaterlooApi api);

    public abstract void onBindData(final Metadata metadata, final V data);

    private final class LoadModuleDataTask extends AsyncTask<UWaterlooApi, Void, T> {

        @Override
        protected T doInBackground(final UWaterlooApi... apis) {
            // Performed on a background thread, so network calls are performed here
            try {
                return onLoadData(apis[0]);
            } catch (final Exception e) {
                Log.e("LoadModuleDataTask", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(final T data) {
            // Performed on the main thread, so view manipulation is performed here
            mLastResponse = data;
            onLoadFinished();

            deliverResponse(data);
        }

    }
}
