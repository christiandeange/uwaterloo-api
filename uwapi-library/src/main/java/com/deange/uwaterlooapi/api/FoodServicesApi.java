package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.foodservices.MenuResponse;

import retrofit.http.GET;

public interface FoodServicesApi {

    /**
     * This method returns current week's food menu.
     */
    @GET("/foodservices/menu.{format}")
    public MenuResponse getMenu();

}
