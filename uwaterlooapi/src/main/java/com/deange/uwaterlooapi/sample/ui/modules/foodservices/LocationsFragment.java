package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.StringAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ModuleFragment(
        path = "/foodservices/locations",
        base = true,
        icon = R.drawable.ic_launcher
)
public class LocationsFragment
        extends BaseListModuleFragment<Response.Locations, Location>
        implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private static final Comparator<Location> sComparator = new Comparator<Location>() {
        @Override
        public int compare(final Location l1, final Location l2) {
            return l1.getName() == null ? -1 : l1.getName().compareTo(l2.getName());
        }
    };

    private LocationAdapter mAdapter;
    private List<Location> mAllLocations = Collections.unmodifiableList(new ArrayList<Location>());
    private final List<Location> mDataLocations = new ArrayList<>();

    private Boolean mFilterOpenOnly = null;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View root = super.getContentView(inflater, savedInstanceState);

        getListView().setOnItemClickListener(this);
        getListView().setDivider(null);
        getListView().setDividerHeight(0);

        final StringAdapter filterAdapter = new StringAdapter(getActivity(),
                Arrays.asList(getResources().getStringArray(R.array.foodservices_locations_array)));
        final Spinner spinner = (Spinner) root.findViewById(R.id.locations_filter_spinner);
        spinner.setAdapter(filterAdapter);
        spinner.setOnItemSelectedListener(this);

        return root;
    }

    @Override
    public ModuleAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_foodservices_locations;
    }

    @Override
    public Response.Locations onLoadData(final UWaterlooApi api) {
        return api.FoodServices.getLocations();
    }

    @Override
    public void onBindData(final Metadata metadata, final List<Location> data) {
        mAllLocations = Collections.unmodifiableList(data);
        bindAndFilterData();
    }

    private void bindAndFilterData() {

        mDataLocations.clear();
        for (final Location location : mAllLocations) {
            if (mFilterOpenOnly == null || mFilterOpenOnly == location.isOpenNow()) {
                mDataLocations.add(location);
            }
        }

        Collections.sort(mDataLocations, sComparator);

        mAdapter = new LocationAdapter(getActivity(), mDataLocations);
        getListView().setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        showFragment(new LocationFragment(), true,
                LocationFragment.newBundle(mAdapter.getItem(position)));
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
        switch (position) {
            case 0:
                mFilterOpenOnly = null;
                break;
            case 1:
                mFilterOpenOnly = true;
                break;
            case 2:
                mFilterOpenOnly = false;
                break;
            default:
                throw new ArrayIndexOutOfBoundsException();
        }

        bindAndFilterData();
    }

    @Override
    public void onNothingSelected(final AdapterView<?> parent) {
    }
}
