package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;

public class ModuleListItem extends FrameLayout {
    public ModuleListItem(final Context context) {
        super(context);
    }

    public ModuleListItem(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ModuleListItem(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        inflate(getContext(), R.layout.simple_module_item, this);

        final TextView title = (TextView) findViewById(android.R.id.text1);
        if (title != null) {
            title.setText(getContentDescription());
        }
    }
}
