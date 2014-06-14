package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface CoursesApi {

    /**
     * This method returns all the coures offered under a given subject
     * @param subject Valid uWaterloo subject name
     */
    @GET("/courses/{subject}.{format}")
    public Response.Courses getCourseInfo(@Path("subject") String subject);

    /**
     * This method returns all available information for a given course
     * @param courseId Valid uWaterloo course ID
     */
    @GET("/courses/{course_id}.{format}")
    public Response.CoursesInfo getCourseInfo(@Path("course_id") int courseId);

    /**
     * This method returns the class schedule for a given course and term
     * @param classNumber Valid uWaterloo course number
     */
    @GET("/courses/{class_number}/schedule.{format}")
    public Response.CoursesSchedule getCourseSchedule(@Path("class_number") int classNumber);

    /**
     * This method returns the class schedule for a given course and term
     * @param classNumber Valid uWaterloo course number
     * @param term Four digit term representation
     */
    @GET("/courses/{class_number}/schedule.{format}")
    public Response.CoursesSchedule getCourseSchedule(@Path("class_number") int classNumber,
                                                      @Query("term") int term);

    /**
     * This method returns all available information for a given course
     * @param subject Valid uWaterloo subject name
     * @param courseCode Valid uWaterloo course number
     */
    @GET("/courses/{subject}/{catalog_number}.{format}")
    public Response.CoursesInfo getCourseInfo(@Path("subject") String subject,
                                              @Path("catalog_number") String courseCode);

    /**
     * This method returns the class schedule for a given course and term
     * @param subject Valid uWaterloo subject name
     * @param courseCode Valid uWaterloo course number
     */
    @GET("/courses/{subject}/{catalog_number}/schedule.{format}")
    public Response.CoursesSchedule getCourseSchedule(@Path("subject") String subject,
                                                      @Path("catalog_number") String courseCode);

    /**
     * This method returns the class schedule for a given course and term
     * @param subject Valid uWaterloo subject name
     * @param courseCode Valid uWaterloo course number
     * @param term Four digit term representation
     */
    @GET("/courses/{subject}/{catalog_number}/schedule.{format}")
    public Response.CoursesSchedule getCourseSchedule(@Path("subject") String subject,
                                                      @Path("catalog_number") String courseCode,
                                                      @Query("term") int term);

    /**
     * This method returns parsed and raw representation of prerequsites for a given course
     * @param subject Valid uWaterloo subject name
     * @param courseCode Valid uWaterloo course number
     */
    @GET("/courses/{subject}/{catalog_number}/prerequisites.{format}")
    public Response.Prerequisites getPrerequisites(@Path("subject") String subject,
                                                   @Path("catalog_number") String courseCode);

    /**
     * This method returns a given course's exam schedule
     * @param subject Valid uWaterloo subject name
     * @param courseCode Valid uWaterloo course number
     */
    @GET("/courses/{subject}/{catalog_number}/examschedule.{format}")
    public Response.ExamSchedule getExamSchedule(@Path("subject") String subject,
                                                 @Path("catalog_number") String courseCode);

}
