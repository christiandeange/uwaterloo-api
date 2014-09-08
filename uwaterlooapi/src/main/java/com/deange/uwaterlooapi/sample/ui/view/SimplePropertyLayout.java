package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;

public class SimplePropertyLayout extends PropertyLayout {

    public SimplePropertyLayout(final Context context) {
        super(context);
    }

    public SimplePropertyLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimplePropertyLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.view_simple_property, this);

        // Property name
        if (mPropertyNameView != null) {
            mPropertyNameView.setText(String.valueOf(getTag()));
        }

        // Property value
        if (mPropertyValueView != null) {
            mPropertyValueView.setId(getId());
        }

        setId(View.NO_ID);
        setTag(null);
    }
}
