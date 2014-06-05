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

}
