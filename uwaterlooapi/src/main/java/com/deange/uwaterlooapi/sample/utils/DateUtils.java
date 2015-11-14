package com.deange.uwaterlooapi.sample.utils;

import android.content.Context;

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

}
