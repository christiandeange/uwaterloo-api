package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface BuildingsApi {

    /**
     * This method returns a list of official building names, codes, numbers, and their
     * lat/long coordinates.
     */
    @GET("/buildings/list.{format}")
    public Response.Buildings getBuildings();

}
