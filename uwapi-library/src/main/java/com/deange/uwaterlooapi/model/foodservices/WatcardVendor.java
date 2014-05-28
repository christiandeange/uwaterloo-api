package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class WatcardVendor extends BaseModel {

    @SerializedName("vendor_id")
    private int mId;

    @SerializedName("vendor_name")
    private String mName;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

}
