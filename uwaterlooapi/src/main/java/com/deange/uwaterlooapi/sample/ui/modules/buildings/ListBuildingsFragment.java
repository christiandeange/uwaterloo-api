package com.deange.uwaterlooapi.sample.ui.modules.buildings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleIndexedAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import retrofit2.Call;

@ModuleFragment(
        path = "/buildings/list",
        layout = R.layout.module_buildings
)
public class ListBuildingsFragment
        extends BaseListModuleFragment<Response.Buildings, Building>
        implements
        View.OnClickListener {

    private final List<Building> mResponse = new ArrayList<>();
    private String[] mIndices;

    @Override
    public String getToolbarTitle() {
        return getString(R.string.api_maps);
    }

    @Override
    public Call<Response.Buildings> onLoadData(final UWaterlooApi api) {
        return api.Buildings.getBuildings();
    }

    @Override
    public void onBindData(final Metadata metadata, final List<Building> data) {
        mResponse.clear();
        for (final Building building : data) {
            // Unfortunately many buildings do not have location data
            // We need to filter those ones out
            if (building.hasLocation()) {
                mResponse.add(building);
            }
        }

        Collections.sort(mResponse, (lhs, rhs) -> lhs.getBuildingName().compareTo(rhs.getBuildingName()));

        final Set<String> indices = new TreeSet<>();
        for (final Building building : mResponse) {
            indices.add(String.valueOf(building.getBuildingName().charAt(0)));
        }

        mIndices = indices.toArray(new String[indices.size()]);

        getListView().setFastScrollEnabled(true);
        getListView().setFastScrollAlwaysVisible(true);
        notifyDataSetChanged();
    }

    @Override
    public String getContentType() {
        return ModuleType.BUILDINGS;
    }

    @Override
    public ModuleAdapter getAdapter() {
        return new BuildingsAdapter(getActivity());
    }

    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();
        final Building building = mResponse.get(position);
        showModule(BuildingFragment.class, BuildingFragment.newBundle(building));
    }

    private final class BuildingsAdapter
            extends ModuleIndexedAdapter<String> {

        public BuildingsAdapter(final Context context) {
            super(context);
        }

        @Override
        public int getListItemLayoutId() {
            return R.layout.list_item_building;
        }

        @Override
        public void bindView(final Context context, final int position, final View view) {

            view.setTag(position);
            view.setOnClickListener(ListBuildingsFragment.this);

            final TextView header = (TextView) view.findViewById(R.id.building_abbreviation);
            final TextView buildingName = (TextView) view.findViewById(R.id.building_name);

            final String firstLetter = getFirstCharOf(position);
            if (position == 0 || !firstLetter.equals(getFirstCharOf(position - 1))) {
                header.setText(firstLetter);
            } else {
                header.setText("");
            }

            buildingName.setText(getItem(position).getBuildingName());
        }

        @Override
        public int getCount() {
            return mResponse == null ? 0 : mResponse.size();
        }

        @NonNull
        @Override
        public Building getItem(final int position) {
            return mResponse.get(position);
        }

        @Override
        public String[] getSections() {
            return mIndices;
        }

        public String getFirstCharOf(final int position) {
            return String.valueOf(getItem(position).getBuildingName().charAt(0));
        }
    }
}
