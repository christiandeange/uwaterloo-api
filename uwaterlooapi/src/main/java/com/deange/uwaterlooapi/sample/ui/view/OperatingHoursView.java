package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.model.foodservices.OperatingHours;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.FontUtils;

import java.util.Calendar;
import java.util.Map;

public class OperatingHoursView extends FrameLayout {

    private static final String[] WEEK_MAP = new String[] {
            "",
            OperatingHours.SUNDAY,
            OperatingHours.MONDAY,
            OperatingHours.TUESDAY,
            OperatingHours.WEDNESDAY,
            OperatingHours.THURSDAY,
            OperatingHours.FRIDAY,
            OperatingHours.SATURDAY,
    };

    private Map<String, OperatingHours> mHours;
    private boolean mInflated = false;

    public OperatingHoursView(final Context context) {
        this(context, null);
    }

    public OperatingHoursView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OperatingHoursView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_operating_hours, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInflated = true;
        refresh();
    }

    public void setHours(final Map<String, OperatingHours> hours) {
        mHours = hours;
        refresh();
    }

    private void refresh() {
        if (!mInflated || mHours == null) {
            return;
        }

        final ViewGroup parentLayout = (ViewGroup) getChildAt(0);
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            final ViewGroup parent = (ViewGroup) parentLayout.getChildAt(i);
            final TextView labelView = (TextView) parent.findViewById(R.id.view_location_hours_day);
            final TextView hoursView = (TextView) parent.findViewById(R.id.view_location_hours_hours);

            final String dayOfWeek = labelView.getText().toString().toLowerCase();
            final OperatingHours hours = mHours.get(dayOfWeek);
            if (hours.isClosedAllDay()) {
                hoursView.setText(R.string.foodservices_location_hours_closed);
            } else {
                final String start = Location.convert24To12(hours.getOpeningHour());
                final String end = Location.convert24To12(hours.getClosingHour());
                hoursView.setText(start + " – " + end);
            }
        }
    }

    public void setTodayBold() {
        if (!mInflated || mHours == null) {
            return;
        }

        final ViewGroup parentLayout = (ViewGroup) getChildAt(0);
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            final ViewGroup parent = (ViewGroup) parentLayout.getChildAt(i);
            final TextView labelView = (TextView) parent.findViewById(R.id.view_location_hours_day);
            final TextView hoursView = (TextView) parent.findViewById(R.id.view_location_hours_hours);

            final String dayOfWeek = labelView.getText().toString().toLowerCase();
            final int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

            final Typeface bold = FontUtils.getFont(FontUtils.MEDIUM);
            final Typeface normal = FontUtils.getFont(FontUtils.BOOK);

            if (WEEK_MAP[day].equals(dayOfWeek)) {
                labelView.setTypeface(bold, Typeface.BOLD);
                hoursView.setTypeface(bold, Typeface.BOLD);
            } else {
                labelView.setTypeface(normal, Typeface.NORMAL);
                hoursView.setTypeface(normal, Typeface.NORMAL);
            }
        }
    }

    public void unbold() {
        if (!mInflated || mHours == null) {
            return;
        }

        final ViewGroup parentLayout = (ViewGroup) getChildAt(0);
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            final ViewGroup parent = (ViewGroup) parentLayout.getChildAt(i);
            final TextView labelView = (TextView) parent.findViewById(R.id.view_location_hours_day);
            final TextView hoursView = (TextView) parent.findViewById(R.id.view_location_hours_hours);

            final Typeface normal = FontUtils.getFont(FontUtils.DEFAULT);
            labelView.setTypeface(normal, Typeface.NORMAL);
            hoursView.setTypeface(normal, Typeface.NORMAL);
        }
    }

}
