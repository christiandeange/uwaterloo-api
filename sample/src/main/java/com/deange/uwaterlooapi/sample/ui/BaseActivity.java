package com.deange.uwaterlooapi.sample.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.deange.uwaterlooapi.sample.Authorities;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.KeyPersister;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.ModuleChanger;

import butterknife.ButterKnife;
import flow.Dispatcher;
import flow.Flow;
import flow.KeyDispatcher;
import pl.tajchert.nammu.Nammu;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity
    extends AppCompatActivity {

  protected static final String KEY_FLOW_KEY = Authorities.key("flow.key");
  private boolean mHasFlow;

  @Override
  protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final String title = getTaskBarTitle();
    final Bitmap icon = getTaskBarIcon();
    final int color = getTaskBarColor();

    getWindow().setStatusBarColor(color);
    setTaskDescription(new ActivityManager.TaskDescription(title, icon, color));
  }

  @Override
  public void setContentView(@LayoutRes final int layoutResID) {
    // Do this so Calligraphy has a chance to apply font details
    setContentView(View.inflate(this, layoutResID, null));
  }

  @Override
  public void setContentView(final View view) {
    super.setContentView(view);
    ButterKnife.bind(this);
  }

  @Override
  public void setContentView(final View view, final ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    ButterKnife.bind(this);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    final Intent intent = getIntent();
    Object defaultKey = (intent == null) ? null : intent.getParcelableExtra(KEY_FLOW_KEY);
    if (defaultKey == null) {
      defaultKey = getDefaultKey();
    }

    newBase = CalligraphyContextWrapper.wrap(newBase);

    mHasFlow = defaultKey != null;
    if (mHasFlow) {
      // This field is required for flow to be properly set up
      final Dispatcher dispatcher =
          KeyDispatcher.configure(this, new ModuleChanger(this::getContentRoot)).build();

      newBase = Flow.configure(newBase, this)
                    .dispatcher(dispatcher)
                    .defaultKey(defaultKey)
                    .keyParceler(new KeyPersister())
                    .install();
    }

    super.attachBaseContext(newBase);
  }

  protected ViewGroup getContentRoot() {
    // Subclass activities can provide their own root view to attach screens onto
    return null;
  }

  protected Object getDefaultKey() {
    // Subclass activities can return their own default
    return null;
  }

  @Override
  public void onBackPressed() {
    if (!(mHasFlow && flow().goBack())) {
      super.onBackPressed();
    }
  }

  protected final Flow flow() {
    return Flow.get(this);
  }

  @Override
  public void onRequestPermissionsResult(
      final int requestCode,
      @NonNull final String[] permissions,
      @NonNull final int[] grantResults) {
    Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  protected String getTaskBarTitle() {
    return getString(R.string.app_name);
  }

  protected Bitmap getTaskBarIcon() {
    return BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
  }

  @ColorInt
  protected int getTaskBarColor() {
    return ResourcesCompat.getColor(getResources(), R.color.uw_yellow, getTheme());
  }

}
