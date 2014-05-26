package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.foodservices.DietResponse;
import com.deange.uwaterlooapi.model.foodservices.MenuResponse;
import com.deange.uwaterlooapi.model.foodservices.NoteResponse;
import com.deange.uwaterlooapi.model.foodservices.OutletResponse;

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

    /**
     * This method returns a list of all diets
     */
    @GET("/foodservices/diets.{format}")
    public DietResponse getDiets();

    /**
     * This method returns a list of all outlets and their unique IDs, names and
     * breakfast/lunch/dinner meal service indicators
     */
    @GET("/foodservices/outlets.{format}")
    public OutletResponse getOutlets();

}
