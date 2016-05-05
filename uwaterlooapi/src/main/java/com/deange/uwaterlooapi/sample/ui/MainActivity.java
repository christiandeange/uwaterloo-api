package com.deange.uwaterlooapi.sample.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.sample.ApiRunner;
import com.deange.uwaterlooapi.sample.BuildConfig;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ApiMethodsFragment;
import com.deange.uwaterlooapi.sample.ui.modules.home.HomeFragment;
import com.deange.uwaterlooapi.sample.utils.FontUtils;
import com.deange.uwaterlooapi.sample.utils.PlatformUtils;
import com.deange.uwaterlooapi.utils.GsonController;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity
        extends BaseActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private static final String FRAGMENT_TAG = ApiMethodsFragment.class.getSimpleName();
    private static final String NAV_ITEM_ID = "nav_item_id";

    private int mNavItemId;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    private int mClicks;
    private ModuleCategories mMenuStructure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiRunner.runAll(new UWaterlooApi(BuildConfig.UWATERLOO_API_KEY));
            }
        }).start();

        if (savedInstanceState == null) {
            mNavItemId = R.id.menu_item_home;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        // listen for navigation events
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(this);

        final View headerView = mNavigationView.inflateHeaderView(R.layout.view_navigation_header);
        final TextView titleView = (TextView) headerView.findViewById(R.id.navigation_header_title);
        final TextView subtitleView = (TextView) headerView.findViewById(R.id.navigation_header_subtitle);
        HeaderTitlePresenter.show(titleView, subtitleView);

        if (PlatformUtils.hasLollipop()) {
            headerView.findViewById(R.id.navigation_header_clickable).setOnClickListener(this);
        }

        // set up the hamburger icon to open and close the drawer
        mDrawerLayout = ((DrawerLayout) findViewById(R.id.drawer_layout));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        final InputStreamReader reader = new InputStreamReader(getResources().openRawResource(R.raw.menu_structure));
        mMenuStructure = GsonController.getInstance().fromJson(reader, ModuleCategories.class);

        onNavigationItemSelected(mNavigationView.getMenu().findItem(mNavItemId));

        mNavigationView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fixForegrounds(mNavigationView);
                return true;
            }
        });
    }

    private void fixForegrounds(final View view) {
        if (view instanceof NavigationMenuItemView) {
            //noinspection RedundantCast
            ((NavigationMenuItemView) view).setForeground(null);

        } else if (view instanceof TextView) {
            FontUtils.apply(view, FontUtils.DEFAULT);
        }

        if (view instanceof ViewGroup) {
            final ViewGroup parent = ((ViewGroup) view);
            for (int i = 0; i < parent.getChildCount(); ++i) {
                fixForegrounds(parent.getChildAt(i));
            }
        }
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
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // update the main content by replacing fragments

        final int itemId = item.getItemId();
        final Fragment fragment;

        if (itemId == R.id.menu_item_about) {
            startActivity(new Intent(this, AboutActivity.class));
            overridePendingTransition(R.anim.bottom_in, R.anim.stay);
            return false;
        }

        if (itemId == R.id.menu_item_home) {
            fragment = new HomeFragment();

        } else {
            final String[] endpoints = mMenuStructure.getApiMethods(item.getItemId(), getResources());

            if (endpoints.length == 1) {
                ApiMethodsFragment.openModule(this, endpoints[0]);
                return false;
            }

            fragment = ApiMethodsFragment.newInstance(endpoints);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, FRAGMENT_TAG)
                .commitAllowingStateLoss();

        mDrawerLayout.closeDrawer(GravityCompat.START);

        mNavItemId = itemId;
        setTitle(item.getTitle());
        FontUtils.apply(mToolbar, FontUtils.DEFAULT);

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

        public String[] getApiMethods(final @IdRes int menuItemId, final Resources res) {
            final String idName = res.getResourceEntryName(menuItemId);
            final String category = idName.substring("menu_item_".length());
            final List<String> endpoints = containsKey(category) ? get(category) : new ArrayList<String>();

            return endpoints.toArray(new String[endpoints.size()]);
        }

    }
}
