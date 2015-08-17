package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.foodservices.Outlet;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;

public class MenuFragment
        extends BaseModuleFragment<Response.Outlets, Outlet> {

    private static final String KEY_DAY_OF_WEEK = "day_of_week";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    public static <V extends BaseModel> Bundle newBundle(final V model, final int dayOfWeek) {
        final Bundle bundle = newBundle(model);
        bundle.putInt(KEY_DAY_OF_WEEK, dayOfWeek);
        return bundle;
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_foodservices_menu, null);

        mTabLayout = (TabLayout) view.findViewById(R.id.menu_tab_parent);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager = (ViewPager) view.findViewById(R.id.menu_tab_content);

        return view;
    }

    @Override
    public Outlet onLoadData() {
        return getModel();
    }

    @Override
    public void onBindData(final Metadata metadata, final Outlet outlet) {
        mViewPager.setAdapter(new MenuDayAdapter(outlet.getMenu()));
        mTabLayout.setupWithViewPager(mViewPager);

        final int dayOfWeekPosition = Math.min(
                getArguments().getInt(KEY_DAY_OF_WEEK) - 1, mTabLayout.getTabCount() - 1);

        mTabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(
                    final View v, final int l, final int t, final int r, final int b,
                    final int ol, final int ot, final int or, final int ob) {
                // Tab indicator does not move unless at least one layout pass has occurred
                mTabLayout.removeOnLayoutChangeListener(this);
                mTabLayout.getTabAt(dayOfWeekPosition).select();
            }
        });

    }

}
