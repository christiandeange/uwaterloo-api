package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.foodservices.MenuResponse;

import retrofit.http.GET;

public interface FoodServicesApi {

    @GET("/foodservices/menu.{format}")
    public MenuResponse getMenu();

}
