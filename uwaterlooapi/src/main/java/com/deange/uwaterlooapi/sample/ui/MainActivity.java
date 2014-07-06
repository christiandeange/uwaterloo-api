package com.deange.uwaterlooapi.sample.ui;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.sample.ApiRunner;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.view.TextDrawable;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.OnDrawerItemSelectedListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String mTitle;
    private TextDrawable mIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        getActionBar().setTitle(null);
        mTitle = null;

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        final UWaterlooApi api = new UWaterlooApi("YOUR_API_KEY_HERE");

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiRunner.runAll(api);
            }
        }).start();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, ViewPagerHost.newInstance(position))
                .commit();
    }

    public void onSectionAttached(int number) {
        mTitle = getResources().getStringArray(R.array.api_array)[number];
        restoreActionBar();
    }

    public void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setIcon(R.drawable.uwapi);
        setActionBarTitle(getString(R.string.app_name), Typeface.BOLD);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        setActionBarTitle(mTitle);
    }

    public void setActionBarTitle(final String text) {
        setActionBarTitle(text, 0);
    }

    public void setActionBarTitle(final String text, final int style) {
        final ActionBar actionBar = getActionBar();

        if (TextUtils.isEmpty(text)) {
            actionBar.setIcon(R.drawable.uwapi);

        } else {
            if (mIcon == null) {
                mIcon = new TextDrawable(this);
            }

            mIcon.setTypeface(Typeface.createFromAsset(getAssets(), "Gotham-Book.otf"), style);

            // Spaces for emulated 'padding'
            mIcon.setText("  " + text + " ");
            actionBar.setIcon(mIcon);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
