package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.foodservices.AnnouncementsResponse;
import com.deange.uwaterlooapi.model.foodservices.DietResponse;
import com.deange.uwaterlooapi.model.foodservices.LocationsResponse;
import com.deange.uwaterlooapi.model.foodservices.MenuResponse;
import com.deange.uwaterlooapi.model.foodservices.NoteResponse;
import com.deange.uwaterlooapi.model.foodservices.OutletResponse;
import com.deange.uwaterlooapi.model.foodservices.ProductResponse;
import com.deange.uwaterlooapi.model.foodservices.WatcardResponse;

import retrofit.http.GET;
import retrofit.http.Path;

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

    /**
     * This method returns a list of all outlets and their operating hour data
     */
    @GET("/foodservices/locations.{format}")
    public LocationsResponse getLocations();

    /**
     * This method returns a list of all WatCard locations according to Food Services
     */
    @GET("/foodservices/watcard.{format}")
    public WatcardResponse getWatcardVendors();

    /**
     * This method returns additional announcements regarding food served in the current week
     */
    @GET("/foodservices/announcements.{format}")
    public AnnouncementsResponse getAnnouncements();

    /**
     * This method returns a product's nutritional information
     */
    @GET("/foodservices/products/{product_id}.{format}")
    public ProductResponse getProduct(@Path("product_id") int productId);
}
