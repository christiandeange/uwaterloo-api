package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;
import retrofit.http.Path;

public interface NewsApi {

    /**
     * This method returns a list of the freshest (most recently updated) 100
     * University of Waterloo news items as crawled from all University WCMS sites
     */
    @GET("/news.{format}")
    public Response.News getNews();

    /**
     * This method returns a list of the upcoming site's news given a site slug
     * @param site Valid site slug from /resources/sites
     */
    @GET("/news/{site}.{format}")
    public Response.News getNews(@Path("site") String site);

}
