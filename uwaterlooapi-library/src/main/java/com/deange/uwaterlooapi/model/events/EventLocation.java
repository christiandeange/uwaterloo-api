package com.deange.uwaterlooapi.model.events;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class EventLocation extends BaseModel {

    @SerializedName("id")
    int mId;

    @SerializedName("name")
    String mName;

    @SerializedName("street")
    String mStreet;

    @SerializedName("additional")
    String mAdditional;

    @SerializedName("city")
    String mCity;

    @SerializedName("province")
    String mProvince;

    @SerializedName("postal_code")
    String mPostalCode;

    @SerializedName("country")
    String mCountry;

    @SerializedName("latitude")
    float mLatitude;

    @SerializedName("longitude")
    float mLongitude;

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
    public float[] getLocation() {
        return new float[] { mLatitude, mLongitude };
    }

}
