package com.deange.uwaterlooapi.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Formatter {

    private static final String TAG = "Formatter";

    private Formatter() { }

    public static Date parseDate(final String date, final String format) {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(date);

        } catch (final ParseException e) {
            Log.w(TAG, "Invalid date \"" + date + "\" for format \""+ format + "\"", e);
            return null;
        }
    }


}
