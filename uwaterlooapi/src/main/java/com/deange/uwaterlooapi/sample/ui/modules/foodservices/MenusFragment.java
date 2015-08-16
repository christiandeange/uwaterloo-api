package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.foodservices.MenuInfo;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;

@ModuleFragment(
        path = "/foodservices/menu",
        base = true,
        icon = R.drawable.ic_launcher
)
public class MenusFragment
        extends BaseModuleFragment<Response.Menus, MenuInfo>
        implements
        ModuleAdapter.ModuleListItemListener {

    private ListView mListView;
    private OutletsAdapter mAdapter;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_foodservices_menus, null);

        mListView = (ListView) view.findViewById(android.R.id.list);

        return view;
    }

    @Override
    public Response.Menus onLoadData(final UWaterlooApi api) {
        return api.FoodServices.getWeeklyMenu();
    }

    @Override
    public void onBindData(final Metadata metadata, final MenuInfo data) {
        mAdapter = new OutletsAdapter(getActivity(), this, data);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(final int position) {
        showModule(MenuFragment.class, MenuFragment.newBundle(mAdapter.getItem(position)));
    }

}
