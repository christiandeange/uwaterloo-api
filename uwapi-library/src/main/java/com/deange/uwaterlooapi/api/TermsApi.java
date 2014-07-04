package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;
import retrofit.http.Path;

public interface TermsApi {

    /**
     * This method returns the current, previous and next term's id along with a list of terms
     * in the past year and the next year
     */
    @GET("/terms/list.{format}")
    public Response.Terms getTermList();

    /**
     * This method returns a given term's exam schedule
     * @param term Numeric representation of the term
     */
    @GET("/terms/{term}/examschedule.{format}")
    public Response.TermExamSchedule getExamSchedule(@Path("term") int term);

}
