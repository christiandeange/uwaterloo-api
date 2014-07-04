package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.common.DataFormat;

public final class UWaterlooApi {

    private String mApiKey;
    private DataFormat sDataFormat = DataFormat.JSON;

    public UWaterlooApi(final String apiKey) {
        mApiKey = apiKey;
    }

    /* package */ String getApiKey() {
        return mApiKey;
    }

    /* package */ DataFormat getDataFormat() {
        return sDataFormat;
    }

    public void checkAccess() {
        if (mApiKey == null) {
            throw new IllegalStateException("API key is null!");
        }
    }


    /**
     * APIs DEFINED BELOW
     */

    public final FoodServicesApi FoodServices = ApiBuilder.build(this, FoodServicesApi.class);

    public final CoursesApi Courses = ApiBuilder.build(this, CoursesApi.class);

    public final EventsApi Events = ApiBuilder.build(this, EventsApi.class);

    public final NewsApi News = ApiBuilder.build(this, NewsApi.class);

    public final WeatherApi Weather = ApiBuilder.build(this, WeatherApi.class);

    public final TermsApi Terms = ApiBuilder.build(this, TermsApi.class);

}
