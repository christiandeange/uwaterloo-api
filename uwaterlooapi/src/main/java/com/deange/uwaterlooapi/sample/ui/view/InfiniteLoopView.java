package com.deange.uwaterlooapi.sample.ui.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Keep;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.Px;

public class InfiniteLoopView extends View {

    private static final float PULL = 0.552284749831f; // 4/3 * tan(π/8)

    private final Paint mPaintForeground = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintEnd = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Path mPath = new Path();
    private final Path mPathForeground = new Path();
    private final PathMeasure mPathMeasure = new PathMeasure();

    private final RectF mBounds = new RectF();
    private float[] mProgressStart = new float[2];
    private float[] mProgressEnd = new float[2];
    private float[] mTan = new float[2];

    private float mPhase;
    private float mPathBackgroundLength;

    public InfiniteLoopView(final Context context) {
        this(context, null);
    }

    public InfiniteLoopView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfiniteLoopView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public InfiniteLoopView(
            final Context context,
            final AttributeSet attrs,
            final int defStyleAttr,
            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InfiniteLoopView, defStyleAttr, defStyleRes);
        final float strokeWidth = a.getDimension(R.styleable.InfiniteLoopView_progressStrokeWidth, Px.fromDpF(4));
        a.recycle();

        initPaints();
        initPath();
        updateStrokeWidth(strokeWidth);

        initAnimator();
    }

    private void initAnimator() {
        final ObjectAnimator animatorPhase = ObjectAnimator.ofFloat(this, "phase", 0f, 1f);
        animatorPhase.setDuration(4000L);
        animatorPhase.setRepeatMode(ValueAnimator.RESTART);
        animatorPhase.setRepeatCount(ValueAnimator.INFINITE);
        animatorPhase.setInterpolator(new LinearInterpolator());
        animatorPhase.start();
    }

    @Keep
    public void setPhase(final float phase) {
        mPhase = phase;
        invalidate();
    }

    private void initPaints() {
        mPaintForeground.setStrokeCap(Paint.Cap.ROUND);
        mPaintForeground.setStyle(Paint.Style.STROKE);
        mPaintForeground.setColor(Color.BLACK);

        mPaintEnd.setStyle(Paint.Style.FILL);
        mPaintEnd.setColor(Color.BLACK);
    }

    private void updateStrokeWidth(final float strokeWidth) {
        mPaintForeground.setStrokeWidth(strokeWidth);
        mPaintEnd.setStrokeWidth(strokeWidth / 2f);
    }

    static float lerp(final float v0, final float v1, final float t) {
        return v0 + t * (v1 - v0);
    }

    private void initPath() {
        mPath.reset();

        final RectF b = mBounds;
        final PointF c = new PointF(mBounds.centerX(), mBounds.centerY());

        final float x0 = c.x;
        final float y0 = c.y;

        final float x1 = lerp(c.x, b.left, 0.5f);
        final float y1 = b.top;
        final float x2 = b.left;
        final float y2 = c.y;
        final float x3 = lerp(b.left, c.x, 0.5f);
        final float y3 = b.bottom;
        final float x4 = c.x;
        final float y4 = c.y;
        final float x5 = lerp(c.x, b.right, 0.5f);
        final float y5 = b.top;
        final float x6 = b.right;
        final float y6 = c.y;
        final float x7 = lerp(b.right, c.x, 0.5f);
        final float y7 = b.bottom;
        final float x8 = c.x;
        final float y8 = c.y;

        mPath.moveTo(x0, y0);
        mPath.cubicTo(
                x0, lerp(y0, y1, PULL),
                lerp(x1, x0, PULL), y1,
                x1, y1);

        mPath.cubicTo(
                lerp(x1, x2, PULL), y1,
                x2, lerp(y2, y1, PULL),
                x2, y2);

        mPath.cubicTo(
                x2, lerp(y2, y3, PULL),
                lerp(x3, x2, PULL), y3,
                x3, y3);

        mPath.cubicTo(
                lerp(x3, x4, PULL), y3,
                x4, lerp(y4, y3, PULL),
                x4, y4);

        mPath.cubicTo(
                x4, lerp(y4, y5, PULL),
                lerp(x5, x4, PULL), y5,
                x5, y5);

        mPath.cubicTo(
                lerp(x5, x6, PULL), y5,
                x6, lerp(y6, y5, PULL),
                x6, y6);

        mPath.cubicTo(
                x6, lerp(y6, y7, PULL),
                lerp(x7, x6, PULL), y7,
                x7, y7);

        mPath.cubicTo(
                lerp(x7, x8, PULL), y7,
                x8, lerp(y8, y7, PULL),
                x8, y8);

        mPathMeasure.setPath(mPath, true);
        mPathBackgroundLength = mPathMeasure.getLength();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        updateProgress();

        final float strokeWidth = mPaintForeground.getStrokeWidth() / 2f;

        canvas.drawPath(mPathForeground, mPaintForeground);
        canvas.drawCircle(mProgressStart[0], mProgressStart[1], strokeWidth, mPaintEnd);
        canvas.drawCircle(mProgressEnd[0], mProgressEnd[1], strokeWidth, mPaintEnd);
    }

    private void updateProgress() {
        mPathForeground.reset();

        final float start = mPathBackgroundLength * mPhase;
        final float end = (start + (mPathBackgroundLength * 0.875f)) % mPathBackgroundLength;

        if (start < end) {
            mPathMeasure.getSegment(start, end, mPathForeground, true);

        } else {
            mPathMeasure.getSegment(start, mPathBackgroundLength, mPathForeground, true);
            mPathMeasure.getSegment(0, end, mPathForeground, false);
        }

        mPathMeasure.getPosTan(start, mProgressStart, mTan);
        mPathMeasure.getPosTan(end, mProgressEnd, mTan);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        final int pt = getPaddingTop();
        final int pb = getPaddingBottom();
        final int pl = getPaddingLeft();
        final int pr = getPaddingRight();

        final int width = w - pl - pr;
        final int height = h - pt - pb;
        final float s = getStrokeWidth() / 2f;

        float circleDiameter = height - getStrokeWidth();
        float requiredWidth = (circleDiameter * 2) + getStrokeWidth();
        if (requiredWidth < width) {
            // Width is bigger, add horizontal padding
            final float extra = width - requiredWidth;
            mBounds.set(pl + extra / 2f, pt, w - pr - extra / 2f, h - pb);

        } else {
            // Height is bigger, add vertical padding
            circleDiameter = (width - s * 2) / 2f;
            final float requiredHeight = circleDiameter + s * 2;
            final float extra = height - requiredHeight;
            mBounds.set(pl, pt + extra / 2f, w - pr, h - pb - extra / 2f);
        }

        final float inset = (float) Math.ceil(s);
        mBounds.inset(inset, inset);

        initPath();
        invalidate();
    }

    public int getProgressColor() {
        return mPaintForeground.getColor();
    }

    public void setProgressColor(final int progressColor) {
        mPaintForeground.setColor(progressColor);
        mPaintEnd.setColor(progressColor);

        invalidate();
    }

    public float getStrokeWidth() {
        return mPaintForeground.getStrokeWidth();
    }

    public void setStrokeWidth(final float strokeWidth) {
        updateStrokeWidth(strokeWidth);

        requestLayout();
        invalidate();
    }

}