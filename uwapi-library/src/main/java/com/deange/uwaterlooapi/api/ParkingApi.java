package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface ParkingApi {

    /**
     * This method returns real-time parking counts in select parking lots across campus.
     */
    @GET("/parking/watpark.json")
    Response.Parking getParkingInfo();

}
