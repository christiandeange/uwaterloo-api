package com.deange.uwaterlooapi.model.buildings;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class BuildingSection extends BaseModel {

    @SerializedName("section_name")
    String mSectionName;

    @SerializedName("latitude")
    float mLatitude;

    @SerializedName("longitude")
    float mLongitude;

    /**
     * Name of section
     */
    public String getSectionName() {
        return mSectionName;
    }

    /**
     * Latitude + longitude of building section location
     */
    public float[] getLocation() {
        return new float[] {mLatitude, mLongitude};
    }

    @Override
    public String toString() {
        return mSectionName;
    }
}
