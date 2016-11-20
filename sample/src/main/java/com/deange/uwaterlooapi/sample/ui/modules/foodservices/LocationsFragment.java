package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Responses;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.ui.StringAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;

@ModuleFragment(
        path = "/foodservices/locations",
        layout = R.layout.module_foodservices_locations
)
public class LocationsFragment
        extends BaseListModuleFragment<Responses.Locations, Location>
        implements
        AdapterView.OnItemSelectedListener,
        ModuleListItemListener {

    private static final Comparator<Location> sComparator = (l1, l2) -> l1.getName() == null ? -1 : l1.getName().compareTo(l2.getName());

    private LocationAdapter mAdapter;
    private List<Location> mAllLocations = Collections.unmodifiableList(new ArrayList<Location>());
    private final List<Location> mDataLocations = new ArrayList<>();

    private LocationFilter mFilter = LocationFilter.NONE;

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
        final View root = super.getContentView(inflater, parent);

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
    public String getToolbarTitle() {
        return getString(R.string.title_foodservices_locations);
    }

    @Override
    public float getToolbarElevationPx() {
        return 0;
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
    public Call<Responses.Locations> onLoadData(final UWaterlooApi api) {
        return api.FoodServices.getLocations();
    }

    @Override
    public void onBindData(final Metadata metadata, final List<Location> data) {
        mAllLocations = Collections.unmodifiableList(data);
        bindAndFilterData();
    }

    @Override
    public String getContentType() {
        return ModuleType.LOCATIONS;
    }

    private void bindAndFilterData() {
        mDataLocations.clear();
        for (final Location location : mAllLocations) {
            if (mFilter.keep(location)) {
                mDataLocations.add(location);
            }
        }

        Collections.sort(mDataLocations, sComparator);

        mAdapter = new LocationAdapter(getActivity(), mDataLocations, this);

        getListView().setFastScrollEnabled(true);
        getListView().setAdapter(mAdapter);
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
        mFilter = LocationFilter.FILTERS[position];
        bindAndFilterData();
    }

    @Override
    public void onNothingSelected(final AdapterView<?> parent) {
    }

    @Override
    public void onItemClicked(final int position) {
        showModule(LocationFragment.class,
                LocationFragment.newBundle(mAdapter.getItem(position)));
    }

    @SuppressWarnings({ "Convert2Lambda", "Anonymous2MethodRef" })
    private interface LocationFilter {
        boolean keep(final Location location);

        LocationFilter NONE = new LocationFilter() {
            @Override
            public boolean keep(final Location location) {
                return true;
            }
        };

        LocationFilter OPEN = new LocationFilter() {
            @Override
            public boolean keep(final Location location) {
                return location.isOpenNow();
            }
        };

        LocationFilter TIM_HORTONS = new LocationFilter() {
            @Override
            public boolean keep(final Location location) {
                return location.getName().contains("Tim Hortons");
            }
        };

        LocationFilter[] FILTERS = new LocationFilter[] { NONE, OPEN, TIM_HORTONS };
    }
}
