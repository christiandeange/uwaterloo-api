package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ParkingApi {

    /**
     * This method returns real-time parking counts in select parking lots across campus.
     */
    @GET("parking/watpark.json")
    Call<Response.Parking> getParkingInfo();

}
