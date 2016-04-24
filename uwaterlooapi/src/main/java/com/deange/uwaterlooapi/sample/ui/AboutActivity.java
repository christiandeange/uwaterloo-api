package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.FontUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity
        extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do this so Calligraphy has a chance to apply font details
        setContentView(View.inflate(this, R.layout.activity_about, null));

        final CollapsingToolbarLayout collapsingView = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final Typeface typeface = FontUtils.getFont(FontUtils.BOOK);
        collapsingView.setCollapsedTitleTypeface(typeface);
        collapsingView.setExpandedTitleTypeface(typeface);
        collapsingView.setTitle(getString(R.string.menu_about));

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.bottom_out);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
