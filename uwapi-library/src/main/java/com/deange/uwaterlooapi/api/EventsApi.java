package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;
import retrofit.http.Path;

public interface EventsApi {

    /**
     * This method returns a list of the upcoming University of Waterloo events as crawled from
     * all University WCMS sites listed at https://api.uwaterloo.ca/v2/resources/sites.json
     */
    @GET("/events.{format}")
    public Response.Events getEvents();

    /**
     * This method returns a list of the upcoming site events given a site slug
     * @param site Valid site slug from /resources/sites
     */
    @GET("/events/{site}.{format}")
    public Response.Events getEvents(@Path("site") String site);

}
