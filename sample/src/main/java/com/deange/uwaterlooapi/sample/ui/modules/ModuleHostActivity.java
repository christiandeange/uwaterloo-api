package com.deange.uwaterlooapi.sample.ui.modules;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.deange.uwaterlooapi.UWaterlooApi;
import com.deange.uwaterlooapi.sample.BuildConfig;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.controller.WatcardManager;
import com.deange.uwaterlooapi.sample.ui.BaseActivity;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.utils.FontUtils;


public class ModuleHostActivity
    extends BaseActivity
    implements
    FragmentManager.OnBackStackChangedListener {

  private static final String TAG = "module_fragment";
  private static final String ARG_FRAGMENT_CLASS = "fragment_class";

  private UWaterlooApi mApi;
  private BaseModuleFragment mChildFragment;
  private Toolbar mToolbar;

  public static <T extends BaseModuleFragment> Intent getStartIntent(
      final Context context,
      final Class<T> fragment) {
    return getStartIntent(context, fragment, new Bundle());
  }

  public static <T extends BaseModuleFragment> Intent getStartIntent(
      final Context context,
      final Class<T> fragment,
      final Bundle args) {
    final Intent intent = new Intent(context, ModuleHostActivity.class);

    intent.putExtra(ARG_FRAGMENT_CLASS, fragment.getCanonicalName());
    if (args != null) {
      intent.putExtras(args);
    }

    return intent;
  }

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

    setContentView(R.layout.activity_module_host_simple);

    mApi = new UWaterlooApi(BuildConfig.UWATERLOO_API_KEY);
    mApi.setWatcardCredentials(WatcardManager.getInstance().getCredentials());

    mToolbar = getToolbar();
    setSupportActionBar(mToolbar);
    getSupportFragmentManager().addOnBackStackChangedListener(this);

    mChildFragment = findContentFragment();
    if (mChildFragment == null) {
      final String fragmentName = getIntent().getStringExtra(ARG_FRAGMENT_CLASS);
      showFragment((BaseModuleFragment) Fragment.instantiate(this, fragmentName), false,
                   getIntent().getExtras());
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    refreshActionBar();
  }

  public Toolbar getToolbar() {
    return (Toolbar) findViewById(R.id.host_toolbar);
  }

  private BaseModuleFragment findContentFragment() {
    return (BaseModuleFragment) getSupportFragmentManager().findFragmentById(R.id.content);
  }

  public void showFragment(
      final BaseModuleFragment fragment, final boolean addToBackStack,
      final Bundle arguments) {
    mChildFragment = fragment;
    mChildFragment.setArguments(arguments);

    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    if (addToBackStack) {
      transaction.addToBackStack(mChildFragment.getClass().getCanonicalName());
    }
    transaction.replace(R.id.content, mChildFragment, TAG).commit();
  }

  public void refreshActionBar() {
    final ActionBar actionBar = getSupportActionBar();

    if (actionBar == null || mChildFragment == null || !mChildFragment.isAdded()) {
      return;
    }

    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(mChildFragment.getToolbarTitle());
    actionBar.setElevation(mChildFragment.getToolbarElevationPx());

    FontUtils.apply(getToolbar(), FontUtils.DEFAULT);
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onAttachFragment(final Fragment fragment) {
    onBackStackChanged();
  }

  @Override
  public void onBackPressed() {
    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
      getSupportFragmentManager().popBackStack();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  protected ViewGroup getContentRoot() {
    return (ViewGroup) findViewById(R.id.content);
  }

  public UWaterlooApi getApi() {
    return mApi;
  }

  @Override
  public void onBackStackChanged() {
    mChildFragment = findContentFragment();
    refreshActionBar();
  }
}
