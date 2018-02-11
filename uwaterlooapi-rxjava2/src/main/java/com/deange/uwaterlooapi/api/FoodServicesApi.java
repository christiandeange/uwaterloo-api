package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Responses;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodServicesApi {

  /**
   * This method returns current week's food menu.
   */
  @GET("foodservices/menu.json")
  Observable<Responses.Menus> getWeeklyMenu();

  /**
   * This method returns additional notes regarding food served in the current week
   */
  @GET("foodservices/notes.json")
  Observable<Responses.Notes> getNotes();

  /**
   * This method returns a list of all diets
   */
  @GET("foodservices/diets.json")
  Observable<Responses.Diets> getDiets();

  /**
   * This method returns a list of all outlets and their unique IDs, names and
   * breakfast/lunch/dinner meal service indicators
   */
  @GET("foodservices/outlets.json")
  Observable<Responses.Outlets> getOutlets();

  /**
   * This method returns a list of all outlets and their operating hour data
   */
  @GET("foodservices/locations.json")
  Observable<Responses.Locations> getLocations();

  /**
   * This method returns a list of all WatCard locations according to Food Services
   */
  @GET("foodservices/watcard.json")
  Observable<Responses.Watcards> getWatcardVendors();

  /**
   * This method returns additional announcements regarding food served in the current week
   */
  @GET("foodservices/announcements.json")
  Observable<Responses.Announcements> getAnnouncements();

  /**
   * This method returns a product's nutritional information
   *
   * @param productId Valid product ID from menu
   */
  @GET("foodservices/products/{product_id}.json")
  Observable<Responses.Products> getProduct(@Path("product_id") int productId);

  /**
   * This method returns the given week and year's food menu.
   *
   * @param year The year of menu to be requested
   * @param week The week number of the menu to be requested
   */
  @GET("foodservices/{year}/{week}/menu.json")
  Observable<Responses.Menus> getWeeklyMenu(@Path("year") int year, @Path("week") int week);

  /**
   * This method returns additional notes regarding food served in the given week
   *
   * @param year The year of notes to be requested
   * @param week The week number of the notes to be requested
   */
  @GET("foodservices/{year}/{week}/notes.json")
  Observable<Responses.Notes> getNotes(@Path("year") int year, @Path("week") int week);

  /**
   * This method returns additional announcements regarding food served in the given week
   *
   * @param year The year of announcements to be requested
   * @param week The week number of the announcements to be requested
   */
  @GET("foodservices/{year}/{week}/announcements.json")
  Observable<Responses.Announcements> getAnnouncements(
      @Path("year") int year, @Path("week") int week);
}
