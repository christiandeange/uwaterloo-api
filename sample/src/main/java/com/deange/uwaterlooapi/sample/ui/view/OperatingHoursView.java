package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.model.foodservices.OperatingHours;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.FontUtils;

import java.util.Calendar;
import java.util.Map;

public class OperatingHoursView
        extends LinearLayout {

    public static final int MODE_DAYS_OF_WEEK = 1;
    public static final int MODE_MANUAL = 2;

    private static final String[] WEEK_MAP = new String[]{
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
    @Mode private int mMode;

    public OperatingHoursView(final Context context) {
        this(context, null);
    }

    public OperatingHoursView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OperatingHoursView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInflated = true;
        refresh();
    }

    public void setMode(@Mode final int mode) {
        if (mMode != mode) {
            mMode = mode;

            removeAllViews();
            if (mode == MODE_DAYS_OF_WEEK) {
                inflate(getContext(), R.layout.view_operating_hours, this);
            }

            refresh();
        }
    }

    public void setHours(final Map<String, OperatingHours> hours) {
        mHours = hours;

        if (mMode == MODE_MANUAL) {
            removeAllViews();

            if (mHours != null) {
                for (final Map.Entry<String, OperatingHours> entry : mHours.entrySet()) {
                    final OperatingHoursRow row = new OperatingHoursRow(getContext());
                    row.setDay(entry.getKey());
                    row.setHours(formatHours(entry.getValue()));
                    addView(row);
                }
            }
        }

        refresh();
    }

    private String formatHours(final OperatingHours hours) {
        final String start = Location.convert24To12(hours.getOpeningHour());
        final String end = Location.convert24To12(hours.getClosingHour());
        return start + " – " + end;
    }

    private void refresh() {
        if (!mInflated || mHours == null) {
            return;
        }

        for (int i = 0; i < getChildCount(); i++) {
            final OperatingHoursRow parent = (OperatingHoursRow) getChildAt(i);

            final String dayOfWeek = parent.getDayView().getText().toString();
            OperatingHours hours = mHours.get(dayOfWeek);
            if (hours == null) {
                hours = mHours.get(dayOfWeek.toLowerCase());
            }

            final TextView hoursView = parent.getHoursView();
            if (hours.isClosedAllDay()) {
                hoursView.setText(R.string.foodservices_location_hours_closed);

            } else {
                hoursView.setText(formatHours(hours));
            }
        }
    }

    public void setTodayBold() {
        final int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        bold(WEEK_MAP[day]);
    }

    public void bold(final String key) {
        if (!mInflated || mHours == null) {
            return;
        }

        final Typeface bold = FontUtils.getFont(FontUtils.MEDIUM);
        final Typeface normal = FontUtils.getFont(FontUtils.BOOK);

        for (int i = 0; i < getChildCount(); i++) {
            final OperatingHoursRow parent = (OperatingHoursRow) getChildAt(i);
            final TextView labelView = parent.getDayView();
            final TextView hoursView = parent.getHoursView();

            if (labelView.getText().toString().equalsIgnoreCase(key)) {
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

        final Typeface normal = FontUtils.getFont(FontUtils.DEFAULT);

        for (int i = 0; i < getChildCount(); i++) {
            final OperatingHoursRow parent = (OperatingHoursRow) getChildAt(i);
            parent.getDayView().setTypeface(normal, Typeface.NORMAL);
            parent.getHoursView().setTypeface(normal, Typeface.NORMAL);
        }
    }

    @IntDef({ MODE_DAYS_OF_WEEK, MODE_MANUAL })
    public @interface Mode {
    }

}
