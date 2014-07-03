package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface NewsApi {

    @GET("/news.{format}")
    public Response.News getNews();

}
