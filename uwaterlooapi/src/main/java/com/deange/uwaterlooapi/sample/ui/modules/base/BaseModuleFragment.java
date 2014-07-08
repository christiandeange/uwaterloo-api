package com.deange.uwaterlooapi.sample.ui.modules.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

public abstract class BaseModuleFragment<T extends SimpleResponse<V>, V> extends Fragment implements View.OnClickListener, View.OnTouchListener {

    public static final long MINIMUM_UPDATE_DURATION = 2000;
    public static final long ANIMATION_DURATION = 300;

    private long mLastUpdate = 0;
    private ViewGroup mLoadingLayout;
    private final Handler mHandler = new Handler();

    public BaseModuleFragment() {
        // Required constructor
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

        final View root = getView();
        root.findViewById(R.id.refresh_button).setOnClickListener(this);

        // Show data when first displayed
        if (mLastUpdate == 0) {
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
        changeLoadingVisibility(true);

        mLastUpdate = System.currentTimeMillis();
        final UWaterlooApi api = ((ModuleHostActivity) getActivity()).getApi();
        new LoadModuleDataTask().execute(api);
    }

    protected void changeLoadingVisibility(final boolean show) {

        final View loadingLayout = mLoadingLayout;
        if (mLastUpdate == 0) {
            loadingLayout.setVisibility(View.VISIBLE);
            return;
        }

        // Get the center for the clipping circle
        final int centerX = (loadingLayout.getLeft() + loadingLayout.getRight()) / 2;
        final int centerY = (loadingLayout.getTop() + loadingLayout.getBottom()) / 2;
        final int zero = 0;
        final int full = Math.max(loadingLayout.getWidth(), loadingLayout.getHeight());

        final int startRadius;
        final int finalRadius;

        if (show) {
            // Show the loading layout
            startRadius = zero;
            finalRadius = full;
            mLoadingLayout.setVisibility(View.VISIBLE);
        } else {
            // Hide the loading layout
            startRadius = full;
            finalRadius = zero;
        }

        final AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                loadingLayout.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            }
        };

        if (Build.VERSION.SDK_INT >= 20) {
            final ValueAnimator anim = ViewAnimationUtils.createCircularReveal(
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

    protected void onLoadFinished() {
        // We want to keep the refresh UI up for *at least* MINIMUM_UPDATE_DURATION
        // Otherwise it looks very choppy and overall not a pleasant look
        final long now = System.currentTimeMillis();
        final long delay = MINIMUM_UPDATE_DURATION - (now - mLastUpdate);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeLoadingVisibility(false);
            }
        }, delay);
    }

    protected void onNullResponseReceived() {
        Toast.makeText(getActivity(), "Could not receive data", Toast.LENGTH_SHORT).show();
    }

    public abstract Bundle getFragmentInfo();

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

            if (data == null) {
                onNullResponseReceived();
            } else {
                onBindData(data.getMetadata(), data.getData());
            }
        }

    }
}
