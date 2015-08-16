package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class WrapContentListView extends ListView {
    public WrapContentListView(final Context context) {
        super(context);
    }

    public WrapContentListView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentListView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {

        int totalHeight = 0;

        final ListAdapter adapter = getAdapter();
        final int dividersHeight = (getDividerHeight() * (adapter.getCount() - 1));

        for (int i = 0; i < adapter.getCount(); ++i) {
            final View listItem = adapter.getView(i, null, this);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        totalHeight += dividersHeight;

        final int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                totalHeight, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }
}
