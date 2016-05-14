package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodServicesApi {

    /**
     * This method returns current week's food menu.
     */
    @GET("foodservices/menu.json")
    Call<Response.Menus> getWeeklyMenu();

    /**
     * This method returns additional notes regarding food served in the current week
     */
    @GET("foodservices/notes.json")
    Call<Response.Notes> getNotes();

    /**
     * This method returns a list of all diets
     */
    @GET("foodservices/diets.json")
    Call<Response.Diets> getDiets();

    /**
     * This method returns a list of all outlets and their unique IDs, names and
     * breakfast/lunch/dinner meal service indicators
     */
    @GET("foodservices/outlets.json")
    Call<Response.Outlets> getOutlets();

    /**
     * This method returns a list of all outlets and their operating hour data
     */
    @GET("foodservices/locations.json")
    Call<Response.Locations> getLocations();

    /**
     * This method returns a list of all WatCard locations according to Food Services
     */
    @GET("foodservices/watcard.json")
    Call<Response.Watcards> getWatcardVendors();

    /**
     * This method returns additional announcements regarding food served in the current week
     */
    @GET("foodservices/announcements.json")
    Call<Response.Announcements> getAnnouncements();

    /**
     * This method returns a product's nutritional information
     * @param productId Valid product ID from menu
     */
    @GET("foodservices/products/{product_id}.json")
    Call<Response.Products> getProduct(@Path("product_id") int productId);

    /**
     * This method returns the given week and year's food menu.
     * @param year The year of menu to be requested
     * @param week The week number of the menu to be requested
     */
    @GET("foodservices/{year}/{week}/menu.json")
    Call<Response.Menus> getWeeklyMenu(@Path("year") int year, @Path("week") int week);

    /**
     * This method returns additional notes regarding food served in the given week
     * @param year The year of notes to be requested
     * @param week The week number of the notes to be requested
     */
    @GET("foodservices/{year}/{week}/notes.json")
    Call<Response.Notes> getNotes(@Path("year") int year, @Path("week") int week);

    /**
     * This method returns additional announcements regarding food served in the given week
     * @param year The year of announcements to be requested
     * @param week The week number of the announcements to be requested
     */
    @GET("foodservices/{year}/{week}/announcements.json")
    Call<Response.Announcements> getAnnouncements(@Path("year") int year, @Path("week") int week);
}
