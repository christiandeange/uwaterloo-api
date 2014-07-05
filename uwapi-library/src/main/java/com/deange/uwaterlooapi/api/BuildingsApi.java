package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;
import retrofit.http.Path;

public interface BuildingsApi {

    /**
     * This method returns a list of official building names, codes, numbers, and their
     * lat/long coordinates.
     */
    @GET("/buildings/list.{format}")
    public Response.Buildings getBuildings();

    /**
     * This method returns the official building name, its unique number, and its
     * lat/long coordinates given a building code.
     * @param buildingCode Building code, eg: CPH, SLC, DC
     */
    @GET("/buildings/{building_code}.{format}")
    public Response.BuildingEntity getBuilding(@Path("building_code") String buildingCode);

}
