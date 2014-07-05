package com.deange.uwaterlooapi.model.buildings;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class BuildingSection extends BaseModel {

    @SerializedName("section_name")
    private String mSectionName;

    @SerializedName("latitude")
    private float mLatitude;

    @SerializedName("longitude")
    private float mLongitude;

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
}
