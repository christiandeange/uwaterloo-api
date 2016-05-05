package com.deange.uwaterlooapi.sample.ui.modules.home;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.BuildConfig;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.ui.modules.foodservices.LocationsFragment;
import com.deange.uwaterlooapi.sample.utils.MapManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class NearbyLocationsFragment
        extends Fragment
        implements
        LocationListener,
        PermissionCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int LOCATION_AMOUNT = 3;
    private static final LocationRequest LOCATION_REQUEST =
            LocationRequest.create().setInterval(TimeUnit.SECONDS.toMillis(10));

    @Bind(R.id.nearby_locations_enable_permission) View mLocationPermission;
    @Bind(R.id.nearby_locations_list) ListView mLocationsList;

    private GoogleApiClient mApiClient;
    private NearbyLocationsAdapter mAdapter;
    private List<Location> mAllLocations = new ArrayList<>();
    private android.location.Location mCurrentLocation;

    private final Handler mHandler = new Handler();
    private final Runnable mLocationsRunnable = new Runnable() {
        @Override
        public void run() {
            new LocationTask(mApiClient).execute();

            // Repeat again in a minute
            mHandler.postDelayed(this, TimeUnit.SECONDS.toMillis(10));
        }
    };

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        mApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_nearby_locations, container, false);

        ButterKnife.bind(this, view);

        ((ViewGroup) view).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        mAdapter = new NearbyLocationsAdapter(getContext(), new ArrayList<Location>(), null);
        mLocationsList.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        stopLocationUpdates();
    }

    private void startLocationUpdates() {
        if (getActivity() == null) {
            return;
        }

        if (!Nammu.hasPermission(getActivity(), MapManager.LOCATION_PERMISSION)) {
            mLocationPermission.setVisibility(View.VISIBLE);
            return;
        } else {
            mLocationPermission.setVisibility(View.GONE);
        }

        getLocationAndSubscribe();
    }

    private void stopLocationUpdates() {
        mHandler.removeCallbacks(mLocationsRunnable);
        LocationServices.FusedLocationApi.removeLocationUpdates(mApiClient, this);
    }

    @NonNull
    private List<Location> getClosest(final int takeCount, final android.location.Location currentLocation) {
        if (mAllLocations.isEmpty() || takeCount <= 0 || currentLocation == null) {
            return new ArrayList<>();
        }

        final float latitude = (float) currentLocation.getLatitude();
        final float longitude = (float) currentLocation.getLongitude();

        final List<Location> locations = new ArrayList<>();
        for (final Location location : mAllLocations) {
            if (location.isOpenNow()) {
                locations.add(location);
            }
        }

        final Map<Location, Float> distances = new HashMap<>();

        Collections.sort(locations, new Comparator<Location>() {
            @Override
            public int compare(final Location lhs, final Location rhs) {

                final float[] results = new float[1];

                final float distance1;
                if (distances.containsKey(lhs)) {
                    distance1 = distances.get(lhs);

                } else {
                    // Calculate distance from first location to user's location
                    final float[] location1 = lhs.getLocation();
                    android.location.Location.distanceBetween(latitude, longitude, location1[0], location1[1], results);
                    distance1 = results[0];
                    distances.put(lhs, distance1);
                }

                final float distance2;
                if (distances.containsKey(rhs)) {
                    distance2 = distances.get(rhs);

                } else {
                    // Calculate distance from second location to user's location
                    final float[] location2 = rhs.getLocation();
                    android.location.Location.distanceBetween(latitude, longitude, location2[0], location2[1], results);
                    distance2 = results[0];
                    distances.put(rhs, distance2);
                }

                // Put the closer of the two earlier in the list
                return Float.compare(distance1, distance2);
            }
        });

        return locations.subList(0, Math.min(takeCount, locations.size()));
    }

    @SuppressWarnings("MissingPermission")
    private void getLocationAndSubscribe() {
        if (Nammu.hasPermission(getActivity(), MapManager.LOCATION_PERMISSION)) {
            mHandler.post(mLocationsRunnable);
            LocationServices.FusedLocationApi.requestLocationUpdates(mApiClient, LOCATION_REQUEST, this);
        }
    }

    @OnClick(R.id.nearby_locations_see_all)
    public void onSeeAllClicked() {
        getContext().startActivity(ModuleHostActivity.getStartIntent(getContext(), LocationsFragment.class));
    }

    @OnClick(R.id.nearby_locations_enable_permission)
    public void onLocationPermissionRequestClicked() {
        Nammu.askForPermission(getActivity(), MapManager.LOCATION_PERMISSION, this);
    }

    @Override
    public void onLocationChanged(final android.location.Location location) {
        mCurrentLocation = location;
        updateAdapter();
    }

    private void onLocationsLoaded(final List<Location> locations) {
        mAllLocations = locations;
        updateAdapter();
    }

    private void updateAdapter() {
        final List<Location> closestLocations = getClosest(LOCATION_AMOUNT, mCurrentLocation);
        mAdapter.updateLocations(closestLocations);
        mAdapter.updateCurrentLocation(mCurrentLocation);
    }

    @Override
    public void onConnected(@Nullable final Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(final int reasonCode) {
        stopLocationUpdates();
    }

    @Override
    public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {
    }

    @Override
    public void permissionGranted() {
        mLocationPermission.setVisibility(View.GONE);
        getLocationAndSubscribe();
    }

    @Override
    public void permissionRefused() {
    }

    @SuppressWarnings("MissingPermission")
    private final class LocationTask
            extends AsyncTask<Void, Void, android.location.Location> {

        private final GoogleApiClient mApiClient;
        private Response.Locations mResponse;

        private LocationTask(final GoogleApiClient apiClient) {
            mApiClient = apiClient;
        }

        @Override
        protected android.location.Location doInBackground(final Void... params) {

            final UWaterlooApi api = new UWaterlooApi(BuildConfig.UWATERLOO_API_KEY);
            mResponse = api.FoodServices.getLocations();

            return LocationServices.FusedLocationApi.getLastLocation(mApiClient);
        }

        @Override
        protected void onPostExecute(final android.location.Location location) {
            if (isAdded()) {
                onLocationsLoaded(mResponse.getData());
                onLocationChanged(location);
            }
        }
    }

}
