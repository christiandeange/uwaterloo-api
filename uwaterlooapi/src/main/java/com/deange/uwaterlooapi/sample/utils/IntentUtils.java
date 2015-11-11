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

    public static boolean isIntentSupported(final Context context, final Intent intent) {
        return context.getPackageManager().resolveActivity(intent, 0) != null;
    }

}
