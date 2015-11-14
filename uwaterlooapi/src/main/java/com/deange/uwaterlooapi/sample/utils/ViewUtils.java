package com.deange.uwaterlooapi.sample.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public final class ViewUtils {

    private ViewUtils() {
        throw new AssertionError();
    }

    public static void setText(final TextView view, final CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        }
    }

}
