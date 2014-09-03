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

        ((TextView) getChildAt(0)).setText(String.valueOf(getTag()));   // Property name
        getChildAt(1).setId(getId());                                   // Property value

        setId(View.NO_ID);
        setTag(null);
    }
}
