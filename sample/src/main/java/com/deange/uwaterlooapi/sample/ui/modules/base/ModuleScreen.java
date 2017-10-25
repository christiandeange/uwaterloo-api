package com.deange.uwaterlooapi.sample.ui.modules.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.deange.uwaterlooapi.UWaterlooApi;
import com.deange.uwaterlooapi.model.AbstractModel;
import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.SimpleListResponse;
import com.deange.uwaterlooapi.sample.Analytics;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.net.Calls;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.ModuleKey;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.Screen;
import com.deange.uwaterlooapi.sample.utils.NetworkController;
import com.deange.uwaterlooapi.sample.utils.Px;

import java.util.List;

import retrofit2.Call;

public abstract class ModuleScreen<K extends ModuleKey, T extends Parcelable, V extends AbstractModel>
    extends Screen<K>
    implements
    View.OnTouchListener,
    SwipeRefreshLayout.OnRefreshListener {

  protected static final long MINIMUM_UPDATE_DURATION = 1000;
  protected static final long ANIMATION_DURATION = 300;
  protected static final Interpolator ANIMATION_INTERPOLATOR = new FastOutSlowInInterpolator();

  private static final String KEY_MODEL = "model";
  private static final String KEY_RESPONSE = "response";
  private static final String KEY_LAST_UPDATED = "last_updated";

  private long mLastUpdate = 0;
  private ViewGroup mLoadingLayout;
  private ViewGroup mNetworkLayout;

  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private T mLastResponse;
  private Animator mLoadingAnimator;
  private LoadModuleDataTask mTask;
  private SwipeRefreshLayout mSwipeLayout;

  public static <V extends AbstractModel> Bundle newBundle(final V model) {
    final Bundle bundle = new Bundle();
    bundle.putParcelable(KEY_MODEL, model);
    return bundle;
  }

  public ModuleHostActivity getHostActivity() {
    return (ModuleHostActivity) getActivity();
  }

  @Override
  @CallSuper
  protected void onViewAttached(final View root) {
    final String contentType = getContentType();
    if (contentType != null) {
      Analytics.view(contentType);
    }

    mLoadingLayout = (ViewGroup) root.findViewById(R.id.loading_layout);
    mLoadingLayout.setOnTouchListener(this);

    mNetworkLayout = (ViewGroup) root.findViewById(R.id.no_network_layout);
    mNetworkLayout.setOnTouchListener(this);

    mSwipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.fragment_swipe_container);
    if (mSwipeLayout != null) {
      mSwipeLayout.setOnRefreshListener(this);
    }
  }

  @Override
  public void onRestore(final Bundle bundle) {
    if (bundle != null) {
      mLastUpdate = bundle.getLong(KEY_LAST_UPDATED);
      mLastResponse = bundle.getParcelable(KEY_RESPONSE);
    }

    // Deliver the response if we still have one, otherwise load the data
    // (usually from coming back from another activity or rotating)
    // postDelayed() so that the SwipeRefreshLayout draws the indicator correctly (http://stackoverflow.com/a/26860930)
    postDelayed(() -> {
      if (mLastResponse != null) {
        onRefreshRequested();
        deliverResponse(mLastResponse);

      } else if (mLastUpdate == 0) {
        doRefresh();
      }
    }, 100);
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
    if (getModel() == null) {
      // If there is a response available in memory, no need to "refresh"
      inflater.inflate(R.menu.menu_base_module, menu);
    }

    syncRefreshMenuItem(menu);
    return true;
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
  public void onSave(final Bundle state) {
    state.putLong(KEY_LAST_UPDATED, mLastUpdate);
    state.putParcelable(KEY_RESPONSE, mLastResponse);
  }

  @Override
  @CallSuper
  protected void onViewDetached() {
    if (mTask != null) {
      mTask.cancel(true);
      mTask = null;
    }
  }

  public void showModule(final ModuleKey key) {
    getHostActivity().flow().set(key);
  }

  public <M> M getModel() {
    // noinspection unchecked
    return (M) key().model();
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
      mHandler.post(() -> {
        deliverData(data);
        onLoadFinished();
      });

    } else {
      changeLoadingVisibilityInternal(true);
      mLastUpdate = System.currentTimeMillis();

      mTask = new LoadModuleDataTask();
      mTask.execute(getApi());
    }
  }

  public UWaterlooApi getApi() {
    return getHostActivity().getApi();
  }

  protected void onRefreshRequested() {
    // Overriden by subclasses
  }

  private void changeLoadingVisibilityInternal(final boolean show) {
    // Allow the refresh menu item to be updated
    getHostActivity().supportInvalidateOptionsMenu();
    changeLoadingVisibility(show);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  protected void changeLoadingVisibility(final boolean show) {
    if (mSwipeLayout != null) {
      mSwipeLayout.setRefreshing(show);
      mSwipeLayout.setEnabled(!show);
    }

    if (show) {
      mLoadingLayout.setVisibility(View.VISIBLE);
      if (mLastUpdate == 0) {
        return;
      }
    }

    final AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(final Animator animation) {
        mLoadingLayout.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
      }

      @Override
      public void onAnimationCancel(final Animator animation) {
        mLoadingLayout.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
      }
    };

    if (mLoadingAnimator != null) {
      mLoadingAnimator.cancel();
    }
    mLoadingAnimator = getVisibilityAnimator(mLoadingLayout, show);
    mLoadingAnimator.setInterpolator(ANIMATION_INTERPOLATOR);
    mLoadingAnimator.setDuration(ANIMATION_DURATION);
    mLoadingAnimator.addListener(listener);
    mLoadingAnimator.start();
  }

  private Animator getVisibilityAnimator(final View view, final boolean show) {
    final int full = Math.max(view.getWidth(), view.getHeight());
    final int startRadius = (show) ? 0 : full;
    final int finalRadius = (show) ? full : 0;
    final int centerX = (view.getLeft() + view.getRight()) / 2;
    final int centerY = (view.getTop() + view.getBottom()) / 2;

    return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius,
                                                   finalRadius);
  }

  @Override
  public boolean onTouch(final View view, final MotionEvent motionEvent) {
    // Loading/Network layouts intercept all touch events
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
    mHandler.postDelayed(() -> {
      if (getActivity() != null) {
        // Ensure we haven't been detached
        changeLoadingVisibilityInternal(false);
        onContentShown();
      }
    }, delay);
  }

  protected void onNullResponseReceived() {
    Toast.makeText(getActivity(), "Received no data", Toast.LENGTH_SHORT).show();
  }

  protected void onNoDataReturned() {
    Toast.makeText(getActivity(), "Received no data", Toast.LENGTH_LONG).show();
  }

  private void resolveNetworkLayoutVisibility() {
    final boolean connected = NetworkController.getInstance().isConnected();

    final Animator animator = getVisibilityAnimator(mNetworkLayout, !connected);
    if (!connected && mNetworkLayout.getVisibility() == View.INVISIBLE) {
      animator.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(final Animator animation) {
          mNetworkLayout.setVisibility(View.VISIBLE);
        }
      });

      animator.start();

    } else if (connected && mNetworkLayout.getVisibility() == View.VISIBLE) {
      animator.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(final Animator animation) {
          mNetworkLayout.setVisibility(View.INVISIBLE);
        }
      });

      animator.start();
    }
  }

  protected void deliverResponse(final T data) {
    resolveNetworkLayoutVisibility();

    if (data == null) {
      onNullResponseReceived();

    } else if (data instanceof BaseResponse) {
      final BaseResponse response = (BaseResponse) data;
      final Metadata metadata = response.getMetadata();

      if (response.getData() == null) {
        onNullResponseReceived();

      } else if (metadata != null && metadata.getStatus() == 204) {
        onNoDataReturned();

      } else if (response instanceof SimpleListResponse) {
        onBindData(metadata, (List<V>) response.getData());

      } else {
        onBindData(metadata, (V) response.getData());
      }
    }

    if (getActivity() != null) {
      ((ModuleHostActivity) getActivity()).refreshActionBar();
    }
  }

  protected void deliverData(final V data) {
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

  public float getToolbarElevationPx() {
    // Overriden by subclasses
    return Px.fromDpF(8);
  }

  public Call<T> onLoadData(final UWaterlooApi api) {
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
        if (NetworkController.getInstance().isConnected()) {
          final Call<T> call = onLoadData(apis[0]);
          if (call != null) {
            return Calls.unwrap(call);
          }
        } else {
          Thread.sleep(MINIMUM_UPDATE_DURATION);
        }
      } catch (final Exception e) {
        Log.e("LoadModuleDataTask", e.getMessage(), e);
      }

      return null;
    }

    @Override
    protected void onPostExecute(final T data) {
      if (getActivity() == null) {
        return;
      }

      // Performed on the main thread, so view manipulation is performed here
      mLastResponse = data;
      onLoadFinished();

      deliverResponse(data);
    }

  }
}
