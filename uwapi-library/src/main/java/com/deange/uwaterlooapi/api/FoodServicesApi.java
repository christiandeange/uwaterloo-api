package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.foodservices.Response;

import retrofit.http.GET;
import retrofit.http.Path;

public interface FoodServicesApi {

    /**
     * This method returns current week's food menu.
     */
    @GET("/foodservices/menu.{format}")
    public Response.Menus getWeeklyMenu();

    /**
     * This method returns additional notes regarding food served in the current week
     */
    @GET("/foodservices/notes.{format}")
    public Response.Notes getNotes();

    /**
     * This method returns a list of all diets
     */
    @GET("/foodservices/diets.{format}")
    public Response.Diets getDiets();

    /**
     * This method returns a list of all outlets and their unique IDs, names and
     * breakfast/lunch/dinner meal service indicators
     */
    @GET("/foodservices/outlets.{format}")
    public Response.Outlets getOutlets();

    /**
     * This method returns a list of all outlets and their operating hour data
     */
    @GET("/foodservices/locations.{format}")
    public Response.Locations getLocations();

    /**
     * This method returns a list of all WatCard locations according to Food Services
     */
    @GET("/foodservices/watcard.{format}")
    public Response.Watcards getWatcardVendors();

    /**
     * This method returns additional announcements regarding food served in the current week
     */
    @GET("/foodservices/announcements.{format}")
    public Response.Announcements getAnnouncements();

    /**
     * This method returns a product's nutritional information
     */
    @GET("/foodservices/products/{product_id}.{format}")
    public Response.Products getProduct(@Path("product_id") int productId);

    /**
     * This method returns the given week and year's food menu.
     */
    @GET("/foodservices/{year}/{week}/menu.{format}")
    public Response.Menus getWeeklyMenu(@Path("year") int year, @Path("week") int week);
}
