
package com.deange.uwaterlooapi.blurs;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class BlurFrameLayout extends FrameLayout {

    private Blurrer mBlur;
    private boolean parentDrawn;

    public BlurFrameLayout(final Context context) {
        this(context, null);
    }

    public BlurFrameLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurFrameLayout(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        setupBlurrer();
        mBlur.setUpStylableAttributes(attrs, R.styleable.BlurFrameLayout,
                R.styleable.BlurFrameLayout_blurRadius);
    }

    private void setupBlurrer() {
        mBlur = new Blurrer(this);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBlur.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void draw(final Canvas canvas) {

        // Avoid recursive explosion
        if (parentDrawn) {
            return;
        }

        parentDrawn = true;

        mBlur.draw(canvas);
        super.draw(canvas);
        parentDrawn = false;
    }

    public void setBlurRadius(final float blurRadius) {
        mBlur.setBlurRadius(blurRadius);
    }

    public float getBlurRadius() {
        return mBlur.getBlurRadius();
    }

}
