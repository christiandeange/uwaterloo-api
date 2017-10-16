package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.model.foodservices.OperatingHours;
import com.deange.uwaterlooapi.model.foodservices.SpecialOperatingHours;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleIndexedAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class LocationAdapter
    extends ModuleIndexedAdapter<String> {

  private final List<Location> mLocations;
  private final String[] mIndices;

  public LocationAdapter(
      final Context context,
      final List<Location> locations,
      final ModuleListItemListener listener) {
    super(context, listener);
    mLocations = locations;

    final Set<String> indices = new TreeSet<>();
    for (int i = 0; i < mLocations.size(); i++) {
      indices.add(getFirstCharOf(i));
    }
    mIndices = indices.toArray(new String[indices.size()]);
  }

  @Override
  public View newView(final Context context, final int position, final ViewGroup parent) {
    return LayoutInflater
        .from(context)
        .inflate(R.layout.list_item_foodservices_location, parent, false);
  }

  @Override
  public void bindView(final Context context, final int position, final View view) {
    final Location location = getItem(position);

    final TextView titleView = (TextView) view.findViewById(R.id.list_location_title);
    final TextView locationView = (TextView) view.findViewById(R.id.list_location_building);
    final TextView timingView = (TextView) view.findViewById(R.id.list_location_timing_desc);
    final View iconView = view.findViewById(R.id.list_location_timing_icon);

    final String[] split = location.getName().split(" - ");
    titleView.setText(split[0]);
    ViewUtils.setText(locationView, (split.length == 2) ? split[1] : null);

    final Resources r = context.getResources();
    final Resources.Theme t = context.getTheme();
    final int color;

    if (!location.isOpenNow()) {
      color = ResourcesCompat.getColor(r, R.color.foodservices_location_closed, t);

      timingView.setText(R.string.foodservices_location_closed_now);

    } else {
      color = ResourcesCompat.getColor(r, R.color.foodservices_location_open, t);

      final LocalTime closing = getClosingTime(location);
      final String timeFormat = "h" + (closing.getMinuteOfHour() == 0 ? "" : ":mm") + " a";
      timingView.setText(r.getString(
          R.string.foodservices_location_closes_at, closing.toString(timeFormat)));
    }

    timingView.setTextColor(color);
    iconView.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
  }

  @Override
  public int getCount() {
    return mLocations.size();
  }

  @Override
  public Location getItem(final int position) {
    return mLocations.get(position);
  }

  @Override
  public boolean areAllItemsEnabled() {
    return true;
  }

  @Override
  public String[] getSections() {
    return mIndices;
  }

  @Override
  public String getFirstCharOf(final int position) {
    return String.valueOf(getItem(position).getName().charAt(0));
  }

  public static LocalTime getClosingTime(final Location location) {

    LocalTime closing = null;
    final LocalDate today = LocalDate.now();
    final DateTimeFormatter format = DateTimeFormat.forPattern(OperatingHours.TIME_FORMAT);

    // Check for special hours for this particular day
    for (final SpecialOperatingHours specialDay : location.getSpecialOperatingHoursRaw()) {
      final LocalDate day = LocalDate.fromDateFields(specialDay.getDate());
      final LocalTime openTime = LocalTime.parse(specialDay.getOpeningHour(), format);
      final LocalTime closeTime = LocalTime.parse(specialDay.getClosingHour(), format);

      if (day.isEqual(today)
          || (day.plusDays(1).equals(today) && closeTime.isBefore(openTime))) {
        // Check for post-midnight hours as well
        closing = LocalTime.parse(specialDay.getClosingHour(), format);
      }
    }

    // Check the normal operating hours schedule (if necessary)
    if (closing == null) {
      final int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
      final OperatingHours hours = location.getHours(OperatingHours.WEEKDAYS.get(day));
      final LocalTime opening = LocalTime.parse(hours.getOpeningHour(), format);

      if (opening.isAfter(DateTime.now().toLocalTime())) {
        // It is open but opens after now, we need to look at the hours for yesterday
        // since it closes after midnight from yesterday
        final int yesterday = (day == Calendar.SUNDAY) ? Calendar.SATURDAY : day - 1;
        final OperatingHours yesterdayHours =
            location.getHours(OperatingHours.WEEKDAYS.get(yesterday));
        closing = LocalTime.parse(yesterdayHours.getClosingHour(), format);

      } else {
        closing = LocalTime.parse(hours.getClosingHour(), format);
      }
    }

    return closing;
  }
}
