package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Responses;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PointsOfInterestApi {

  /**
   * This method returns list of ATMs across campus.
   */
  @GET("poi/atms.json")
  Call<Responses.ATMs> getATMs();

  /**
   * This method returns list of Greyhound bus stops across the city.
   */
  @GET("poi/greyhound.json")
  Call<Responses.Greyhound> getGreyhoundStops();

  /**
   * This method returns list of photospheres across campus.
   */
  @GET("poi/photospheres.json")
  Call<Responses.Photospheres> getPhotospheres();

  /**
   * This method returns list of emergency helplines across campus.
   */
  @GET("poi/helplines.json")
  Call<Responses.Helplines> getHelplines();

  /**
   * This method returns list of libraries across the city.
   */
  @GET("poi/libraries.json")
  Call<Responses.Libraries> getLibraries();

  /**
   * This method returns list of defibrillators across campus.
   */
  @GET("poi/defibrillators.json")
  Call<Responses.Defibrillators> getDefibrillators();

}
