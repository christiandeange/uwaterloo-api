package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.sample.ApiRunner;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ApiMethodsFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.OnDrawerItemSelectedListener {

    private static final String FRAGMENT_TAG = ApiMethodsFragment.class.getSimpleName();

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String mTitle;
    private UWaterlooApi mApi = new UWaterlooApi("YOUR_API_KEY_HERE");
    private Toolbar mToolbar;

    @Override
    protected void attachBaseContext(final Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                mToolbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiRunner.runAll(mApi);
            }
        }).start();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, ApiMethodsFragment.newInstance(position), FRAGMENT_TAG)
                .commit();
    }

    public void onSectionAttached(int number) {
        setTitle(getResources().getStringArray(R.array.api_array)[number]);
    }

    @Override
    public void showGlobalContextActionBar() {
        setTitle(R.string.app_name);
    }

    public void setTitle(final String title) {
        mTitle = title;
        refreshActionBar();
    }

    @Override
    public void setTitle(final int titleId) {
        mTitle = getString(titleId);
        refreshActionBar();
    }

    public void refreshActionBar() {
        if (mToolbar != null) {
            mToolbar.setTitle(mTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            refreshActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
