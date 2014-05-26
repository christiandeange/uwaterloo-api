package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.foodservices.MenuResponse;
import com.deange.uwaterlooapi.model.foodservices.NoteResponse;

import retrofit.http.GET;

public interface FoodServicesApi {

    /**
     * This method returns current week's food menu.
     */
    @GET("/foodservices/menu.{format}")
    public MenuResponse getWeeklyMenu();

    /**
     * This method returns additional notes regarding food served in the current week
     */
    @GET("/foodservices/notes.{format}")
    public NoteResponse getNotes();

}
