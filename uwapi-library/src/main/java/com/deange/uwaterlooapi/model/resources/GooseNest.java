package com.deange.uwaterlooapi.model.resources;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GooseNest extends BaseModel {

    @SerializedName("id")
    private String mId;

    @SerializedName("location")
    private String mLocationDescription;

    @SerializedName("latitude")
    private float mLatitude;

    @SerializedName("longitude")
    private float mLongitude;

    @SerializedName("updated")
    private String mUpdated;

    /**
     * Goose Nest ID
     */
    public String getId() {
        return mId;
    }

    /**
     * Human-readable description of goose nest location
     */
    public String getLocationDescription() {
        return mLocationDescription;
    }

    /**
     * Latitude + longitude of goose nest location
     */
    public float[] getLocation() {
        return new float[] {mLatitude, mLongitude};
    }

    /**
     * ISO 8601 time-stamp of last update
     */
    public Date getUpdatedDate() {
        return Formatter.parseDate(mUpdated);
    }

    /**
     * ISO 8601 time-stamp of last update as a string
     */
    public String getRawUpdatedDate() {
        return mUpdated;
    }
}
