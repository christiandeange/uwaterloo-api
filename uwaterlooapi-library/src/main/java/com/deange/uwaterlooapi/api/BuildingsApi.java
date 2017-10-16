package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Responses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BuildingsApi {

  /**
   * This method returns a list of official building names, codes, numbers, and their
   * lat/long coordinates.
   */
  @GET("buildings/list.json")
  Call<Responses.Buildings> getBuildings();

  /**
   * This method returns the official building name, its unique number, and its
   * lat/long coordinates given a building code.
   *
   * @param buildingCode Building code, eg: CPH, SLC, DC
   */
  @GET("buildings/{building_code}.json")
  Call<Responses.BuildingEntity> getBuilding(@Path("building_code") String buildingCode);

  /**
   * This method gives out the all the courses offered in a given classroom.
   *
   * @param buildingCode Building code, eg: CPH, SLC, DC
   * @param room         Room number
   */
  @GET("buildings/{building_code}/{room}/courses.json")
  Call<Responses.RoomCourses> getClassroomCourses(
      @Path("building_code") String buildingCode,
      @Path("room") String room);

}
