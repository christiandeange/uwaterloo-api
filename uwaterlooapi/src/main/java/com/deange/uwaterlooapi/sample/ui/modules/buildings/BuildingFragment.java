package com.deange.uwaterlooapi.sample.ui.modules.buildings;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.buildings.BuildingSection;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.FragmentInfo;
import com.deange.uwaterlooapi.sample.ui.StringAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.ApiFragment;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.PropertyLayout;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

@ApiFragment("/buildings/*")
public class BuildingFragment extends BaseModuleFragment<Response.BuildingEntity, Building>
        implements AdapterView.OnItemClickListener {

    public static final String TAG = BuildingFragment.class.getSimpleName();
    public static final String ARG_BUILDING_CODE = "building_code";

    private SupportMapFragment mMapFragment;
    private ViewGroup mRoot;
    private Building mBuilding;

    public static Bundle newBundle(final String buildingCode) {
        Bundle args = new Bundle();
        args.putString(ARG_BUILDING_CODE, buildingCode);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        mRoot = (ViewGroup) inflater.inflate(R.layout.fragment_building, null);

        PropertyLayout.resize(mRoot);

        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentByTag(TAG);
        if (mMapFragment != null) {
            transaction.remove(mMapFragment);
        }

        final GoogleMapOptions options = new GoogleMapOptions().mapType(GoogleMap.MAP_TYPE_HYBRID);
        mMapFragment = SupportMapFragment.newInstance(options);

        transaction
                .add(R.id.building_map, mMapFragment, TAG)
                .commit();

        MapsInitializer.initialize(getActivity());

        return mRoot;
    }

    @Override
    public Response.BuildingEntity onLoadData(final UWaterlooApi api) {
        return api.Buildings.getBuilding(getArguments().getString(ARG_BUILDING_CODE));
    }

    @Override
    public void onBindData(final Metadata metadata, final Building data) {
        mBuilding = data;

        ((TextView) mRoot.findViewById(R.id.building_name)).setText(mBuilding.getBuildingName());
        ((TextView) mRoot.findViewById(R.id.building_code)).setText(mBuilding.getBuildingCode());
        ((TextView) mRoot.findViewById(R.id.building_id)).setText(mBuilding.getBuildingId());
        ((TextView) mRoot.findViewById(R.id.building_alternate_names)).setText(
                Joiner.on('\n').join(mBuilding.getAlternateNames()));

        final ListView listView = (ListView) mRoot.findViewById(R.id.building_alternate_sections);
        if (mBuilding.getBuildingSections().isEmpty()) {
            listView.setVisibility(View.GONE);

        } else {
            // Add the building itself to the list
            final List<Object> sections = new ArrayList<Object>(mBuilding.getBuildingSections());
            sections.add(0, mBuilding.getBuildingName());
            listView.setAdapter(new StringAdapter(getActivity(), sections));
            listView.setOnItemClickListener(this);
        }

        showLocation(mBuilding.getBuildingName(), mBuilding.getLocation());
    }

    private void showLocation(final String buildingName, final float[] location) {
        final LatLng buildingLocation = new LatLng(location[0], location[1]);
        final GoogleMap map = mMapFragment.getMap();

        if (map == null) {
            new UpdateMapTask(buildingName, location).execute();

        } else {
            map.clear();
            map.setMyLocationEnabled(false);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(buildingLocation, 17));
            map.addMarker(new MarkerOptions()
                    .title(buildingName)
                    .position(buildingLocation));
        }
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view,
                            final int position, final long id) {
        if (mBuilding == null) {
            return;

        } else if (position == 0) {
            showLocation(mBuilding.getBuildingName(), mBuilding.getLocation());

        } else {
            final BuildingSection section = mBuilding.getBuildingSections().get(position - 1);
            showLocation(section.getSectionName(), section.getLocation());
        }
    }

    @Override
    public FragmentInfo getFragmentInfo(final Context context) {
        return new Info(context);
    }

    public final class Info extends FragmentInfo {

        public Info(final Context context) {
            super(context);
        }

        @Override
        public String getActionBarTitle() {
            return getContext().getString(R.string.api_buildings);
        }
    }

    private final class UpdateMapTask extends AsyncTask<Void, Void, Void> {

        private final String mName;
        private final float[] mLocation;

        public UpdateMapTask(final String name, final float[] location) {
            mName = name;
            mLocation = location;
        }

        @Override
        protected Void doInBackground(final Void... voids) {

            try {
                // Retry for 5s at most, every 500ms
                int count = 0;
                while (mMapFragment.getMap() == null && count < 10) {
                    count++;
                    Thread.sleep(500);
                }
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            if (getActivity() != null) {
                if (mMapFragment.getMap() != null) {
                    showLocation(mName, mLocation);
                }
            }
        }
    }
}
