package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class UwProgressBar extends ProgressBar {
  public UwProgressBar(final Context context) {
    super(context);
    init();
  }

  public UwProgressBar(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public UwProgressBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
  }
}
