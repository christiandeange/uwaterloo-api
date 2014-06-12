package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;
import retrofit.http.Path;

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

}
