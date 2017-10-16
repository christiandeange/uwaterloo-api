package com.deange.uwaterlooapi.sample.ui;

import android.app.ActivityManager;
import android.content.Context;
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

import com.deange.uwaterlooapi.sample.R;

import butterknife.ButterKnife;
import pl.tajchert.nammu.Nammu;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity
    extends AppCompatActivity {

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
  protected void attachBaseContext(final Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
