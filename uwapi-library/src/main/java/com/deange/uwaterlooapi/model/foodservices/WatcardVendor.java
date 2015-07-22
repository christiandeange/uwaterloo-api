package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class WatcardVendor extends BaseModel {

    @SerializedName("vendor_id")
    int mId;

    @SerializedName("vendor_name")
    String mName;

    /**
     * Outlet ID number
     */
    public int getId() {
        return mId;
    }

    /**
     * Vendor name
     */
    public String getName() {
        return mName;
    }

}
