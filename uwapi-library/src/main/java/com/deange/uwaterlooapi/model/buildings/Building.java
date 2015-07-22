package com.deange.uwaterlooapi.model.buildings;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Building extends BaseModel {

    @SerializedName("building_id")
    String mBuildingId;

    @SerializedName("building_code")
    String mBuildingCode;

    @SerializedName("building_name")
    String mBuildingName;

    @SerializedName("alternate_names")
    List<String> mAlternateNames;

    @SerializedName("latitude")
    float mLatitude;

    @SerializedName("longitude")
    float mLongitude;

    @SerializedName("building_sections")
    List<BuildingSection> mBuildingSections;

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
