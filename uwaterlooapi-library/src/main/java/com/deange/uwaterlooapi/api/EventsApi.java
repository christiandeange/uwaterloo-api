package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Responses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventsApi {

  /**
   * This method returns a list of the upcoming University of Waterloo events as crawled from
   * all University WCMS sites listed at https://api.uwaterloo.ca/v2/resources/sites.json
   */
  @GET("events.json")
  Call<Responses.Events> getEvents();

  /**
   * This method returns a list of the upcoming site events given a site slug
   *
   * @param site Valid site slug from /resources/sites
   */
  @GET("events/{site}.json")
  Call<Responses.Events> getEvents(@Path("site") String site);

  /**
   * This method returns a specific event's information given a site slug and the unique id
   *
   * @param site Valid site slug from /resources/sites
   * @param id   Valid event id
   */
  @GET("events/{site}/{id}.json")
  Call<Responses.EventDetails> getEvent(@Path("site") String site, @Path("id") int id);

}
