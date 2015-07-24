package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.deange.uwaterlooapi.sample.R;

public class CardView extends android.support.v7.widget.CardView {

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
        final float dip = getResources().getDisplayMetrics().density;

        if (getCardElevation() == 0) {
            setCardElevation(4 * dip);
        }

        if (getRadius() == 0) {
            setRadius(2 * dip);
        }
    }

}
