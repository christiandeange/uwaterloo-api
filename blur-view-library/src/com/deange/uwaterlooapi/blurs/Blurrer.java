package com.deange.uwaterlooapi.blurs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @see <a href="https://github.com/masconsult/blur-view-library">blur-view-library</a>
 */
public class Blurrer {

    private static final int DEFAULT_BLUR_RADIUS = 15;
    private static final int MAX_BLUR_RADIUS = 25;

    private RenderScript renderScript;
    private ScriptIntrinsicBlur blurIntrinsic;

    private final ViewGroup mView;
    private Bitmap blurredBackground;

    private boolean parentDrawn = false;
    private Canvas blurCanvas;
    private Allocation in;
    private Allocation out;

    private float blurRadius = DEFAULT_BLUR_RADIUS;

    public Blurrer(final ViewGroup view) {
        mView = view;
        setUpBlurIntrinsic(view.getContext());
    }

    private void setUpBlurIntrinsic(final Context context) {
        renderScript = RenderScript.create(context);
        blurIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
    }

    public void setUpStylableAttributes(final AttributeSet attrs, final int[] theme,
                                        final int radiusAttr) {

        float blurRadius = DEFAULT_BLUR_RADIUS;
        TypedArray a = null;
        try {
            a = mView.getContext().getTheme().obtainStyledAttributes(attrs, theme, 0, 0);
            blurRadius = a.getFloat(radiusAttr, DEFAULT_BLUR_RADIUS);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }

        mView.setWillNotDraw(false);
        setBlurRadius(blurRadius);
    }

    public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        createBitmaps();
    }

    private void createBitmaps() {
        final Bitmap originalBackground = Bitmap.createBitmap(
                mView.getMeasuredWidth(), mView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        blurredBackground = Bitmap.createBitmap(
                mView.getMeasuredWidth(), mView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        in = Allocation.createFromBitmap(renderScript, originalBackground);
        out = Allocation.createFromBitmap(renderScript, blurredBackground);
        blurCanvas = new Canvas(originalBackground);
    }

    private void blur() {
        blurIntrinsic.setRadius(blurRadius);
        blurIntrinsic.setInput(in);
        blurIntrinsic.forEach(out);
        out.copyTo(blurredBackground);
    }

    public void draw(final Canvas canvas) {
        drawParentInBitmap((View) mView.getParent());

        blur();
        canvas.drawBitmap(blurredBackground, 0, 0, null);
    }

    private void drawParentInBitmap(final View v) {
        blurCanvas.save();
        blurCanvas.translate(-mView.getLeft(), -mView.getTop());
        v.draw(blurCanvas);
        blurCanvas.restore();
    }

    public void setBlurRadius(final float blurRadius) {
        this.blurRadius = blurRadius;
        mView.invalidate();
    }

    public float getBlurRadius() {
        return blurRadius;
    }
}
