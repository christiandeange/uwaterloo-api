package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OutletResponse extends BaseResponse {

    @SerializedName("data")
    private List<Outlet> mOutlets;

    public List<Outlet> getOutlets() {
        return mOutlets;
    }

}
