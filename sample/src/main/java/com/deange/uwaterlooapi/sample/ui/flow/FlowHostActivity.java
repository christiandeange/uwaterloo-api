package com.deange.uwaterlooapi.sample.ui.flow;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import flow.path.Path;

public class FlowHostActivity
    extends BaseFlowActivity {

  private static final String TAG = "module_fragment";

  @Override
  public void startActivityForResult(
      final Intent intent,
      final int requestCode,
      final Bundle options) {
    try {
      super.startActivityForResult(intent, requestCode, options);
    } catch (final ActivityNotFoundException e) {
      Log.e(TAG, "No Activity found to handle Intent", e);
    }
  }

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setSupportActionBar(getToolbar());
  }

  public Toolbar getToolbar() {
    return (Toolbar) findViewById(R.id.host_toolbar);
  }

  private BaseModuleFragment findContentFragment() {
    return (BaseModuleFragment) getSupportFragmentManager().findFragmentById(R.id.content);
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public int layoutId() {
    return R.layout.activity_module_root;
  }

  @NonNull @Override protected Path defaultPath() {
    return null;
  }
}
