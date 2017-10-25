package com.deange.uwaterlooapi.sample.ui.modules.baseflow;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.deange.uwaterlooapi.sample.Analytics;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.deange.uwaterlooapi.sample.utils.ViewUtils.unwrap;

public abstract class Screen<T extends Key> {

  private Activity mActivity;
  private View mView;
  private Unbinder mUnbinder;

  private T mKey;

  public T key() {
    return mKey;
  }

  public Context getContext() {
    return mView.getContext();
  }

  public Resources getResources() {
    return mActivity.getResources();
  }

  public String getString(@StringRes final int id, final Object... formatArgs) {
    return mActivity.getResources().getString(id, formatArgs);
  }

  public Activity getActivity() {
    return mActivity;
  }

  void setKey(final T key) {
    mKey = key;
  }

  final void takeView(final View view) {
    mActivity = unwrap(view.getContext());
    mView = view;

    final String contentType = getContentType();
    if (contentType != null) {
      Analytics.view(contentType);
    }

    mUnbinder = ButterKnife.bind(this, view);

    onViewAttached(view);
  }

  final void detach() {
    onViewDetached();

    mUnbinder.unbind();
    mUnbinder = null;
    mView = null;
    mActivity = null;
  }

  public abstract String getContentType();

  protected abstract void onViewAttached(final View view);

  public void onRestore(final Bundle bundle) {
    // Override this method to get access to the previously-saved state
  }

  public boolean onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
    // Override this method to provide menu items
    return false;
  }

  public boolean onOptionsItemSelected(final MenuItem item) {
    // Override this method to respond to menu item interactions
    return false;
  }

  public void onSave(final Bundle state) {
    // Override this method to save things from your screen
  }

  protected void onViewDetached() {
    // Clean up any remaining resources
  }
}
