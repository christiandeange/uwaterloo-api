package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.DateRange;
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

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getBuilding() {
        return mBuilding;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }

    private float[] getLocation() {
        return new float[] { mLatitude, mLongitude };
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAnnouncements() {
        return mAnnouncements;
    }

    public boolean isOpenNow() {
        return mIsOpenNow;
    }

    public Map<String, OperatingHours> getHours() {
        return mHours;
    }

    public List<SpecialOperatingHours> getSpecialOperatingHours() {
        return mSpecialOperatingHours;
    }

    public List<String> getRawDatesClosed() {
        return mDatesClosedRaw;
    }

    public List<Date> getDatesClosed() {

        // Lazy load the parsed Date objects
        if (mDatesClosed == null) {
            mDatesClosed = new ArrayList<>();

            for (final String date : mDatesClosedRaw) {
                final Date parsedDate = Formatter.parseDate(date, DateRange.YMD);
                if (parsedDate != null) {
                    mDatesClosed.add(parsedDate);
                }
            }
        }

        return mDatesClosed;
    }
}
