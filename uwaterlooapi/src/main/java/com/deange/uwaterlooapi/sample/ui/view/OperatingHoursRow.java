package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OperatingHoursRow
        extends RelativeLayout {

    @BindView(R.id.view_location_hours_day) TextView mDayView;
    @BindView(R.id.view_location_hours_hours) TextView mHoursView;

    public OperatingHoursRow(final Context context) {
        this(context, null);
    }

    public OperatingHoursRow(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OperatingHoursRow(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.OperatingHoursRow);
    }

    public OperatingHoursRow(
            final Context context,
            final AttributeSet attrs,
            final int defStyleAttr,
            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // Get the text for the row (usually a day of the week
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.OperatingHoursRow, defStyleAttr, defStyleRes);
        final String rowText = a.getString(R.styleable.OperatingHoursRow_rowText);
        a.recycle();

        // Use the row name in the layout
        inflate(context, R.layout.view_operating_hours_row, this);

        ButterKnife.bind(this);

        mDayView.setText(rowText);
    }

    public void setDay(final String day) {
        mDayView.setText(day);
    }

    public void setHours(final String hours) {
        mHoursView.setText(hours);
    }

    public TextView getDayView() {
        return mDayView;
    }

    public TextView getHoursView() {
        return mHoursView;
    }
}
