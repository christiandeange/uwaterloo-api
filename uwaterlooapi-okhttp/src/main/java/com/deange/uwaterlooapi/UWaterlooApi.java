package com.deange.uwaterlooapi;

import com.deange.uwaterlooapi.api.BuildingsApi;
import com.deange.uwaterlooapi.api.CoursesApi;
import com.deange.uwaterlooapi.api.EventsApi;
import com.deange.uwaterlooapi.api.FoodServicesApi;
import com.deange.uwaterlooapi.api.LegacyWeatherApi;
import com.deange.uwaterlooapi.api.NewsApi;
import com.deange.uwaterlooapi.api.ParkingApi;
import com.deange.uwaterlooapi.api.PointsOfInterestApi;
import com.deange.uwaterlooapi.api.ResourcesApi;
import com.deange.uwaterlooapi.api.TermsApi;
import com.deange.uwaterlooapi.api.WatcardApi;
import com.deange.uwaterlooapi.api.WeatherApi;
import com.deange.uwaterlooapi.model.watcard.WatcardCredentials;

import static com.deange.uwaterlooapi.utils.Endpoints.LEGACY_WEATHER;
import static com.deange.uwaterlooapi.utils.Endpoints.UWATERLOO;
import static com.deange.uwaterlooapi.utils.Endpoints.WATCARDS;

public final class UWaterlooApi {

  private final ApiBuilder builder;
  private final FoodServicesApi foodServices;
  private final CoursesApi courses;
  private final EventsApi events;
  private final NewsApi news;
  private final WeatherApi weather;
  private final TermsApi terms;
  private final ResourcesApi resources;
  private final BuildingsApi buildings;
  private final ParkingApi parking;
  private final PointsOfInterestApi pointsOfInterest;
  private final LegacyWeatherApi legacyWeather;
  private final WatcardApi watcards;

  private final String mApiKey;
  private WatcardCredentials mWatcardCredentials;

  public UWaterlooApi(final String apiKey) {
    mApiKey = apiKey;

    builder = new ApiBuilder(this);
    foodServices = jsonApi(FoodServicesApi.class);
    courses = jsonApi(CoursesApi.class);
    events = jsonApi(EventsApi.class);
    news = jsonApi(NewsApi.class);
    weather = jsonApi(WeatherApi.class);
    terms = jsonApi(TermsApi.class);
    resources = jsonApi(ResourcesApi.class);
    buildings = jsonApi(BuildingsApi.class);
    parking = jsonApi(ParkingApi.class);
    pointsOfInterest = jsonApi(PointsOfInterestApi.class);
    legacyWeather = xmlApi(LEGACY_WEATHER, LegacyWeatherApi.class);
    watcards = customApi(WATCARDS, WatcardApi.class);
  }

  public String getApiKey() {
    return mApiKey;
  }

  public WatcardCredentials getWatcardCredentials() {
    return mWatcardCredentials;
  }

  public void setWatcardCredentials(final WatcardCredentials watcardCredentials) {
    mWatcardCredentials = watcardCredentials;
  }

  public FoodServicesApi foodServices() {
    return foodServices;
  }

  public CoursesApi courses() {
    return courses;
  }

  public EventsApi events() {
    return events;
  }

  public NewsApi news() {
    return news;
  }

  public WeatherApi weather() {
    return weather;
  }

  public TermsApi terms() {
    return terms;
  }

  public ResourcesApi resources() {
    return resources;
  }

  public BuildingsApi buildings() {
    return buildings;
  }

  public ParkingApi parking() {
    return parking;
  }

  public PointsOfInterestApi pointsOfInterest() {
    return pointsOfInterest;
  }

  public LegacyWeatherApi legacyWeather() {
    return legacyWeather;
  }

  public WatcardApi watcards() {
    return watcards;
  }

  private <T> T jsonApi(Class<T> clazz) {
    return builder.buildJson(UWATERLOO, clazz);
  }

  private <T> T xmlApi(String url, Class<T> clazz) {
    return builder.buildXml(url, clazz);
  }

  private <T> T customApi(String url, Class<T> clazz) {
    return builder.buildCustom(url, clazz);
  }
}
