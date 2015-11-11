package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface ResourcesApi {

    /**
     * This method returns a list of all the sites on campus
     */
    @GET("/resources/sites.{format}")
    public Response.Sites getSites();

    /**
     * This method returns a list of all the tutors available to help for a course for a given term
     */
    @GET("/resources/tutors.{format}")
    public Response.Tutors getTutors();

    /**
     * This method returns a list of printers on campus
     */
    @GET("/resources/printers.{format}")
    public Response.Printers getPrinters();

    /**
     * This method returns a list of campus employer infosessions
     */
    @GET("/resources/infosessions.{format}")
    public Response.InfoSessions getInfoSessions();

    /**
     * This method returns a list of geese nests during their spring mating season
     */
    @GET("/resources/goosewatch.{format}")
    public Response.GooseWatch getGeeseNests();

}
