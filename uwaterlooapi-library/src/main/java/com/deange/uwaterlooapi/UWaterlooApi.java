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

import static com.deange.uwaterlooapi.ApiBuilder.BASE_URL;

public final class UWaterlooApi {

  private final String mApiKey;
  private WatcardCredentials mWatcardCredentials;

  public UWaterlooApi(final String apiKey) {
    mApiKey = apiKey;
  }

  /* package */ String getApiKey() {
    return mApiKey;
  }

  public void checkAccess() {
    if (mApiKey == null) {
      throw new IllegalStateException("API key is null!");
    }
  }

  public WatcardCredentials getWatcardCredentials() {
    return mWatcardCredentials;
  }

  public void setWatcardCredentials(final WatcardCredentials watcardCredentials) {
    mWatcardCredentials = watcardCredentials;
  }

  /**
   * APIs DEFINED BELOW
   */

  public final FoodServicesApi FoodServices = ApiBuilder.buildJson(
      this, BASE_URL, FoodServicesApi.class);

  public final CoursesApi Courses = ApiBuilder.buildJson(this, BASE_URL, CoursesApi.class);

  public final EventsApi Events = ApiBuilder.buildJson(this, BASE_URL, EventsApi.class);

  public final NewsApi News = ApiBuilder.buildJson(this, BASE_URL, NewsApi.class);

  public final WeatherApi Weather = ApiBuilder.buildJson(this, BASE_URL, WeatherApi.class);

  public final TermsApi Terms = ApiBuilder.buildJson(this, BASE_URL, TermsApi.class);

  public final ResourcesApi Resources = ApiBuilder.buildJson(this, BASE_URL, ResourcesApi.class);

  public final BuildingsApi Buildings = ApiBuilder.buildJson(this, BASE_URL, BuildingsApi.class);

  public final ParkingApi Parking = ApiBuilder.buildJson(this, BASE_URL, ParkingApi.class);

  public final PointsOfInterestApi PointsOfInterest = ApiBuilder.buildJson(
      this, BASE_URL, PointsOfInterestApi.class);

  public final LegacyWeatherApi LegacyWeather = ApiBuilder.buildXml(
      this, LegacyWeatherApi.URL, LegacyWeatherApi.class);

  public final WatcardApi Watcard = ApiBuilder.buildCustom(this, WatcardApi.URL, WatcardApi.class);
}
