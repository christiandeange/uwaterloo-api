package com.deange.uwaterlooapi.model.parking;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class ParkingLot extends BaseModel {

    @SerializedName("lot_name")
    String mLotName;

    @SerializedName("latitude")
    float mLatitude;

    @SerializedName("longitude")
    float mLongitude;

    @SerializedName("capacity")
    int mCapacity;

    @SerializedName("current_count")
    int mCurrentCount;

    @SerializedName("percent_filled")
    int mPercentFilled;

    @SerializedName("last_updated")
    String mLastUpdated;

    /**
     * Name of the parking lot
     */
    public String getLotName() {
        return mLotName;
    }

    /**
     * Location [latitude, longitude] coordinates
     */
    public float[] getLocation() {
        return new float[] { mLatitude, mLongitude };
    }

    /**
     * Capacity of the parking lot
     */
    public int getCapacity() {
        return mCapacity;
    }

    /**
     * Current count of the number of cars in the parking lot
     */
    public int getCurrentCount() {
        return mCurrentCount;
    }

    /**
     * Percentage of which the parking lot is filled, rounded to the nearest integer
     */
    public int getPercentFilledRounded() {
        return mPercentFilled;
    }

    /**
     * Percentage of which the parking lot is filled
     */
    public float getPercentFilled() {
        return (float) mCurrentCount / (float) mCapacity;
    }

    /**
     * Time which the `current_count` was last updated
     */
    public Date getLastUpdated() {
        return Formatter.parseDate(mLastUpdated);
    }

    /**
     * Time which the `current_count` was last updated as a string
     */
    public String getLastUpdatedRaw() {
        return mLastUpdated;
    }
}
