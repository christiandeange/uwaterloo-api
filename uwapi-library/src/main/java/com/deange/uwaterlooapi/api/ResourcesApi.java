package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface ResourcesApi {

    /**
     * This method returns a list of all the tutors available to help for a course for a given term
     */
    @GET("/resources/tutors.{format}")
    public Response.Tutors getTutors();

}
