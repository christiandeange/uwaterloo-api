package com.deange.uwaterlooapi.sample.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.view.ElevationOffsetListener;
import com.deange.uwaterlooapi.sample.utils.FontUtils;
import com.deange.uwaterlooapi.sample.utils.Px;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

import static com.deange.uwaterlooapi.sample.dagger.Components.component;

public class AboutActivity
    extends BaseActivity {

  @BindView(R.id.appbar) AppBarLayout mAppBarLayout;
  @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingLayout;
  @BindView(R.id.toolbar) Toolbar mToolbar;

  @BindString(R.string.menu_about) String mAboutString;

  @Inject Px mPx;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    component(this).inject(this);

    setContentView(R.layout.activity_about);

    mAppBarLayout.addOnOffsetChangedListener(new ElevationOffsetListener(mPx.fromDpF(8)));

    final Typeface typeface = FontUtils.getFont(FontUtils.BOOK);
    mCollapsingLayout.setCollapsedTitleTypeface(typeface);
    mCollapsingLayout.setExpandedTitleTypeface(typeface);
    mCollapsingLayout.setTitle(mAboutString);

    setSupportActionBar(mToolbar);
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
