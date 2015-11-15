package com.deange.uwaterlooapi.sample.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.PlatformUtils;

public class SliceView extends RelativeLayout {

    private static final float DEFAULT_HEIGHT = 100;
    private static final float DEFAULT_OFFSET =   0;

    private final Path mPath = new Path();

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

        if (!PlatformUtils.hasJellyBeanMR2()) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        if (PlatformUtils.hasLollipop()) {
            setOutlineProvider(new ViewOutlineProvider() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(final View view, final Outline outline) {
                    if (mPath.isConvex()) {
                        outline.setConvexPath(mPath);
                    }
                }
            });
        }

        if (getBackground() == null) {
            setBackgroundColor(Color.TRANSPARENT);
        }
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

        mPath.reset();
        mPath.moveTo(0                 , mSliceOffset);
        mPath.lineTo(getMeasuredWidth(), mSliceOffset + mSliceHeight);
        mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mPath.lineTo(0, getMeasuredHeight());
        mPath.lineTo(0, mSliceOffset);
    }

    @Override
    public void draw(final Canvas canvas) {
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        super.draw(canvas);
    }

}
