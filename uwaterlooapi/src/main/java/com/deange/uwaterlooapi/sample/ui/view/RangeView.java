package com.deange.uwaterlooapi.sample.ui.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class RangeView extends View {

    private static final ArgbEvaluator sEvaluator = new ArgbEvaluator();

    private final Paint mThumbPaint = new Paint();
    private final Paint mLinePaint = new Paint();
    private final Rect mBounds = new Rect();

    private boolean mThumbShown;
    private int mThumbRadius;
    private float mMin;
    private float mMax;
    private float mValue;

    private int mStartColour = Color.rgb(0x00, 0xE5, 0xFF);
    private int mEndColour   = Color.rgb(0xF5, 0x00, 0x57);

    public RangeView(final Context context) {
        this(context, null);
    }

    public RangeView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mThumbRadius = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        mThumbPaint.setAntiAlias(true);
        mThumbPaint.setStyle(Paint.Style.FILL);
        mThumbPaint.setColor(Color.rgb(0x33, 0xB5, 0xE5));

        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(mThumbRadius * 2, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        // Calculate drawing bounds
        getDrawingRect(mBounds);
        mBounds.left += mThumbRadius;
        mBounds.right -= mThumbRadius;

        // Calculate how far along the line the "thumb" will be
        final float normalized = getNormalizedValue();
        final int thumbX = (int) (mBounds.left + (mBounds.width() * normalized));
        final int middleY = mBounds.top + mBounds.height() / 2;

        // Draw the line
        canvas.drawLine(mBounds.left, middleY, mBounds.right, middleY, mLinePaint);

        if (mThumbShown) {
            // Normalize colour between two endpoints
            final int normalizedColour =
                    (int) sEvaluator.evaluate(normalized, mStartColour, mEndColour);
            mThumbPaint.setColor(normalizedColour);
            canvas.drawCircle(thumbX, middleY, mThumbRadius, mThumbPaint);
        }
    }

    private float getNormalizedValue() {
        // Avoid division by 0
        final float normalized = (mMax == mMin) ? mMax : (mValue - mMin) / (mMax - mMin);
        return Math.max(0, Math.min(normalized, 1));
    }

    private int getNormalizedRangeCode() {
        final float normalized = (mMax == mMin) ? mMax : (mValue - mMin) / (mMax - mMin);
        return normalized < 0 ? -1 : (normalized > 1 ? 1 : 0);
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

    public int getThumbRadius() {
        return mThumbRadius;
    }

    public int getStartColour() {
        return mStartColour;
    }

    public int getEndColour() {
        return mEndColour;
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
        mThumbShown = true;
        mValue = value;
        invalidate();
    }

    public void setThumbRadius(final int thumbRadius) {
        mThumbRadius = thumbRadius;
        requestLayout();
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

    public void setThumbShown(final boolean thumbShown) {
        mThumbShown = thumbShown;
        invalidate();
    }
}
