package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.weather.LegacyWeatherReading;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LegacyWeatherApi {

  String URL = "http://www.civil.uwaterloo.ca/weather/";

  /**
   * This method returns the current weather details from the University of Waterloo
   * Weather Station. Visit http://weather.uwaterloo.ca for more details
   */
  @GET("waterloo_weather_station_data.xml")
  Call<LegacyWeatherReading> getWeather();

}
