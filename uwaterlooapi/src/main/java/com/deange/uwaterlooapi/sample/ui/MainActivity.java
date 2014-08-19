package com.deange.uwaterlooapi.sample.ui;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.deange.uwaterlooapi.api.BuildingsApi;
import com.deange.uwaterlooapi.api.CoursesApi;
import com.deange.uwaterlooapi.api.EventsApi;
import com.deange.uwaterlooapi.api.FoodServicesApi;
import com.deange.uwaterlooapi.api.NewsApi;
import com.deange.uwaterlooapi.api.ResourcesApi;
import com.deange.uwaterlooapi.api.TermsApi;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.api.WeatherApi;
import com.deange.uwaterlooapi.sample.ApiRunner;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ApiMethodsFragment;
import com.deange.uwaterlooapi.sample.ui.view.TextDrawable;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.OnDrawerItemSelectedListener {

    private static final String FRAGMENT_TAG = ApiMethodsFragment.class.getSimpleName();

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String mTitle;
    private UWaterlooApi mApi = new UWaterlooApi("YOUR_API_KEY_HERE");

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

    public static Class getApiForIndex(final int index) {
        // These indices MUST be matched against @array/api_array
        switch (index) {
            case 0: return FoodServicesApi.class;
            case 1: return CoursesApi.class;
            case 2: return EventsApi.class;
            case 3: return NewsApi.class;
            case 4: return WeatherApi.class;
            case 5: return TermsApi.class;
            case 6: return ResourcesApi.class;
            case 7: return BuildingsApi.class;
            default: throw new IllegalArgumentException("invalid index " + index);
        }
    }

    public void onSectionAttached(int number) {
        mTitle = getResources().getStringArray(R.array.api_array)[number];
        restoreActionBar();
    }

    @Override
    public void showGlobalContextActionBar() {
        final SpannableStringBuilder ssb = new SpannableStringBuilder(getString(R.string.app_name));
        final ActionBar actionBar = getActionBar();
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, ssb.length(), 0);
        actionBar.setIcon(R.drawable.uwapi);
        actionBar.setTitle(ssb.toString());
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
