package com.deange.uwaterlooapi.sample.ui.modules.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.SimpleListResponse;
import com.deange.uwaterlooapi.sample.Analytics;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.utils.PlatformUtils;

import org.parceler.Parcels;

import java.util.List;

public abstract class BaseModuleFragment<T extends BaseResponse, V extends BaseModel>
        extends Fragment
        implements
        View.OnTouchListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final long MINIMUM_UPDATE_DURATION = 1000;
    public static final long ANIMATION_DURATION = 300;

    private static final String KEY_MODEL = "model";
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_LAST_UPDATED = "last_updated";

    private long mLastUpdate = 0;
    private ViewGroup mLoadingLayout;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private T mLastResponse;
    private LoadModuleDataTask mTask;
    private SwipeRefreshLayout mSwipeLayout;

    public static <V extends BaseModel> Bundle newBundle(final V model) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_MODEL, Parcels.wrap(model));
        return bundle;
    }

    public BaseModuleFragment() {
        // Required constructor
        setHasOptionsMenu(true);
    }

    public ModuleHostActivity getHostActivity() {
        return (ModuleHostActivity) getActivity();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (!(context instanceof ModuleHostActivity)) {
            throw new RuntimeException("Parent activity not an instance of ModuleHostActivity");
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Analytics.view(getContentType());
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

        mSwipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.fragment_swipe_container);
        if (mSwipeLayout != null) {
            mSwipeLayout.setOnRefreshListener(this);
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
            mLastResponse = BaseResponse.deserialize(savedInstanceState.getString(KEY_RESPONSE));
        }

        // Deliver the response if we still have one, otherwise load the data
        // (usually from coming back from another activity or rotating)
        // post() so that the SwipeRefreshLayout draws the indicator correctly (http://stackoverflow.com/a/26860930)
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mLastResponse != null) {
                    deliverResponse(mLastResponse);

                } else if (mLastUpdate == 0) {
                    doRefresh();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_base_module, menu);
        syncRefreshMenuItem(menu);
    }

    protected final void syncRefreshMenuItem(final Menu menu) {
        final MenuItem refreshItem = menu.findItem(R.id.menu_refresh);
        if (refreshItem != null) {
            refreshItem.setVisible(!isRefreshing());
            refreshItem.setEnabled(!isRefreshing());
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            // Refresh manually requested
            doRefresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_LAST_UPDATED, mLastUpdate);
        outState.putString(KEY_RESPONSE, BaseResponse.serialize(mLastResponse));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mTask != null) {
            mTask.cancel(true);
            mTask = null;
        }
    }

    public void showModule(final Class<? extends BaseModuleFragment> fragment,
                           final Bundle arguments) {
        getActivity().startActivity(
                ModuleHostActivity.getStartIntent(getActivity(), fragment, arguments));
    }

    public void showModule(final BaseModuleFragment fragment,
                           final boolean addToBackStack,
                           final Bundle arguments) {
        ((ModuleHostActivity) getActivity()).showFragment(fragment, addToBackStack, arguments);
    }

    public <M> M getModel() {
        return Parcels.unwrap(getArguments().getParcelable(KEY_MODEL));
    }

    @Override
    public void onRefresh() {
        doRefresh();
    }

    protected final void doRefresh() {

        onRefreshRequested();

        // Data can potentially be stored from above (maybe cached, or passed in as a Parcelable?)
        // In that case, there's no need to try and load any data from the network
        // Deliver it right away to the main thread!
        final V data = onLoadData();
        if (data != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    deliverResponse(data);
                    onLoadFinished();
                }
            });

        } else {
            changeLoadingVisibilityInternal(true);
            mLastUpdate = System.currentTimeMillis();

            final UWaterlooApi api = ((ModuleHostActivity) getActivity()).getApi();
            mTask = new LoadModuleDataTask();
            mTask.execute(api);
        }
    }

    protected void onRefreshRequested() {
        // Overriden by subclasses
    }

    private void changeLoadingVisibilityInternal(final boolean show) {

        // Allow the refresh menu item to be updated
        getActivity().supportInvalidateOptionsMenu();

        changeLoadingVisibility(show);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void changeLoadingVisibility(final boolean show) {

        if (mSwipeLayout != null) {
            mSwipeLayout.setRefreshing(show);
            mSwipeLayout.setEnabled(!show);
            return;
        }

        final View loadingLayout = mLoadingLayout;

        if (show) {
            loadingLayout.setVisibility(View.VISIBLE);
            if (mLastUpdate == 0) {
                return;
            }
        }

        if (!show && mLastResponse == null) {
            loadingLayout.setVisibility(View.GONE);
            return;
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

        if (PlatformUtils.hasLollipop()) {
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

    public long getLastUpdate() {
        return mLastUpdate;
    }

    public boolean isRefreshing() {
        return mTask != null;
    }

    protected void onContentShown() {
        // Can be overriden by subclasses
    }

    protected void onLoadFinished() {
        final long delay;
        if (mSwipeLayout != null || mLastResponse == null) {
            // No minimum delay for the SwipeRefreshLayout or no response at all
            delay = 0;

        } else {
            // We want to keep the refresh UI up for *at least* MINIMUM_UPDATE_DURATION
            // Otherwise it looks very choppy and overall not a pleasant look
            final long now = System.currentTimeMillis();
            delay = MINIMUM_UPDATE_DURATION - (now - mLastUpdate);
        }

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

    private void deliverResponse(final T response) {

        if (response == null || response.getData() == null) {
            onNullResponseReceived();
        } else if (response instanceof SimpleListResponse)  {
            onBindData(response.getMetadata(), (List<V>) response.getData());
        } else {
            onBindData(response.getMetadata(), (V) response.getData());
        }

        if (getActivity() != null) {
            ((ModuleHostActivity) getActivity()).refreshActionBar();
        }
    }

    private void deliverResponse(final V data) {
        if (data == null) {
            onNullResponseReceived();
        } else {
            onBindData(null, data);
        }

        if (getActivity() != null) {
            ((ModuleHostActivity) getActivity()).refreshActionBar();
        }
    }

    public void post(final Runnable runnable) {
        mHandler.post(runnable);
    }

    public void postDelayed(final Runnable runnable, final long delay) {
        mHandler.postDelayed(runnable, delay);
    }

    public String getToolbarTitle() {
        return null;
    }

    public T onLoadData(final UWaterlooApi api) {
        // Overriden by subclasses
        return null;
    }

    public V onLoadData() {
        // Overriden by subclasses
        return null;
    }

    public void onBindData(final Metadata metadata, final V data) {
        // Overriden by subclasses
    }

    public void onBindData(final Metadata metadata, final List<V> data) {
        // Overriden by subclasses
    }

    public abstract String getContentType();

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
