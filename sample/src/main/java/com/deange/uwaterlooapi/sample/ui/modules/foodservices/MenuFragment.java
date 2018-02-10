package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.deange.uwaterlooapi.model.AbstractModel;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Responses;
import com.deange.uwaterlooapi.model.foodservices.Outlet;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import org.joda.time.LocalDate;

public class MenuFragment
    extends BaseModuleFragment<Responses.Outlets, Outlet> {

  private static final String KEY_DAY_OF_WEEK = "day_of_week";

  @BindView(R.id.tab_layout) TabLayout mTabLayout;
  @BindView(R.id.tab_content) ViewPager mViewPager;
  private MenuDayAdapter mAdapter;

  public static <V extends AbstractModel> Bundle newBundle(final V model, final int dayOfWeek) {
    final Bundle bundle = newBundle(model);
    bundle.putInt(KEY_DAY_OF_WEEK, dayOfWeek);
    return bundle;
  }

  @Override
  protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
    final View view = inflater.inflate(R.layout.view_tablayout_viewpager, parent, false);
    ButterKnife.bind(this, view);

    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    return view;
  }

  @Override
  public String getToolbarTitle() {
    return ((Outlet) getModel()).getName();
  }

  @Override
  public float getToolbarElevationPx() {
    return 0;
  }

  @Override
  public Outlet onLoadData() {
    return getModel();
  }

  @Override
  public void onBindData(final Metadata metadata, final Outlet outlet) {
    mAdapter = new MenuDayAdapter(outlet.getMenu());
    mViewPager.setAdapter(mAdapter);
    mTabLayout.setupWithViewPager(mViewPager);

    // Tab indicator does not move unless at least one layout pass has occurred
    mTabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
      @Override
      public void onLayoutChange(
          final View v, final int l, final int t, final int r, final int b,
          final int ol, final int ot, final int or, final int ob) {
        mTabLayout.removeOnLayoutChangeListener(this);
        final int dayOfWeek = getArguments().getInt(KEY_DAY_OF_WEEK);

        for (int i = 0; i < mAdapter.getCount(); ++i) {
          final LocalDate date = LocalDate.fromDateFields(mAdapter.getItem(i).getDate());
          if (date.getDayOfWeek() == dayOfWeek) {
            mTabLayout.getTabAt(i).select();
          }
        }
      }
    });

  }

  @Override
  public String getContentType() {
    return ModuleType.MENU;
  }

}
