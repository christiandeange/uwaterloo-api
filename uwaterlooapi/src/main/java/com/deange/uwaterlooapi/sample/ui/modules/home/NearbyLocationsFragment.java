package com.deange.uwaterlooapi.sample.ui.modules.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.BuildConfig;
import com.deange.uwaterlooapi.sample.R;
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

    private static final LocationRequest LOCATION_REQUEST =
            LocationRequest.create().setInterval(TimeUnit.MINUTES.toMillis(1));

    @Bind(R.id.nearby_locations_enable_permission) View mLocationPermission;

    private GoogleApiClient mApiClient;
    private List<Location> mLocations = new ArrayList<>();

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

        LocationServices.FusedLocationApi.removeLocationUpdates(mApiClient, this);
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

    private
    @NonNull
    List<Location> getClosest(final int takeCount, final float latitude, final float longitude) {
        if (mLocations.isEmpty()) {
            return new ArrayList<>();
        }

        final List<Location> locations = new ArrayList<>(mLocations);
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

        return locations.subList(0, takeCount);
    }

    @SuppressWarnings("MissingPermission")
    private void getLocationAndSubscribe() {
        new LocationTask(mApiClient).execute();
        LocationServices.FusedLocationApi.requestLocationUpdates(mApiClient, LOCATION_REQUEST, this);
    }

    @OnClick(R.id.nearby_locations_enable_permission)
    public void onLocationPermissionRequestClicked() {
        Nammu.askForPermission(getActivity(), MapManager.LOCATION_PERMISSION, this);
    }

    @Override
    public void onLocationChanged(final android.location.Location location) {
        final List<Location> closest = getClosest(3, (float) location.getLatitude(), (float) location.getLongitude());

        final List<String> names = new ArrayList<>();
        for (final Location place : closest) {
            names.add(place.getName());
        }
        Toast.makeText(getActivity(), names.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(@Nullable final Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(final int reasonCode) {
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

        private LocationTask(final GoogleApiClient apiClient) {
            mApiClient = apiClient;
        }

        @Override
        protected android.location.Location doInBackground(final Void... params) {

            final UWaterlooApi api = new UWaterlooApi(BuildConfig.UWATERLOO_API_KEY);
            final Response.Locations response = api.FoodServices.getLocations();
            mLocations = response.getData();

            return LocationServices.FusedLocationApi.getLastLocation(mApiClient);
        }

        @Override
        protected void onPostExecute(final android.location.Location location) {
            if (isAdded()) {
                onLocationChanged(location);
            }
        }

    }
}
