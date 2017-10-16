package com.deange.uwaterlooapi.sample.utils;

import android.content.Context;
import android.content.res.Resources;

import com.deange.uwaterlooapi.sample.R;

import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Instant;
import org.joda.time.Minutes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

  private static final DateFormat sDateFormat = new SimpleDateFormat("MMM dd, yyyy");
  private static final DateFormat sDateTimeFormat = new SimpleDateFormat("hh:mm aa, MMM dd");

  private DateUtils() {
    throw new UnsupportedOperationException();
  }

  public static String formatDateTime(final Date date) {
    return sDateTimeFormat.format(date);
  }

  public static String formatDate(final Date date) {
    return sDateFormat.format(date);
  }

  public static String formatDate(final Context context, final Date date) {
    return android.text.format.DateFormat.getMediumDateFormat(context).format(date);
  }

  public static String formatTime(final Context context, final Date date) {
    return android.text.format.DateFormat.getTimeFormat(context).format(date);
  }

  public static String getTimeDifference(
      final Resources resources,
      final long millis) {
    final long now = System.currentTimeMillis();
    final Instant instantNow = new Instant(now);
    final Instant instantMillis = new Instant(millis);

    final int days = Math.abs(Days.daysBetween(instantMillis, instantNow).getDays());
    final int hours = Math.abs(Hours.hoursBetween(instantMillis, instantNow).getHours());
    final int minutes = Math.abs(Minutes.minutesBetween(instantMillis, instantNow).getMinutes());

    boolean isFormattedAsNow = false;
    final String prettyDuration;
    if (days != 0) {
      prettyDuration = days + " " + resources.getQuantityString(R.plurals.duration_days, days);

    } else if (hours != 0) {
      prettyDuration = hours + " " + resources.getQuantityString(R.plurals.duration_hours, hours);

    } else if (minutes != 0) {
      prettyDuration = minutes + " " + resources.getQuantityString(R.plurals.duration_minutes,
                                                                   minutes);

    } else {
      prettyDuration = resources.getString(R.string.duration_instant);
      isFormattedAsNow = true;
    }

    final String fullDuration;
    if (isFormattedAsNow) {
      fullDuration = prettyDuration;
    } else if (instantMillis.isBefore(instantNow)) {
      fullDuration = prettyDuration + " " + resources.getString(R.string.duration_ago);
    } else {
      fullDuration = resources.getString(R.string.duration_until) + " " + prettyDuration;
    }

    return fullDuration;
  }

}
