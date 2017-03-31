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
import com.deange.uwaterlooapi.api.WeatherApi;

public final class UWaterlooApi {

    private String mApiKey;

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

    /**
     * APIs DEFINED BELOW
     */

    public final FoodServicesApi FoodServices = ApiBuilder.buildJson(this, FoodServicesApi.class);

    public final CoursesApi Courses = ApiBuilder.buildJson(this, CoursesApi.class);

    public final EventsApi Events = ApiBuilder.buildJson(this, EventsApi.class);

    public final NewsApi News = ApiBuilder.buildJson(this, NewsApi.class);

    public final WeatherApi Weather = ApiBuilder.buildJson(this, WeatherApi.class);

    public final TermsApi Terms = ApiBuilder.buildJson(this, TermsApi.class);

    public final ResourcesApi Resources = ApiBuilder.buildJson(this, ResourcesApi.class);

    public final BuildingsApi Buildings = ApiBuilder.buildJson(this, BuildingsApi.class);

    public final ParkingApi Parking = ApiBuilder.buildJson(this, ParkingApi.class);

    public final PointsOfInterestApi PointsOfInterest = ApiBuilder.buildJson(this, PointsOfInterestApi.class);

    public final LegacyWeatherApi LegacyWeather = ApiBuilder.buildXml(LegacyWeatherApi.URL, LegacyWeatherApi.class);

}
