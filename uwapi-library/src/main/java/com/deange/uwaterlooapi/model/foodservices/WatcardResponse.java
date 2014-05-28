package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WatcardResponse extends BaseResponse {

    @SerializedName("data")
    private List<WatcardVendor> mVendors;

    public List<WatcardVendor> getVendors() {
        return mVendors;
    }
}
