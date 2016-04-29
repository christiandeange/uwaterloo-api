package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.foodservices.MenuInfo;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.DateSelectorView;

import org.joda.time.LocalDate;

import butterknife.Bind;
import butterknife.ButterKnife;

@ModuleFragment(
        path = "/foodservices/menu",
        layout = R.layout.module_foodservices_menus
)
public class MenusFragment
        extends BaseModuleFragment<Response.Menus, MenuInfo>
        implements
        ModuleListItemListener,
        DateSelectorView.OnDateChangedListener {

    @Bind(R.id.fragment_date_selector) DateSelectorView mDateSelector;
    @Bind(android.R.id.list) ListView mListView;

    private OutletsAdapter mAdapter;

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
        final View view = inflater.inflate(R.layout.fragment_foodservices_menus, parent, false);
        ButterKnife.bind(this, view);

        mDateSelector.setOnDateSetListener(this);

        return view;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_foodservices_menus);
    }

    @Override
    public Response.Menus onLoadData(final UWaterlooApi api) {
        final LocalDate date = mDateSelector.getDate();
        final int year = date.getYear();
        final int week = date.getWeekOfWeekyear();

        return api.FoodServices.getWeeklyMenu(year, week);
    }

    @Override
    public void onBindData(final Metadata metadata, final MenuInfo data) {
        mAdapter = new OutletsAdapter(getActivity(), this, data);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public String getContentType() {
        return ModuleType.MENUS;
    }

    @Override
    public void onItemClicked(final int position) {
        final int dayOfWeek = mDateSelector.getDate().getDayOfWeek(); // range [1,7]
        showModule(MenuFragment.class, MenuFragment.newBundle(mAdapter.getItem(position), dayOfWeek));
    }

    @Override
    public void onDateSet(final int year, final int monthOfYear, final int dayOfMonth) {
        doRefresh();
    }
}
