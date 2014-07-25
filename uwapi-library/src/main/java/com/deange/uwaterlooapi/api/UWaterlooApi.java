package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.deange.uwaterlooapi.utils.CollectionsPolicy;

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

    public static void setPolicy(final CollectionsPolicy policy) {
        CollectionUtils.setPolicy(policy);
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

    public final ResourcesApi Resources = ApiBuilder.build(this, ResourcesApi.class);

    public final BuildingsApi Buildings = ApiBuilder.build(this, BuildingsApi.class);

}
