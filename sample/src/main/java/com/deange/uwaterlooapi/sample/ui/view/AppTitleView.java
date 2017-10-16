package com.deange.uwaterlooapi.sample.ui.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.FontUtils;

public class AppTitleView
    extends AppCompatTextView {

  // Animate only once per app lifetime
  private static boolean sDidAnimate;

  public AppTitleView(Context context) {
    this(context, null);
  }

  public AppTitleView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AppTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setText(getResources().getString(R.string.app_name).toLowerCase());
    FontUtils.apply(this, FontUtils.ULTRA);

    if (!sDidAnimate && !isInEditMode()) {
      sDidAnimate = true;

      final ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
      animator.addUpdateListener(anim -> setLetterSpacing((Float) anim.getAnimatedValue()));
      animator.setStartDelay(500L);
      animator.setDuration(2000L);
      animator.setInterpolator(new BounceInterpolator());
      animator.start();

      setAlpha(0);
      final ValueAnimator alphaAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0, 1);
      alphaAnimator.setStartDelay(500L);
      alphaAnimator.setDuration(1000L);
      alphaAnimator.setInterpolator(new FastOutSlowInInterpolator());
      alphaAnimator.start();
    }
  }

}
