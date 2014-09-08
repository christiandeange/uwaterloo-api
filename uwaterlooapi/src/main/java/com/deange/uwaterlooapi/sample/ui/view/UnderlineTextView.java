package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * @author Eric Frohnhoefer
 */
public class UnderlineTextView  extends TextView {
    private final Paint mPaint = new Paint();
    private int mUnderlineHeight = 0;

    public UnderlineTextView(final Context context) {
        this(context, null);
    }

    public UnderlineTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderlineTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        mUnderlineHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
    }

    @Override
    public void setPadding(final int left, final int top, final int right, final int bottom) {
        super.setPadding(left, top, right, bottom + mUnderlineHeight);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        // Draw the underline the same color as the text
        mPaint.setColor(getTextColors().getDefaultColor());
        canvas.drawRect(0, getHeight() - mUnderlineHeight, getWidth(), getHeight(), mPaint);
    }
}
