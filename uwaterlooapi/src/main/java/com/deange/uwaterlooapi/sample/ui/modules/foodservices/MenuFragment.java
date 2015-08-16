package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.foodservices.Menu;
import com.deange.uwaterlooapi.model.foodservices.Outlet;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;

public class MenuFragment
        extends BaseModuleFragment<Response.Outlets, Outlet> {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

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
    }

}
