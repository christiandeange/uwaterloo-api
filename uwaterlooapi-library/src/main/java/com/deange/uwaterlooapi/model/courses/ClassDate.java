package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Parcel
public class ClassDate extends BaseModel {

    @SerializedName("start_time")
    String mStartTime;

    @SerializedName("end_time")
    String mEndTime;

    @SerializedName("weekdays")
    String mWeekdays;

    @SerializedName("start_date")
    String mStartDate;

    @SerializedName("end_date")
    String mEndDate;

    @SerializedName("is_tba")
    boolean mIsTBA;

    @SerializedName("is_cancelled")
    boolean mIsCancelled;

    @SerializedName("is_closed")
    boolean mIsClosed;

    /**
     * 24 hour class starting time
     */
    public String getStartTime() {
        return mStartTime;
    }

    /**
     * 24 hour class ending time
     */
    public String getEndTime() {
        return mEndTime;
    }

    /**
     * Weekdays the course is offered
     */
    public String getWeekdays() {
        return mWeekdays;
    }

    /**
     * Additional starting date for course
     */
    public String getStartDate() {
        return mStartDate;
    }

    /**
     * Additional ending date for course
     */
    public String getEndDate() {
        return mEndDate;
    }

    /**
     * If the course schedule is TBA
     */
    public boolean isTBA() {
        return mIsTBA;
    }

    /**
     * If the course is cancelled for the term
     */
    public boolean isCancelled() {
        return mIsCancelled;
    }

    /**
     * If the course is closed for the term
     */
    public boolean isClosed() {
        return mIsClosed;
    }

    /**
     * Parses {@link #mWeekdays weekdays} into a list of Calendar.java weekday constants
     * eg: Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.FRIDAY, ...
     */
    public List<Integer> getDaysOfWeek() {
        final List<Integer> daysOfWeek = new ArrayList<>();

        for (int i = 0; i < mWeekdays.length(); ++i) {
            String day = String.valueOf(mWeekdays.charAt(i));
            if (i < mWeekdays.length() - 1 && Character.isLowerCase(mWeekdays.charAt(i + 1))) {
                day += mWeekdays.charAt(i + 1);
                i += 1;
            }

            final int dayOfWeek = convertToCalendarDay(day);
            if (dayOfWeek != Integer.MIN_VALUE) {
                daysOfWeek.add(dayOfWeek);
            }
        }

        return daysOfWeek;
    }

    private int convertToCalendarDay(final String dayOfWeek) {
        switch (dayOfWeek) {
            case "M":  return Calendar.MONDAY;
            case "T":  return Calendar.TUESDAY;
            case "W":  return Calendar.WEDNESDAY;
            case "Th": return Calendar.THURSDAY;
            case "F":  return Calendar.FRIDAY;
            case "Sa": return Calendar.SATURDAY;
            case "Su": return Calendar.SUNDAY;
            default: return Integer.MIN_VALUE;
        }
    }
}
