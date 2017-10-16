package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Responses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoursesApi {

  /**
   * This method returns all the coures offered under a given subject
   *
   * @param subject Valid uWaterloo subject name
   */
  @GET("courses/{subject}.json")
  Call<Responses.Courses> getCourseInfo(@Path("subject") String subject);

  /**
   * This method returns all available information for a given course
   *
   * @param courseId Valid uWaterloo course ID
   */
  @GET("courses/{course_id}.json")
  Call<Responses.CoursesInfo> getCourseInfo(@Path("course_id") int courseId);

  /**
   * This method returns the class schedule for a given course and term
   *
   * @param classNumber Valid uWaterloo course number
   */
  @GET("courses/{class_number}/schedule.json")
  Call<Responses.CoursesSchedule> getCourseSchedule(@Path("class_number") int classNumber);

  /**
   * This method returns the class schedule for a given course and term
   *
   * @param classNumber Valid uWaterloo course number
   * @param term        Four digit term representation
   */
  @GET("courses/{class_number}/schedule.json")
  Call<Responses.CoursesSchedule> getCourseSchedule(
      @Path("class_number") int classNumber,
      @Query("term") int term);

  /**
   * This method returns all available information for a given course
   *
   * @param subject    Valid uWaterloo subject name
   * @param courseCode Valid uWaterloo course number
   */
  @GET("courses/{subject}/{catalog_number}.json")
  Call<Responses.CoursesInfo> getCourseInfo(
      @Path("subject") String subject,
      @Path("catalog_number") String courseCode);

  /**
   * This method returns the class schedule for a given course and term
   *
   * @param subject    Valid uWaterloo subject name
   * @param courseCode Valid uWaterloo course number
   */
  @GET("courses/{subject}/{catalog_number}/schedule.json")
  Call<Responses.CoursesSchedule> getCourseSchedule(
      @Path("subject") String subject,
      @Path("catalog_number") String courseCode);

  /**
   * This method returns the class schedule for a given course and term
   *
   * @param subject    Valid uWaterloo subject name
   * @param courseCode Valid uWaterloo course number
   * @param term       Four digit term representation
   */
  @GET("courses/{subject}/{catalog_number}/schedule.json")
  Call<Responses.CoursesSchedule> getCourseSchedule(
      @Path("subject") String subject,
      @Path("catalog_number") String courseCode,
      @Query("term") int term);

  /**
   * This method returns parsed and raw representation of prerequsites for a given course
   *
   * @param subject    Valid uWaterloo subject name
   * @param courseCode Valid uWaterloo course number
   */
  @GET("courses/{subject}/{catalog_number}/prerequisites.json")
  Call<Responses.Prerequisites> getPrerequisites(
      @Path("subject") String subject,
      @Path("catalog_number") String courseCode);

  /**
   * This method returns a given course's exam schedule
   *
   * @param subject    Valid uWaterloo subject name
   * @param courseCode Valid uWaterloo course number
   */
  @GET("courses/{subject}/{catalog_number}/examschedule.json")
  Call<Responses.ExamSchedule> getExamSchedule(
      @Path("subject") String subject,
      @Path("catalog_number") String courseCode);

}
