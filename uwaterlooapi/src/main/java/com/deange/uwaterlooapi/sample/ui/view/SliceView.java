package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.deange.uwaterlooapi.sample.R;

public class SliceView extends RelativeLayout {

    private static final float DEFAULT_HEIGHT = 100;
    private static final float DEFAULT_OFFSET =   0;

    private final Paint mPaint = new Paint();
    private final Path mPath = new Path();

    private boolean mHasBeenLayout;
    private Drawable mDrawable;

    private float mSliceOffset;
    private float mSliceHeight;

    public SliceView(final Context context) {
        this(context, null);
    }

    public SliceView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliceView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        final TypedArray a =
                getContext().obtainStyledAttributes(attrs, R.styleable.SliceView, defStyle, 0);
        if (a != null) {
            mSliceHeight = a.getDimensionPixelSize(
                    R.styleable.SliceView_sliceHeight, (int) DEFAULT_HEIGHT);
            mSliceOffset = a.getDimensionPixelSize(
                    R.styleable.SliceView_sliceOffset, (int) DEFAULT_OFFSET);
            a.recycle();

        } else {
            mSliceHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_HEIGHT,
                    getResources().getDisplayMetrics());
            mSliceOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_OFFSET,
                    getResources().getDisplayMetrics());
        }

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        if (mDrawable == null) {
            // Don't overwrite the background if specified as an attr
            mPaint.setColor(Color.WHITE);
        }

        super.setBackgroundColor(Color.TRANSPARENT);
    }

    public float getSliceOffset() {
        return mSliceOffset;
    }

    public void setSliceOffset(final int slicePixelsOffset) {
        mSliceOffset = slicePixelsOffset;
        requestLayout();
        invalidate();
    }

    public float getSliceHeight() {
        return mSliceHeight;
    }

    public void setSliceHeight(final int slicePixelsHeight) {
        mSliceHeight = slicePixelsHeight;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onLayout(final boolean changed,
                            final int l, final int t, final int r, final int b) {
        super.onLayout(changed, l, t, r, b);
        mHasBeenLayout = true;

        setBackground(mDrawable != null ? mDrawable : getBackground());

        mPath.reset();
        mPath.moveTo(0                 , mSliceOffset);
        mPath.lineTo(getMeasuredWidth(), mSliceOffset + mSliceHeight);
        mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mPath.lineTo(0                 , getMeasuredHeight());
        mPath.lineTo(0                 , mSliceOffset);
    }

    @Override
    public void setBackgroundColor(final int color) {
        mPaint.setShader(null);
        mPaint.setColor(color);
    }

    @Override
    public void setBackgroundResource(final int resid) {
        setBackgroundDrawable(getResources().getDrawable(resid));
    }

    @Override
    public void setBackground(final Drawable background) {
        setBackgroundDrawable(background);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBackgroundDrawable(final Drawable background) {
        if (background instanceof ColorDrawable) {
            super.setBackgroundDrawable(background);
        } else if (background instanceof BitmapDrawable) {
            setBackground(((BitmapDrawable) background).getBitmap());
        } else {
            setBackground(convertToBitmap(background));
        }
    }

    private void setBackground(final Bitmap bitmap) {
        if (bitmap != null) {
            final Shader.TileMode mode = Shader.TileMode.CLAMP;
            mPaint.setShader(new BitmapShader(bitmap, mode, mode));
        }
    }

    private Bitmap convertToBitmap(final Drawable drawable) {

        if (drawable == null) {
            return null;

        } else if (!mHasBeenLayout) {
            mDrawable = drawable;
            return null;

        } else {
            final Bitmap mutableBitmap = Bitmap.createBitmap(
                    getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(mutableBitmap);
            drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            drawable.draw(canvas);
            return mutableBitmap;
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

}
