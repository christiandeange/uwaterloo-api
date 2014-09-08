package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PropertyLayout extends LinearLayout {

    protected TextView mPropertyNameView;
    protected TextView mPropertyValueView;

    public PropertyLayout(final Context context) {
        super(context);
    }

    public PropertyLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public PropertyLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) return;

        String thisId;
        try {
            thisId = getResources().getResourceName(getId());
        } catch (final Resources.NotFoundException e) {
            thisId = Integer.toHexString(getId());
        }

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child instanceof TextView) {
                if (mPropertyNameView == null) {
                    mPropertyNameView = (TextView) child;

                } else if (mPropertyValueView == null) {
                    mPropertyValueView = (TextView) child;

                } else {
                    throw new IllegalStateException(
                            "More than 2 TextViews found in " + thisId + "!");
                }
            }
        }

        if (mPropertyNameView == null) {
            throw new IllegalStateException(
                    "PropertyLayout " + thisId + " must contain at least 1 TextView!");
        }
    }

    public static void resize(final ViewGroup parentView) {

        // Gather all the child PropertyLayouts
        final List<PropertyLayout> layouts = new ArrayList<>();
        for (int i = 0; i < parentView.getChildCount(); i++) {
            final View child = parentView.getChildAt(i);
            if (child instanceof PropertyLayout) {
                layouts.add((PropertyLayout) child);
            }
        }

        int maxPropertyNameWidth = 0;
        for (final PropertyLayout layout : layouts) {
            final int widthMeasureSpec = MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED);
            final int heightMeasureSpec = MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED);
            layout.mPropertyNameView.measure(widthMeasureSpec, heightMeasureSpec);

            maxPropertyNameWidth = Math.max(
                    maxPropertyNameWidth, layout.mPropertyNameView.getMeasuredWidth());
        }

        for (final PropertyLayout layout : layouts) {
            layout.mPropertyNameView.setMinWidth(maxPropertyNameWidth);
        }

    }

    public void setKeyText(final int resId) {
        mPropertyNameView.setText(resId);
    }

    public void setKeyText(final CharSequence text) {
        mPropertyNameView.setText(text);
    }

    public void setValueText(final int resId) {
        mPropertyValueView.setText(resId);
    }

    public void setValueText(final CharSequence text) {
        mPropertyValueView.setText(text);
    }
}
