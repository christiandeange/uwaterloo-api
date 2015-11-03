package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;

public abstract class BaseCourseView extends FrameLayout {
    public BaseCourseView(final Context context) {
        super(context);

        init();
    }

    private void init() {
        setLayoutParams(generateDefaultLayoutParams());

        final int layoutId = getLayoutId();
        if (layoutId != 0) {
            inflate(getContext(), layoutId, this);
        }

        findViews();
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void findViews();

    public abstract void bind(final CombinedCourseInfo info);

}
