package com.deange.uwaterlooapi.model.resources;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Site extends BaseModel {

    @SerializedName("name")
    String mName;

    @SerializedName("slug")
    String mSlug;

    @SerializedName("url")
    String mUrl;

    @SerializedName("group_code")
    String mGroupCode;

    @SerializedName("unit_code")
    String mUnitCode;

    @SerializedName("unit_short_name")
    String mUnitShortName;

    @SerializedName("owner_type")
    String mOwnerType;

    /**
     * The name of the site
     */
    public String getName() {
        return mName;
    }

    /**
     * A url-safe identifier for this site
     */
    public String getSlug() {
        return mSlug;
    }

    /**
     * A link to the site
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * The faculty code (if any) associated with the site
     */
    public String getGroupCode() {
        return mGroupCode;
    }

    /**
     * The department code (if any) associated with the site
     */
    public String getUnitCode() {
        return mUnitCode;
    }

    /**
     * The user-friendly name of the department associated with the site
     */
    public String getUnitShortName() {
        return mUnitShortName;
    }

    /**
     * Identifier for the site owner
     */
    public String getOwnerType() {
        return mOwnerType;
    }
}
