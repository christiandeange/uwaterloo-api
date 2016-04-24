package com.deange.uwaterlooapi.sample.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;

import com.deange.uwaterlooapi.sample.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int statusBarColor = ResourcesCompat.getColor(getResources(), R.color.uw_yellow, getTheme());

        getWindow().setStatusBarColor(statusBarColor);
        setTaskDescription(new ActivityManager.TaskDescription(
                getString(R.string.app_name),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher),
                statusBarColor)
        );
    }

    @Override
    protected void attachBaseContext(final Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
