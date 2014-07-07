package com.deange.uwaterlooapi.sample.ui.modules.buildings;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.BaseModuleFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuildingFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class BuildingFragment extends BaseModuleFragment<Response.BuildingEntity, Building> {

    private static final String ARG_BUILDING_CODE = "building_code";

    private TextView mBuildingNameView;
    private TextView mBuildingCodeView;

    public static BuildingFragment newInstance(final String buildingCode) {
        BuildingFragment fragment = new BuildingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BUILDING_CODE, buildingCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_building, null);

        mBuildingNameView = (TextView) root.findViewById(R.id.building_name);
        mBuildingCodeView = (TextView) root.findViewById(R.id.building_code);

        return root;
    }

    @Override
    public Response.BuildingEntity onLoadData(final UWaterlooApi api) {
        return api.Buildings.getBuilding(getArguments().getString(ARG_BUILDING_CODE));
    }

    @Override
    public void onBindData(final Metadata metadata, final Building data) {
        mBuildingNameView.setText(data.getBuildingName());
        mBuildingCodeView.setText(data.getBuildingCode());
    }
}
