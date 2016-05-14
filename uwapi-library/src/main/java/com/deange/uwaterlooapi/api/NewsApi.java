package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsApi {

    /**
     * This method returns a list of the freshest (most recently updated) 100
     * University of Waterloo news items as crawled from all University WCMS sites
     */
    @GET("news.json")
    Call<Response.News> getNews();

    /**
     * This method returns a list of the upcoming site's news given a site slug
     * @param site Valid site slug from /resources/sites
     */
    @GET("news/{site}.json")
    Call<Response.News> getNews(@Path("site") String site);

    /**
     * This method returns a specific news item's information given a site's slug and id
     * @param site Valid site slug from /resources/sites
     * @param id Valid news id
     */
    @GET("news/{site}/{id}.json")
    Call<Response.NewsEntity> getNews(@Path("site") String site, @Path("id") int id);

}
