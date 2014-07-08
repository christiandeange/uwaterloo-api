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


}
