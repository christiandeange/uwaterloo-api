package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.Response;

import retrofit.http.GET;

public interface WeatherApi {

    /**
     * This method returns the current weather details from the University of Waterloo
     * Weather Station. Visit http://weather.uwaterloo.ca for more details
     */
    @GET("/weather/current.{format}")
    Response.Weather getWeather();

}
