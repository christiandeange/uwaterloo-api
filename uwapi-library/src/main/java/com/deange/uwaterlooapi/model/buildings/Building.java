package com.deange.uwaterlooapi.model.buildings;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Building extends BaseModel {

    @SerializedName("building_id")
    private String mBuildingId;

    @SerializedName("building_code")
    private String mBuildingCode;

    @SerializedName("building_name")
    private String mBuildingName;

    @SerializedName("alternate_names")
    private List<String> mAlternateNames;

    @SerializedName("latitude")
    private float mLatitude;

    @SerializedName("longitude")
    private float mLongitude;

    @SerializedName("building_sections")
    private List<BuildingSection> mBuildingSections;

    /**
     * Official unique building number
     */
    public String getBuildingId() {
        return mBuildingId;
    }

    /**
     * Official unique building code
     */
    public String getBuildingCode() {
        return mBuildingCode;
    }

    /**
     * Official unique building name
     */
    public String getBuildingName() {
        return mBuildingName;
    }

    /**
     * Alternate building names
     */
    public List<String> getAlternateNames() {
        return CollectionUtils.applyPolicy(mAlternateNames);
    }

    /**
     * Latitude + longitude of building location
     */
    public float[] getLocation() {
        return new float[] {mLatitude, mLongitude};
    }

    /**
     * List of building sections
     */
    public List<BuildingSection> getBuildingSections() {
        return CollectionUtils.applyPolicy(mBuildingSections);
    }
}
