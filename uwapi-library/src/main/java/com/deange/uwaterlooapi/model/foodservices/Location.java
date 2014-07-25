package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Location extends BaseModel {

    @SerializedName("outlet_id")
    private int mId;

    @SerializedName("outlet_name")
    private String mName;

    @SerializedName("building")
    private String mBuilding;

    @SerializedName("logo")
    private String mLogoUrl;

    @SerializedName("latitude")
    private float mLatitude;

    @SerializedName("longitude")
    private float mLongitude;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("notice")
    private String mAnnouncements;

    @SerializedName("is_open_now")
    private boolean mIsOpenNow;

    @SerializedName("opening_hours")
    private Map<String, OperatingHours> mHours;

    @SerializedName("special_hours")
    private List<SpecialOperatingHours> mSpecialOperatingHours;

    @SerializedName("dates_closed")
    private List<String> mDatesClosedRaw;

    private List<Date> mDatesClosed;

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
    private float[] getLocation() {
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
    public List<SpecialOperatingHours> getSpecialOperatingHours() {
        return CollectionUtils.applyPolicy(mSpecialOperatingHours);
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
    public List<Date> getDatesClosed() {

        // Lazy load the parsed Date objects
        if (mDatesClosed == null) {
            mDatesClosed = new ArrayList<>();

            for (final String date : mDatesClosedRaw) {
                final Date parsedDate = Formatter.parseDate(date, Formatter.YMD);
                if (parsedDate != null) {
                    mDatesClosed.add(parsedDate);
                }
            }
        }

        return CollectionUtils.applyPolicy(mDatesClosed);
    }
}
