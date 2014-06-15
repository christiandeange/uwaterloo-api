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

    public final CoursesApi CoursesApi = ApiBuilder.build(this, CoursesApi.class);

}
