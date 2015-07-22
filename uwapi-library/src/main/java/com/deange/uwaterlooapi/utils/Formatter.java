package com.deange.uwaterlooapi.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Formatter {

    private static final String TAG = "Formatter";

    public static final String YMD = "yyyy-MM-dd";
    public static final String ISO8601 = "yyyy-MM-dd'T'hh:mm:ssZ";

    private static final SimpleDateFormat sYMDFormat = new SimpleDateFormat(YMD);
    private static final SimpleDateFormat sISO8601Format = new SimpleDateFormat(ISO8601);

    private Formatter() { }

    public static Date parseDate(final String date, final String format) {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return dateFormat.parse(date);

        } catch (final ParseException e) {
            Log.w(TAG, "Invalid date \"" + date + "\" for format \""+ format + "\"", e);
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


}
