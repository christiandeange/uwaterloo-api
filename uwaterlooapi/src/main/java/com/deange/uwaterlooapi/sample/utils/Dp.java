package com.deange.uwaterlooapi.sample.utils;

import android.content.Context;

public final class Dp {

    private static Context sContext;

    public static void init(final Context context) {
        sContext = context.getApplicationContext();
    }

    public static int toPx(final int dp) {
        return (int) (sContext.getResources().getDisplayMetrics().density * dp);
    }

    public static float fromPx(final int px) {
        return px / sContext.getResources().getDisplayMetrics().density;
    }

    public static int width() {
        return sContext.getResources().getDisplayMetrics().widthPixels;
    }

    public static int height() {
        return sContext.getResources().getDisplayMetrics().heightPixels;
    }

}
