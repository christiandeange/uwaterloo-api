package com.deange.uwaterlooapi.sample.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.controller.GsonController;
import com.deange.uwaterlooapi.sample.ui.modules.ApiMethodsFragment;
import com.deange.uwaterlooapi.sample.ui.modules.ApiMethodsKey;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.Key;
import com.deange.uwaterlooapi.sample.ui.modules.home.HomeFragment;
import com.deange.uwaterlooapi.sample.utils.FontUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static flow.Direction.REPLACE;


public class MainActivity
    extends BaseActivity
    implements
    NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

  private static final String NAV_ITEM_ID = "nav_item_id";

  @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
  @BindView(R.id.navigation) NavigationView mNavigationView;
  @BindView(R.id.toolbar) Toolbar mToolbar;

  private int mClicks;
  private int mNavItemId;
  private ModuleCategories mMenuStructure;
  private ActionBarDrawerToggle mDrawerToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setSupportActionBar(mToolbar);

    if (savedInstanceState == null) {
      mNavItemId = R.id.menu_item_home;
    } else {
      mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
    }

    // listen for navigation events
    mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);
    mNavigationView.setNavigationItemSelectedListener(this);

    final View headerView = mNavigationView.getHeaderView(0);
    final TextView titleView = (TextView) headerView.findViewById(R.id.navigation_header_title);
    final TextView subtitleView = (TextView) headerView.findViewById(
        R.id.navigation_header_subtitle);
    HeaderTitlePresenter.show(titleView, subtitleView);

    // Can't use butterknife for this call
    headerView.findViewById(R.id.navigation_header_clickable).setOnClickListener(this);

    // set up the hamburger icon to open and close the drawer
    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0);
    mDrawerToggle.syncState();
    mDrawerLayout.addDrawerListener(mDrawerToggle);

    final InputStream inputStream = getResources().openRawResource(R.raw.menu_structure);
    final InputStreamReader reader = new InputStreamReader(inputStream);
    mMenuStructure = GsonController.getInstance().fromJson(reader, ModuleCategories.class);

    mNavigationView.getViewTreeObserver().addOnPreDrawListener(() -> {
      FontUtils.apply(mNavigationView, FontUtils.DEFAULT);
      return true;
    });
  }

  @Override
  public void onPostCreate(
      @Nullable final Bundle savedInstanceState,
      @Nullable final PersistableBundle persistentState) {
    super.onPostCreate(savedInstanceState, persistentState);

    onNavigationItemSelected(mNavigationView.getMenu().findItem(mNavItemId));
  }

  @Override
  public void onConfigurationChanged(final Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
      return mDrawerToggle.onOptionsItemSelected(item);
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  protected Object getDefaultKey() {
    return HomeFragment.Key.create();
  }

  @Override
  protected ViewGroup getContentRoot() {
    return (ViewGroup) findViewById(R.id.container);
  }

  @Override
  protected void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(NAV_ITEM_ID, mNavItemId);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
    // update the main content by replacing keys
    mNavItemId = item.getItemId();
    setTitle(item.getTitle());
    FontUtils.apply(mToolbar, FontUtils.DEFAULT);

    if (mNavItemId == R.id.menu_item_about) {
      startActivity(new Intent(this, AboutActivity.class));
      overridePendingTransition(R.anim.bottom_in, R.anim.stay);
      return false;
    }

    final Key key;
    if (mNavItemId == R.id.menu_item_home) {
      key = HomeFragment.Key.create();

    } else {
      final List<String> endpoints = mMenuStructure.getApiMethods(item.getItemId(), getResources());

      if (endpoints.size() == 1) {
        ApiMethodsFragment.openModule(this, endpoints.get(0));
        return false;
      }

      key = ApiMethodsKey.create(endpoints);
    }

    flow().replaceTop(key, REPLACE);

    mDrawerLayout.closeDrawer(GravityCompat.START);

    return true;
  }

  @Override
  public void onClick(final View v) {
    // Header view
    if (++mClicks >= 30) {
      mClicks = 0;
      startActivity(new Intent(this, GooseActivity.class));
    }
  }

  static final class ModuleCategories extends HashMap<String, List<String>> {

    public List<String> getApiMethods(
        final @IdRes int menuItemId,
        final Resources res) {
      final String idName = res.getResourceEntryName(menuItemId);
      final String category = idName.substring("menu_item_".length());
      return containsKey(category) ? get(category) : new ArrayList<>();
    }

  }
}
