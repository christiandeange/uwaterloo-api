package com.deange.uwaterlooapi.model.events;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class EventLocation extends BaseModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("street")
    private String mStreet;

    @SerializedName("additional")
    private String mAdditional;

    @SerializedName("city")
    private String mCity;

    @SerializedName("province")
    private String mProvince;

    @SerializedName("postal_code")
    private String mPostalCode;

    @SerializedName("country")
    private String mCountry;

    @SerializedName("latitude")
    private float mLatitude;

    @SerializedName("longitude")
    private float mLongitude;

    /**
     * Unique id of location
     */
    public int getId() {
        return mId;
    }

    /**
     * Name of location
     */
    public String getName() {
        return mName;
    }

    /**
     * Street address of location
     */
    public String getStreet() {
        return mStreet;
    }

    /**
     * Additional information regarding street address of location
     */
    public String getAdditional() {
        return mAdditional;
    }

    /**
     * Name of city
     */
    public String getCity() {
        return mCity;
    }

    /**
     * Name of province in two-letter short form
     */
    public String getProvince() {
        return mProvince;
    }

    /**
     * Postal code "in L#L #L#" format
     */
    public String getPostalCode() {
        return mPostalCode;
    }

    /**
     * Full name of country
     */
    public String getCountry() {
        return mCountry;
    }

    /**
     * Event location latitude + longitude
     */
    private float[] getLocation() {
        return new float[] { mLatitude, mLongitude };
    }

}
