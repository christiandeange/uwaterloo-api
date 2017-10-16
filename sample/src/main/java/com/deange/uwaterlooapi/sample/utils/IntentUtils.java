package com.deange.uwaterlooapi.sample.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;

import com.deange.uwaterlooapi.sample.R;

public final class IntentUtils {

  private static final String EXTRA_CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";
  private static final String EXTRA_CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";

  private IntentUtils() {
    throw new AssertionError();
  }

  public static void openBrowser(final Context context, final String url) {
    if (!TextUtils.isEmpty(url)) {
      final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

      // Enable Chrome Custom Tabs
      final int color = ResourcesCompat.getColor(context.getResources(), R.color.uw_yellow,
                                                 context.getTheme());
      final IBinder binder = null;

      final Bundle extras = new Bundle();
      extras.putInt(EXTRA_CUSTOM_TABS_TOOLBAR_COLOR, color);
      extras.putBinder(EXTRA_CUSTOM_TABS_SESSION, binder);
      intent.putExtras(extras);

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
