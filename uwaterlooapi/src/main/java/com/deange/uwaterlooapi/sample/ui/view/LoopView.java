package com.deange.uwaterlooapi.sample.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Keep;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.Px;

public class LoopView
        extends View {

    private static final float SLICE_DEGREES = 315;
    private static final long DURATION = 800L;

    private final float mPadding;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // First entry is start angle, second is sweep angle
    private final float[] mAngles1 = new float[2];
    private final float[] mAngles2 = new float[2];

    private final Runnable mCreateAnimatorRunnable = new Runnable() {
        @Override
        public void run() {
            createAnimator();
        }
    };

    public LoopView(final Context context) {
        this(context, null);
    }

    public LoopView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoopView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopView);
        final float strokeWidth;
        if (!isInEditMode()) {
            strokeWidth = a.getDimension(R.styleable.LoopView_strokeWidth, Px.fromSpF(1));
        }  else {
            strokeWidth = 5;
        }
        a.recycle();

        mPadding = strokeWidth / 2f;

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        setStartAngle1(0f);
        setSweepAngle1(SLICE_DEGREES);
        setStartAngle2(SLICE_DEGREES - 180);
        setSweepAngle2(-SLICE_DEGREES);

        post(mCreateAnimatorRunnable);
    }

    private void createAnimator() {
        final ObjectAnimator sweepAngle1;
        final ObjectAnimator sweepAngle2;
        final ObjectAnimator sweepAngle1Second;
        final ObjectAnimator sweepAngle2Second;

        sweepAngle1 = ObjectAnimator.ofPropertyValuesHolder(this,
                PropertyValuesHolder.ofFloat("sweepAngle1", SLICE_DEGREES, 0));
        sweepAngle1.setStartDelay(DURATION / 4);
        sweepAngle1.setInterpolator(new AccelerateInterpolator());
        sweepAngle1.setDuration(DURATION);

        sweepAngle2 = ObjectAnimator.ofPropertyValuesHolder(this,
                PropertyValuesHolder.ofFloat("sweepAngle2", -SLICE_DEGREES, 0));
        sweepAngle2.setInterpolator(new DecelerateInterpolator());
        sweepAngle2.setDuration(DURATION);

        sweepAngle1Second = ObjectAnimator.ofPropertyValuesHolder(this,
                PropertyValuesHolder.ofFloat("sweepAngle1", 0, -SLICE_DEGREES));
        sweepAngle1Second.setStartDelay(DURATION / 4);
        sweepAngle1Second.setInterpolator(new AccelerateInterpolator());
        sweepAngle1Second.setDuration(DURATION);

        sweepAngle2Second = ObjectAnimator.ofPropertyValuesHolder(this,
                PropertyValuesHolder.ofFloat("sweepAngle2", 0, SLICE_DEGREES));
        sweepAngle2Second.setInterpolator(new DecelerateInterpolator());
        sweepAngle2Second.setDuration(DURATION);

        sweepAngle1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(final Animator animation) {
                setStartAngle1(0f);
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                sweepAngle2.start();
            }
        });

        sweepAngle2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(final Animator animation) {
                setStartAngle2(SLICE_DEGREES - 180);
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                sweepAngle1Second.start();
            }
        });

        sweepAngle1Second.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(final Animator animation) {
                setStartAngle1(SLICE_DEGREES);
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                sweepAngle2Second.start();
            }
        });

        sweepAngle2Second.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(final Animator animation) {
                setStartAngle2(180f);
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                sweepAngle1.start();
            }
        });

        // Start animation loop
        sweepAngle1.start();
    }

    public void setStartAngle1(final float angle) {
        mAngles1[0] = angle;
    }

    public void setStartAngle2(final float angle) {
        mAngles2[0] = angle;
    }

    @Keep
    public void setSweepAngle1(final float angle) {
        mAngles1[1] = angle;
        invalidate();
    }

    @Keep
    public void setSweepAngle2(final float angle) {
        mAngles2[1] = angle;
        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        final int w = getMeasuredWidth();
        final int h = getMeasuredHeight();

        final float l = getPaddingStart() + mPadding;
        final float r = w - getPaddingEnd() - mPadding;
        final float t = getPaddingTop() + mPadding;
        final float b = h - getPaddingBottom() - mPadding;

        canvas.drawArc(l, t, w / 2, b, mAngles1[0], mAngles1[1], false, mPaint);
        canvas.drawArc(w / 2, t, r, b, mAngles2[0], mAngles2[1], false, mPaint);
    }

}
