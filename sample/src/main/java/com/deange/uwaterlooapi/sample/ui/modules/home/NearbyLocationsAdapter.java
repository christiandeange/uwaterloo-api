package com.deange.uwaterlooapi.sample.ui.modules.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.modules.foodservices.LocationFragment;
import com.deange.uwaterlooapi.sample.utils.MathUtils;

import java.util.List;
import java.util.Locale;

public class NearbyLocationsAdapter
    extends ArrayAdapter<Location>
    implements
    View.OnClickListener {

  private final float[] mDistanceHolder = new float[1];
  private float[] mCurrentLocation;

  public NearbyLocationsAdapter(
      final Context context,
      final List<Location> locations,
      final android.location.Location currentLocation) {
    super(context, 0, locations);

    if (currentLocation != null) {
      mCurrentLocation = new float[]{
          (float) currentLocation.getLatitude(),
          (float) currentLocation.getLongitude(),
      };
    }
  }

  public void updateCurrentLocation(final android.location.Location currentLocation) {
    if (currentLocation != null) {
      mCurrentLocation = new float[]{
          (float) currentLocation.getLatitude(),
          (float) currentLocation.getLongitude(),
      };

    } else {
      mCurrentLocation = null;
    }

    notifyDataSetChanged();
  }

  public void updateLocations(final List<Location> locations) {
    clear();
    addAll(locations);

    notifyDataSetChanged();
  }

  @Override
  public View getView(final int position, final View convertView, final ViewGroup parent) {
    final View view;
    if (convertView != null) {
      view = convertView;
    } else {
      view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_nearby_location, parent,
                                                       false);
    }

    final Location location = getItem(position);

    ((TextView) view.findViewById(R.id.nearby_location_title)).setText(location.getName());
    ((TextView) view.findViewById(R.id.nearby_location_distance)).setText(formatDistance(location));

    view.setOnClickListener(this);
    view.setTag(position);

    return view;
  }

  private String formatDistance(final Location location) {
    if (mCurrentLocation == null) {
      return getContext().getString(R.string.nearby_locations_waiting);
    }

    final float[] coordinates = location.getLocation();
    android.location.Location.distanceBetween(
        mCurrentLocation[0], mCurrentLocation[1], coordinates[0], coordinates[1], mDistanceHolder);
    float distance = mDistanceHolder[0];

    String suffix = "m";
    if (distance > 1000) {
      suffix = "km";
      distance /= 1000f;
    }

    return MathUtils.formatFloat(String.format((Locale) null, "%.1f", distance)) + " " + suffix;
  }

  @Override
  public void onClick(final View v) {
    final int position = (int) v.getTag();
    final Location location = getItem(position);

    getContext().startActivity(ModuleHostActivity.getStartIntent(
        getContext(), LocationFragment.class, BaseModuleFragment.newBundle(location)));
  }
}
