package com.deange.uwaterlooapi.sample.ui.flow;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import com.deange.uwaterlooapi.sample.R;
import flow.Flow;
import flow.FlowDelegate;
import flow.path.Path;
import flow.path.PathContainerView;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.graphics.BitmapFactory.decodeResource;
import static flow.History.single;
import static mortar.bundler.BundleServiceRunner.getBundleServiceRunner;

public abstract class BaseFlowActivity
    extends AppCompatActivity implements Flow.Dispatcher, HasLayout {

  private PathContainerView container;
  protected MortarScope activityScope;
  protected FlowDelegate flow;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    int color = ResourcesCompat.getColor(getResources(), R.color.uw_yellow, getTheme());
    getWindow().setStatusBarColor(color);
    setTaskDescription(new ActivityManager.TaskDescription(
        getString(R.string.app_name),
        decodeResource(getResources(), R.drawable.ic_launcher),
        color));

    setContentView(layoutId());
    container = (PathContainerView) findViewById(R.id.container);

    MortarScope appScope = MortarScope.getScope(getApplication());
    String scopeName = getClass().getName();
    activityScope = appScope.findChild(scopeName);
    if (activityScope == null) {
      activityScope =
          appScope.buildChild()
              .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
              .build(scopeName);
    }
    getBundleServiceRunner(activityScope).onCreate(savedInstanceState);

    flow = FlowDelegate.onCreate(
        (FlowDelegate.NonConfigurationInstance) getLastCustomNonConfigurationInstance(),
        getIntent(), savedInstanceState, KeyPathParceler.INSTANCE, single(defaultPath()), this);
  }

  @Override
  public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
    container.dispatch(traversal, callback);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    flow.onNewIntent(intent);
  }

  @Override
  protected void onResume() {
    super.onResume();
    flow.onResume();
  }

  @Override
  protected void onPause() {
    flow.onPause();
    super.onPause();
  }

  @Override
  public Object onRetainCustomNonConfigurationInstance() {
    return flow.onRetainNonConfigurationInstance();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    flow.onSaveInstanceState(outState);
    getBundleServiceRunner(this).onSaveInstanceState(outState);
  }

  @Override
  public void onBackPressed() {
    if (!flow.onBackPressed()) {
      super.onBackPressed();
    }
  }

  @Override
  public Object getSystemService(@NonNull String name) {
    if (flow != null) {
      Object flowService = flow.getSystemService(name);
      if (flowService != null) return flowService;
    }

    return activityScope != null && activityScope.hasService(name)
        ? activityScope.getService(name)
        : super.getSystemService(name);
  }

  @Override
  public void onDestroy() {
    if (isFinishing() && activityScope != null) {
      activityScope.destroy();
      activityScope = null;
    }

    super.onDestroy();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @NonNull
  protected abstract Path defaultPath();
}
