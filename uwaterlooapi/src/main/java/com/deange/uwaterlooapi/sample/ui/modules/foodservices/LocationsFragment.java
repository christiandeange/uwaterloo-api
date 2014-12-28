package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import com.deange.uwaterlooapi.sample.ui.modules.buildings.BuildingFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LocationsFragment extends BaseListModuleFragment<Response.Locations, Location> implements AdapterView.OnItemClickListener {

    private static final Comparator<Location> sComparator = new Comparator<Location>() {
        @Override
        public int compare(final Location l1, final Location l2) {
            return l1.getName() == null ? -1 : l1.getName().compareTo(l2.getName());
        }
    };

    private LocationAdapter mAdapter;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View root = super.getContentView(inflater, savedInstanceState);

        getListView().setOnItemClickListener(this);
        getListView().setDivider(null);
        getListView().setDividerHeight(0);

        return root;
    }

    @Override
    public ModuleAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public Response.Locations onLoadData(final UWaterlooApi api) {
        return api.FoodServices.getLocations();
    }

    @Override
    public void onBindData(final Metadata metadata, final List<Location> data) {

        Collections.sort(data, sComparator);

        mAdapter = new LocationAdapter(getActivity(), data);
        getListView().setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        showFragment(new LocationFragment(), true,
                LocationFragment.newBundle(mAdapter.getItem(position)));
    }
}
