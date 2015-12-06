package com.deange.uwaterlooapi.model.foodservices;

import android.text.TextUtils;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Parcel
public class Location extends BaseModel {

    @SerializedName("outlet_id")
    int mId;

    @SerializedName("outlet_name")
    String mName;

    @SerializedName("building")
    String mBuilding;

    @SerializedName("logo")
    String mLogoUrl;

    @SerializedName("latitude")
    float mLatitude;

    @SerializedName("longitude")
    float mLongitude;

    @SerializedName("description")
    String mDescription;

    @SerializedName("notice")
    String mAnnouncements;

    @SerializedName("is_open_now")
    boolean mIsOpenNow;

    @SerializedName("opening_hours")
    Map<String, OperatingHours> mHours;

    @SerializedName("special_hours")
    List<SpecialOperatingHours> mSpecialOperatingHours;

    @SerializedName("dates_closed")
    List<String> mDatesClosedRaw;

    List<Range> mDatesClosed;

    List<SpecialRange> mDatesSpecial;

    /**
     * Outlet ID number (not always same as outets.json method). Can be null
     */
    public int getId() {
        return mId;
    }

    /**
     * Outlet name
     */
    public String getName() {
        return mName;
    }

    /**
     * Name of the building the outlet is located
     */
    public String getBuilding() {
        return mBuilding;
    }

    /**
     * URL of the ouetlet logo (size varies)
     */
    public String getLogoUrl() {
        return mLogoUrl;
    }

    /**
     * Location [latitude, longitude] coordinates
     */
    public float[] getLocation() {
        return new float[] { mLatitude, mLongitude };
    }

    /**
     * Location blurb
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Outlet specific anouncements
     */
    public String getAnnouncements() {
        return mAnnouncements;
    }

    /**
     * Predicts if the location is currently open by taking the current time into account
     */
    public boolean isOpenNow() {
        return mIsOpenNow;
    }

    /**
     * Weekly operating hours data
     * </p >
     * @param dayOfWeek is one of the following:
     *
     * {@link OperatingHours#SUNDAY SUNDAY},
     * {@link OperatingHours#MONDAY MONDAY},
     * {@link OperatingHours#TUESDAY TUESDAY},
     * {@link OperatingHours#WEDNESDAY WEDNESDAY},
     * {@link OperatingHours#THURSDAY THURSDAY},
     * {@link OperatingHours#FRIDAY FRIDAY},
     * {@link OperatingHours#SATURDAY SATURDAY}.
     *
     */
    public OperatingHours getHours(final String dayOfWeek) {
        return mHours.get(dayOfWeek);
    }

    /**
     * Weekly operating hours data
     */
    public Map<String, OperatingHours> getHours() {
        return CollectionUtils.applyPolicy(mHours);
    }

    /**
     * Special cases for operating hours
     */
    public List<SpecialOperatingHours> getSpecialOperatingHoursRaw() {
        return CollectionUtils.applyPolicy(mSpecialOperatingHours);
    }

    /**
     * Special cases for operating hours
     */
    public List<SpecialRange> getSpecialOperatingHours() {

        // Lazy load the parsed Date objects
        if (mDatesSpecial == null) {
            mDatesSpecial = new ArrayList<>();

            boolean closedRange = true;
            boolean first = true;

            String open = null;
            String close = null;

            final Calendar start = Calendar.getInstance();
            final Calendar curr = Calendar.getInstance();
            final Calendar temp = Calendar.getInstance();
            final Calendar end = Calendar.getInstance();

            for (final SpecialOperatingHours special : mSpecialOperatingHours) {
                final Date date = special.getDate();

                if (first) {
                    first = false;
                    start.setTimeInMillis(date.getTime());
                    end.setTimeInMillis(date.getTime());

                    open = special.getOpeningHour();
                    close = special.getClosingHour();
                    closedRange = false;

                    continue;
                } else {
                    curr.setTime(date);
                    temp.set(end.get(Calendar.YEAR),
                            end.get(Calendar.MONTH),
                            end.get(Calendar.DATE) + 1,
                            0, 0, 0);
                    curr.set(Calendar.MILLISECOND, 0);
                    temp.set(Calendar.MILLISECOND, 0);
                }

                if (temp.getTimeInMillis() == curr.getTimeInMillis()
                        && TextUtils.equals(open, special.getOpeningHour())
                        && TextUtils.equals(close, special.getClosingHour())) {
                    // This is one day after the current end time
                    end.setTimeInMillis(curr.getTimeInMillis());
                    closedRange = false;

                } else {
                    // Start of a new range!
                    mDatesSpecial.add(new SpecialRange(start.getTime(), end.getTime(), open, close));
                    start.setTimeInMillis(curr.getTimeInMillis());
                    end.setTimeInMillis(curr.getTimeInMillis());

                    open = special.getOpeningHour();
                    close = special.getClosingHour();
                    closedRange = true;
                }
            }

            if (!closedRange) {
                mDatesSpecial.add(new SpecialRange(start.getTime(), end.getTime(), open, close));
            }
        }

        return CollectionUtils.applyPolicy(mDatesSpecial);
    }

    /**
     * Y-m-d format list of dates the outlet is closed as strings
     */
    public List<String> getRawDatesClosed() {
        return CollectionUtils.applyPolicy(mDatesClosedRaw);
    }

    /**
     * Y-m-d format list of dates the outlet is closed
     */
    public List<Range> getDatesClosed() {

        // Lazy load the parsed Date objects
        if (mDatesClosed == null) {
            mDatesClosed = new ArrayList<>();

            boolean closedRange = true;
            boolean first = true;

            final Calendar start = Calendar.getInstance();
            final Calendar curr = Calendar.getInstance();
            final Calendar temp = Calendar.getInstance();
            final Calendar end = Calendar.getInstance();

            for (final String dateStr : mDatesClosedRaw) {
                final Date date = Formatter.parseDate(dateStr, Formatter.YMD);

                if (first) {
                    first = false;
                    start.setTimeInMillis(date.getTime());
                    end.setTimeInMillis(date.getTime());
                    closedRange = false;
                    continue;
                } else {
                    curr.setTime(date);
                    temp.set(end.get(Calendar.YEAR),
                            end.get(Calendar.MONTH),
                            end.get(Calendar.DATE) + 1,
                            0, 0, 0);
                    curr.set(Calendar.MILLISECOND, 0);
                    temp.set(Calendar.MILLISECOND, 0);
                }

                if (temp.getTimeInMillis() == curr.getTimeInMillis()) {
                    // This is one day after the current end time
                    end.setTimeInMillis(curr.getTimeInMillis());
                    closedRange = false;

                } else {
                    // Start of a new range!
                    mDatesClosed.add(new Range(start.getTime(), end.getTime()));
                    start.setTimeInMillis(curr.getTimeInMillis());
                    end.setTimeInMillis(curr.getTimeInMillis());
                    closedRange = true;
                }
            }

            if (!closedRange) {
                mDatesClosed.add(new Range(start.getTime(), end.getTime()));
            }
        }

        return CollectionUtils.applyPolicy(mDatesClosed);
    }

    public static String sanitize(String time) {
        if (TextUtils.isEmpty(time)) {
            return null;
        }

        if (time.startsWith("0")) {
            time = time.substring(1);
        }

        time = time.replace(":00", "");
        return time;
    }

    public static String convert24To12(final String time) {
        if (TextUtils.isEmpty(time)) {
            return null;
        }

        final String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        String ampm = "AM";

        if (hour == 0) {
            hour = 12;
        } else if (hour == 12) {
            ampm = "PM";
        } else if (hour > 12) {
            hour -= 12;
            ampm = "PM";
        }

        return hour + ":" + parts[1] + " " + ampm;
    }

    public static String convert12To24(final String time) {
        if (TextUtils.isEmpty(time)) {
            return null;
        }

        final String[] components = time.split(" ");
        final String[] parts = components[0].split(":");

        final boolean isPM = "PM".equalsIgnoreCase(components[1]);
        int hour = Integer.parseInt(parts[0]);

        if (hour == 12 && !isPM) {
            hour = 0;
        } else if (isPM) {
            hour += 12;
        }

        return hour + ":" + parts[1];
    }

    @Parcel
    public static class Range {

        private static final String DATE_FORMAT = "MMM d";
        final Date first;
        final Date second;

        @ParcelConstructor
        Range(final Date first, final Date second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            if (first.equals(second)) {
                return Formatter.formatDate(first, DATE_FORMAT);
            } else {
                return Formatter.formatDate(first, DATE_FORMAT)
                        + " – " + Formatter.formatDate(second, DATE_FORMAT);
            }
        }
    }

    @Parcel
    public static class SpecialRange extends Range {

        final String open;
        final String close;

        @ParcelConstructor
        SpecialRange(final Date first, final Date second,
                     final String open, final String close) {
            super(first, second);
            this.open = open;
            this.close = close;
        }

        @Override
        public String toString() {
            return super.toString() + " ("
                    + convert24To12(open) + " – "
                    + convert24To12(close) + ")";
        }
    }

}
