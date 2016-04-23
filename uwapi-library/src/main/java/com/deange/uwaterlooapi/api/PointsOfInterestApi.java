package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface PointsOfInterestApi {

    /**
     * This method returns list of ATMs across campus.
     */
    @GET("/poi/atms.{format}")
    Response.ATMs getATMs();

    /**
     * This method returns list of Greyhound bus stops across the city.
     */
    @GET("/poi/greyhound.{format}")
    Response.Greyhound getGreyhoundStops();

    /**
     * This method returns list of photospheres across campus.
     */
    @GET("/poi/photospheres.{format}")
    Response.Photospheres getPhotospheres();

    /**
     * This method returns list of emergency helplines across campus.
     */
    @GET("/poi/helplines.{format}")
    Response.Helplines getHelplines();

    /**
     * This method returns list of libraries across the city.
     */
    @GET("/poi/libraries.{format}")
    Response.Libraries getLibraries();

}
