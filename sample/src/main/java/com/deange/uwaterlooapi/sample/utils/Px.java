package com.deange.uwaterlooapi.sample.utils;

import android.content.Context;

public final class Px {

  private static Context sContext;

  public static void init(final Context context) {
    sContext = context.getApplicationContext();
  }

  public static int fromDp(final float dp) {
    return (int) fromDpF(dp);
  }

  public static float fromDpF(final float dp) {
    return (sContext.getResources().getDisplayMetrics().density * dp);
  }

  public static int fromSp(final float sp) {
    return (int) fromSpF(sp);
  }

  public static float fromSpF(final float sp) {
    return (sContext.getResources().getDisplayMetrics().scaledDensity * sp);
  }

  public static int width() {
    return sContext.getResources().getDisplayMetrics().widthPixels;
  }

  public static int height() {
    return sContext.getResources().getDisplayMetrics().heightPixels;
  }

}
