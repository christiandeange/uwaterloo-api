package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationsResponse extends BaseResponse {

    @SerializedName("data")
    private List<Location> mLocations;

    public List<Location> getLocations() {
        return mLocations;
    }

}
