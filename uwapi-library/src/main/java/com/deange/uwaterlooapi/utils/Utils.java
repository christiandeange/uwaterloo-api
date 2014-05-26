package com.deange.uwaterlooapi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Utils {

    private Utils() { }

    public static boolean equals(final Object a, final Object b) {
        return (a == null) ? b == null : a.equals(b);
    }

    public static String streamToString(final InputStream in) throws IOException {

        final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        final StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        reader.close();

        return sb.toString();
    }

    public static int convertBool(final boolean b) {
        return b ? 1 : 0;
    }

    public static boolean convertBool(final int i) {
        return i != 0;
    }

}
