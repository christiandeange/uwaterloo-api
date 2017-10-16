package com.deange.uwaterlooapi.sample.ui.modules.home;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.deange.uwaterlooapi.UWaterlooApi;
import com.deange.uwaterlooapi.model.common.Responses;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.BuildConfig;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.net.Calls;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.ui.modules.foodservices.LocationsFragment;
import com.deange.uwaterlooapi.sample.utils.MapUtils;
import com.deange.uwaterlooapi.sample.utils.NetworkController;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
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
    GoogleApiClient.OnConnectionFailedListener,
    NetworkController.OnNetworkChangedListener {

  private static final String KEY_ALL_LOCATIONS = "all_locations";
  private static final String KEY_MY_LOCATION = "my_location";

  private static final long ERROR_ANIMATION_DURATION = 1000L;
  private static final int LOCATION_AMOUNT = 3;
  private static final LocationRequest LOCATION_REQUEST =
      LocationRequest.create().setInterval(TimeUnit.SECONDS.toMillis(10));

  @BindView(R.id.nearby_locations_root) ViewGroup mRoot;
  @BindView(R.id.nearby_locations_enable_permission) View mLocationPermission;
  @BindView(R.id.nearby_locations_list) ListView mLocationsList;
  @BindView(R.id.nearby_locations_error) TextView mErrorView;

  private GoogleApiClient mApiClient;
  private NearbyLocationsAdapter mAdapter;
  private List<Location> mAllLocations;
  private android.location.Location mCurrentLocation;

  private final Handler mHandler = new Handler();
  private final Runnable mLocationsRunnable = new Runnable() {
    @Override
    public void run() {
      new LocationTask(mApiClient).execute();

      // Repeat again after a small delay
      mHandler.postDelayed(this, TimeUnit.SECONDS.toMillis(10));
    }
  };

  @Override
  public void onAttach(final Context context) {
    super.onAttach(context);

    if (mApiClient == null) {
      mApiClient = new GoogleApiClient.Builder(context)
          .addApi(LocationServices.API)
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .build();
    }

    mApiClient.connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mApiClient.disconnect();
  }

  @Override
  public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
      mAllLocations = savedInstanceState.getParcelableArrayList(KEY_ALL_LOCATIONS);
      mCurrentLocation = savedInstanceState.getParcelable(KEY_MY_LOCATION);
    }
  }

  @Nullable
  @Override
  public View onCreateView(
      final LayoutInflater inflater,
      @Nullable final ViewGroup container,
      @Nullable final Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_nearby_locations, container, false);

    ButterKnife.bind(this, view);

    mRoot.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

    mAdapter = new NearbyLocationsAdapter(getContext(), new ArrayList<>(), null);
    mLocationsList.setAdapter(mAdapter);

    showError(R.string.error_no_network, !NetworkController.getInstance().isConnected());

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();

    NetworkController.getInstance().registerListener(this);

    if (mApiClient.isConnected()) {
      startLocationUpdates();
    }
  }

  @Override
  public void onPause() {
    super.onPause();

    NetworkController.getInstance().unregisterListener(this);
    stopLocationUpdates();
  }

  @Override
  public void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);

    if (mAllLocations != null) {
      outState.putParcelableArrayList(KEY_ALL_LOCATIONS, new ArrayList<>(mAllLocations));
    }
    outState.putParcelable(KEY_MY_LOCATION, mCurrentLocation);
  }

  @Override
  public void onDestroyView() {
    NetworkController.getInstance().unregisterListener(this);

    super.onDestroyView();
  }

  private void startLocationUpdates() {
    if (getActivity() == null) {
      return;
    }

    if (!Nammu.hasPermission(getActivity(), MapUtils.LOCATION_PERMISSION)) {
      mLocationPermission.setVisibility(View.VISIBLE);
      return;
    } else {
      mLocationPermission.setVisibility(View.GONE);
    }

    getLocationAndSubscribe();
  }

  private void stopLocationUpdates() {
    mHandler.removeCallbacks(mLocationsRunnable);
    if (mApiClient.isConnected()) {
      LocationServices.FusedLocationApi.removeLocationUpdates(mApiClient, this);
    }
  }

  @NonNull
  private List<Location> getClosest(final int takeCount) {
    if (mAllLocations == null || mAllLocations.isEmpty() || takeCount <= 0 || mCurrentLocation == null) {
      return new ArrayList<>();
    }

    final float latitude = (float) mCurrentLocation.getLatitude();
    final float longitude = (float) mCurrentLocation.getLongitude();

    final List<Location> locations = new ArrayList<>();
    for (final Location location : mAllLocations) {
      if (location.isOpenNow()) {
        locations.add(location);
      }
    }

    final Map<Location, Float> distances = new HashMap<>();

    Collections.sort(locations, (lhs, rhs) -> {

      final float[] results = new float[1];

      final float distance1;
      if (distances.containsKey(lhs)) {
        distance1 = distances.get(lhs);

      } else {
        // Calculate distance from first location to user's location
        final float[] location1 = lhs.getLocation();
        android.location.Location.distanceBetween(latitude, longitude, location1[0], location1[1],
                                                  results);
        distance1 = results[0];
        distances.put(lhs, distance1);
      }

      final float distance2;
      if (distances.containsKey(rhs)) {
        distance2 = distances.get(rhs);

      } else {
        // Calculate distance from second location to user's location
        final float[] location2 = rhs.getLocation();
        android.location.Location.distanceBetween(latitude, longitude, location2[0], location2[1],
                                                  results);
        distance2 = results[0];
        distances.put(rhs, distance2);
      }

      // Put the closer of the two earlier in the list
      return Float.compare(distance1, distance2);
    });

    return locations.subList(0, Math.min(takeCount, locations.size()));
  }

  @SuppressWarnings("MissingPermission")
  private void getLocationAndSubscribe() {
    if (Nammu.hasPermission(getActivity(), MapUtils.LOCATION_PERMISSION)) {
      mHandler.post(mLocationsRunnable);
      if (mApiClient.isConnected()) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mApiClient, LOCATION_REQUEST,
                                                                 this);
      }
    }
  }

  @OnClick(R.id.nearby_locations_see_all)
  public void onSeeAllClicked() {
    startActivity(ModuleHostActivity.getStartIntent(getContext(), LocationsFragment.class));
  }

  @OnClick(R.id.nearby_locations_enable_permission)
  public void onLocationPermissionRequestClicked() {
    Nammu.askForPermission(getActivity(), MapUtils.LOCATION_PERMISSION, this);
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
    final List<Location> closestLocations = getClosest(LOCATION_AMOUNT);
    mAdapter.updateLocations(closestLocations);
    mAdapter.updateCurrentLocation(mCurrentLocation);

    showError(R.string.nearby_locations_none,
              (mAllLocations != null && closestLocations.isEmpty()));
  }

  private void showError(@StringRes final int resId, final boolean show) {
    if (show) {
      mErrorView.setText(resId);
      mErrorView.animate().alpha(1f).setDuration(ERROR_ANIMATION_DURATION).start();

    } else {
      // Another error may have changed this field's text
      if (TextUtils.equals(mErrorView.getText(), getString(resId))) {
        mErrorView.setText(null);
        mErrorView.animate().alpha(0f).setDuration(ERROR_ANIMATION_DURATION).start();
      }
    }
  }

  @Override
  public void onConnected(@Nullable final Bundle bundle) {
    startLocationUpdates();

    if (mCurrentLocation != null && mAllLocations != null) {
      updateAdapter();
    }
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

  @Override
  public void onNetworkChanged(final boolean connected) {
    if (getActivity() == null) {
      return;
    }

    showError(R.string.error_no_network, !connected);

    if (connected) {
      startLocationUpdates();
    } else {
      stopLocationUpdates();
    }
  }

  @SuppressWarnings("MissingPermission")
  private final class LocationTask
      extends AsyncTask<Void, Void, Boolean> {

    private final GoogleApiClient mApiClient;
    private Responses.Locations mResponse;
    private android.location.Location mLocation;

    private LocationTask(final GoogleApiClient apiClient) {
      mApiClient = apiClient;
    }

    @Override
    protected Boolean doInBackground(final Void... params) {
      try {
        final UWaterlooApi api = new UWaterlooApi(BuildConfig.UWATERLOO_API_KEY);
        mResponse = Calls.unwrap(api.FoodServices.getLocations());
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mApiClient);

        return true;

      } catch (final Exception e) {
        return false;
      }
    }

    @Override
    protected void onPostExecute(final Boolean success) {
      if (getActivity() != null) {
        if (success) {
          onLocationsLoaded(mResponse.getData());
          onLocationChanged(mLocation);
        }

        showError(R.string.nearby_locations_error_availability, mLocation == null);
        showError(R.string.error_no_network, !success);
      }
    }
  }

}
