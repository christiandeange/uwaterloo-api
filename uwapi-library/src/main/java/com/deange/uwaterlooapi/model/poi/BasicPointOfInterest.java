package com.deange.uwaterlooapi.model.poi;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class BasicPointOfInterest extends BaseModel {

    @SerializedName("name")
    String mName;

    @SerializedName("description")
    String mDescription;

    @SerializedName("note")
    String mNote;

    @SerializedName("latitude")
    float mLatitude;

    @SerializedName("longitude")
    float mLongitude;

    /**
     * Name of the location
     */
    public String getName() {
        return mName;
    }

    /**
     * Location description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Any additional notes
     */
    public String getNote() {
        return mNote;
    }

    /**
     * Location [latitude, longitude] coordinates of the location
     */
    public float[] getLocation() {
        return new float[] { mLatitude, mLongitude };
    }

}
