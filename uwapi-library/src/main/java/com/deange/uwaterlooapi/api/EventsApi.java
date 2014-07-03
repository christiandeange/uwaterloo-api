package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface EventsApi {

    /**
     * This method returns a list of the upcoming 21 University of Waterloo events as crawled from
     * all University WCMS sites listed at https://api.uwaterloo.ca/v2/resources/sites.json
     */
    @GET("/events.{format}")
    public Response.Events getEvents();

}
