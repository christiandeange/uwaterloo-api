package com.deange.uwaterlooapi.sample.ui.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.deange.uwaterlooapi.sample.R;

public class RangeView extends View {

    private static final ArgbEvaluator sEvaluator = new ArgbEvaluator();

    private final Paint mThumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Rect mBounds = new Rect();
    private final Path mPath = new Path();

    private float mMin;
    private float mMax;
    private float mValue;

    private int mStartColour;
    private int mEndColour;

    public RangeView(final Context context) {
        this(context, null);
    }

    public RangeView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RangeView);
        mStartColour = a.getColor(R.styleable.RangeView_startColour,
                getResources().getColor(android.R.color.holo_blue_bright));
        mEndColour = a.getColor(R.styleable.RangeView_endColour,
                getResources().getColor(android.R.color.holo_red_dark));
        a.recycle();

        init();
    }

    private void init() {
        final DisplayMetrics dm = getResources().getDisplayMetrics();
        mThumbPaint.setStyle(Paint.Style.FILL);

        mLinePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        // Calculate drawing bounds
        getDrawingRect(mBounds);

        // Calculate how far along the line the "thumb" will be
        final float normalized = getNormalizedValue();
        final int colour = (int) sEvaluator.evaluate(normalized, mStartColour, mEndColour);

        final float middleY = mBounds.top + mBounds.height() / 2;

        // Draw the line
        mLinePaint.setColor(colour);
        canvas.drawLine(mBounds.left, middleY, mBounds.right, middleY, mLinePaint);

        // Draw the path curves
        final float thumbX = mBounds.left + (mBounds.width() * normalized);
        final float firstThird = (thumbX - mBounds.left) / 3f;
        final float secondThird = (mBounds.right - thumbX) / 3f;

        mPath.reset();
        mPath.moveTo(mBounds.left, middleY);

        mPath.cubicTo(
                mBounds.left + firstThird, middleY,
                thumbX - firstThird, mBounds.top,
                thumbX, mBounds.top);

        mPath.cubicTo(
                thumbX + secondThird, mBounds.top,
                mBounds.right - secondThird, middleY,
                mBounds.right, middleY);

        mPath.cubicTo(
                mBounds.right - secondThird, middleY,
                thumbX + secondThird, mBounds.bottom,
                thumbX, mBounds.bottom);

        mPath.cubicTo(
                thumbX - firstThird, mBounds.bottom,
                mBounds.left + firstThird, middleY,
                mBounds.left, middleY);

        // Normalize colour between two endpoints
        mThumbPaint.setColor(colour);
        canvas.drawPath(mPath, mThumbPaint);
    }

    private float getNormalizedValue() {
        // Avoid division by 0
        final float normalized = (mMax == mMin) ? mMax : (mValue - mMin) / (mMax - mMin);
        return Math.max(0, Math.min(normalized, 1));
    }

    public float getMin() {
        return mMin;
    }

    public float getMax() {
        return mMax;
    }

    public float getValue() {
        return mValue;
    }

    public void setMin(final float min) {
        mMin = min;
        invalidate();
    }

    public void setMax(final float max) {
        mMax = max;
        invalidate();
    }

    public void setValue(final float value) {
        mValue = value;
        invalidate();
    }

    public void setStartColour(final int startColour) {
        mStartColour = startColour;
        invalidate();
    }

    public void setEndColour(final int endColour) {
        mEndColour = endColour;
        invalidate();
    }

}
