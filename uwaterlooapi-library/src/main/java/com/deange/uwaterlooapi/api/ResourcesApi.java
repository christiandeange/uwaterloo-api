package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Responses;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ResourcesApi {

  /**
   * This method returns a list of all the sites on campus
   */
  @GET("resources/sites.json")
  Call<Responses.Sites> getSites();

  /**
   * This method returns a list of all the tutors available to help for a course for a given term
   */
  @GET("resources/tutors.json")
  Call<Responses.Tutors> getTutors();

  /**
   * This method returns a list of printers on campus
   */
  @GET("resources/printers.json")
  Call<Responses.Printers> getPrinters();

  /**
   * This method returns a list of campus employer infosessions
   */
  @GET("resources/infosessions.json")
  Call<Responses.InfoSessions> getInfoSessions();

  /**
   * This method returns a list of geese nests during their spring mating season
   */
  @GET("resources/goosewatch.json")
  Call<Responses.GooseWatch> getGeeseNests();

  /**
   * This method returns a list of all UW employees that earn more than $100k/yr
   */
  @GET("resources/sunshinelist.json")
  Call<Responses.Sunshine> getSunshineList();

}
