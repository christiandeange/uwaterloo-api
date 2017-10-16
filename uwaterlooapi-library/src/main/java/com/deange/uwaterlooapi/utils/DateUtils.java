package com.deange.uwaterlooapi.utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DateUtils {

  private static final String TAG = "DateUtils";

  public static final List<String> MONTHS = Arrays.asList(
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December"
  );

  public static final String YMD = "yyyy-MM-dd";
  public static final String ISO8601 = "yyyy-MM-dd'T'hh:mm:ssZ";

  public static final int DATE_LENGTH_SHORT = 0;
  public static final int DATE_LENGTH_MEDIUM = 1;
  public static final int DATE_LENGTH_LONG = 2;

  private static final SimpleDateFormat sYMDFormat = new SimpleDateFormat(YMD, Locale.getDefault());
  private static final SimpleDateFormat sISO8601Format = new SimpleDateFormat(ISO8601,
                                                                              Locale.getDefault());

  private DateUtils() {
    throw new AssertionError();
  }

  public static Date parseDate(final String date, final String format) {
    try {
      final SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
      return dateFormat.parse(date);

    } catch (final ParseException e) {
      Log.w(TAG, "Invalid date \"" + date + "\" for format \"" + format + "\"", e);
      return null;
    }
  }

  public static Date parseDate(final String date) {

    if (YMD.length() == date.length()) {
      // YMD date format
      return parseDate(date, YMD);

    } else {
      // Assume ISO-8601 otherwise
      return parseDate(date, ISO8601);
    }

  }

  public static String formatDate(final Date date, final String format) {
    final SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
    return dateFormat.format(date);
  }

  public static String formatDateYMD(final Date date) {
    return sYMDFormat.format(date);
  }

  public static String formatDateISO8601(final Date date) {
    return sISO8601Format.format(date);
  }

  public static String formatDate(final Context context, final Date date, final int style) {
    final java.text.DateFormat dateFormat;
    switch (style) {
      case DATE_LENGTH_SHORT:
        dateFormat = DateFormat.getDateFormat(context);
        break;
      case DATE_LENGTH_MEDIUM:
        dateFormat = DateFormat.getMediumDateFormat(context);
        break;
      case DATE_LENGTH_LONG:
        dateFormat = DateFormat.getLongDateFormat(context);
        break;
      default:
        throw new IllegalStateException("Unexpected style '" + style + "'");
    }

    return dateFormat.format(date);
  }

}
