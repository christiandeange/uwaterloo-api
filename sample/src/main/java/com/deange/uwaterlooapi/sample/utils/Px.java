package com.deange.uwaterlooapi.sample.utils;

import android.content.Context;

import javax.inject.Inject;

public final class Px {

  private Context mContext;

  @Inject
  Px(final Context context) {
    mContext = context;
  }

  public int fromDp(final float dp) {
    return (int) fromDpF(dp);
  }

  public float fromDpF(final float dp) {
    return (mContext.getResources().getDisplayMetrics().density * dp);
  }

  public int fromSp(final float sp) {
    return (int) fromSpF(sp);
  }

  public float fromSpF(final float sp) {
    return (mContext.getResources().getDisplayMetrics().scaledDensity * sp);
  }

  public int screenW() {
    return mContext.getResources().getDisplayMetrics().widthPixels;
  }

  public int screenH() {
    return mContext.getResources().getDisplayMetrics().heightPixels;
  }

}
