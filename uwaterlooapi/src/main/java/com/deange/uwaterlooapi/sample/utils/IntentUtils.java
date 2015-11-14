package com.deange.uwaterlooapi.sample.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public final class IntentUtils {

    private IntentUtils() {
        throw new AssertionError();
    }

    public static void openBrowser(final Context context, final String url) {
        if (!TextUtils.isEmpty(url)) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (isIntentSupported(context, intent)) {
                context.startActivity(intent);
            }
        }
    }

    public static String makeGeoIntentString(final float[] latLong, final String name) {
        // Examples:
        // geo:47.6,-122.3
        // geo:0,0?q=34.99,-106.61(Treasure)

        if (TextUtils.isEmpty(name)) {
            return "geo:" + latLong[0] + "," + latLong[1];
        } else {
            return "geo:0,0?q=" + latLong[0] + "," + latLong[1] + "(" + name + ")";
        }
    }

    public static boolean isIntentSupported(final Context context, final Intent intent) {
        return context.getPackageManager().resolveActivity(intent, 0) != null;
    }

}
