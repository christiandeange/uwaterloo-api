package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface PointsOfInterestApi {

    /**
     * This method returns list of ATMs across campus.
     */
    @GET("/poi/atms.{format}")
    public Response.ATMs getATMs();

}
