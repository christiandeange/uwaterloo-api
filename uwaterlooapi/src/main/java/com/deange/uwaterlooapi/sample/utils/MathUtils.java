package com.deange.uwaterlooapi.sample.utils;

import android.text.TextUtils;

public final class MathUtils {

    private MathUtils() {
        throw new AssertionError();
    }

    public static float clamp(final float f, final float min, final float max) {
        return (f < min ? min : (f > max ? max : f));
    }

    public static String formatFloat(final float num) {
        return formatFloat(String.valueOf(num));
    }

    public static String formatFloat(String str) {
        if (TextUtils.isEmpty(str)) return str;

        str = str.replaceAll("^0+", "");
        str = str.replaceAll("\\.0+$", "");

        return (str.isEmpty() ? "0" : str);
    }

}
