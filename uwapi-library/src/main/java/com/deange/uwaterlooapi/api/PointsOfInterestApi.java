package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface PointsOfInterestApi {

    /**
     * This method returns list of ATMs across campus.
     */
    @GET("/poi/atms.{format}")
    public Response.ATMs getATMs();

    /**
     * This method returns list of Greyhound bus stops across city.
     */
    @GET("/poi/greyhound.{format}")
    public Response.Greyhound getGreyhoundStops();

    /**
     * This method returns list of photospheres across campus.
     */
    @GET("/poi/photospheres.{format}")
    public Response.Photospheres getPhotospheres();

    /**
     * This method returns list of emergency helplines across campus.
     */
    @GET("/poi/helplines.{format}")
    public Response.Helplines getHelplines();

}
