package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.deange.uwaterlooapi.sample.R;

public class CardView extends RelativeLayout {

    public CardView(final Context context) {
        super(context);
        init();
    }

    public CardView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());
        setBackgroundResource(R.drawable.card_view_background);
        setPadding(padding, padding, padding, padding);
    }

}
