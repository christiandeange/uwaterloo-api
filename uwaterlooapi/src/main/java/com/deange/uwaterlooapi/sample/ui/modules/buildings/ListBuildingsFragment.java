package com.deange.uwaterlooapi.sample.ui.modules.buildings;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@ModuleFragment(
        path = "/buildings/list",
        layout = R.layout.module_buildings
)
public class ListBuildingsFragment
        extends BaseListModuleFragment<Response.Buildings, Building>
        implements
        View.OnClickListener {

    private List<Building> mResponse;
    private Character[] mIndices;

    @Override
    public String getToolbarTitle() {
        return getString(R.string.api_buildings);
    }

    @Override
    public Response.Buildings onLoadData(final UWaterlooApi api) {
        return api.Buildings.getBuildings();
    }

    @Override
    public void onBindData(final Metadata metadata, final List<Building> data) {
        mResponse = data;
        Collections.sort(mResponse, new Comparator<Building>() {
            @Override
            public int compare(final Building lhs, final Building rhs) {
                return lhs.getBuildingName().compareTo(rhs.getBuildingName());
            }
        });

        final Set<Character> indices = new TreeSet<>();
        for (final Building building : mResponse) {
            indices.add(building.getBuildingName().charAt(0));
        }

        mIndices = indices.toArray(new Character[indices.size()]);

        getListView().setFastScrollEnabled(true);
        getListView().setFastScrollAlwaysVisible(true);
        notifyDataSetChanged();
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

    private final class BuildingsAdapter extends ModuleAdapter implements SectionIndexer {

        public BuildingsAdapter(final Context context) {
            super(context);
        }

        @Override
        public int getListItemLayoutId() {
            return R.layout.list_item_condensed;
        }

        @Override
        public void bindView(final Context context, final int position, final View view) {

            view.setTag(position);
            view.setOnClickListener(ListBuildingsFragment.this);

            final TextView header = (TextView) view.findViewById(R.id.header_view);
            final TextView buildingName = (TextView) view.findViewById(android.R.id.text1);

            final char firstLetter = getFirstCharOf(position);
            if (position == 0 || getFirstCharOf(position - 1) != firstLetter) {
                header.setText("" + firstLetter);
            } else {
                header.setText("");
            }

            buildingName.setText(getItem(position).getBuildingName());
        }

        @Override
        public int getCount() {
            return mResponse == null ? 0 : mResponse.size();
        }

        @Override
        public Building getItem(final int position) {
            return mResponse == null ? null : mResponse.get(position);
        }

        @Override
        public Character[] getSections() {
            return mIndices;
        }

        @Override
        public int getPositionForSection(final int sectionIndex) {
            final Character letter = getSections()[sectionIndex];

            int first = 0;
            while (first < mResponse.size() && getFirstCharOf(first) != letter) {
                first++;
            }

            return first;
        }

        @Override
        public int getSectionForPosition(final int position) {
            return Arrays.asList(getSections()).indexOf(getFirstCharOf(position));
        }

        public Character getFirstCharOf(final int position) {
            return getItem(position).getBuildingName().charAt(0);
        }
    }
}
