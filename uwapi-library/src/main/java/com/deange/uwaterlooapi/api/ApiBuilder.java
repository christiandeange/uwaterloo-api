package com.deange.uwaterlooapi.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ApiBuilder {

    public static final int VERSION = 2;
    public static final String BASE_URL = "https://api.uwaterloo.ca/v" + VERSION;

    public static final String API_KEY = "key";
    public static final String FORMAT = "format";

    /* package */ static ApiInterceptor sInterceptor = new ApiInterceptor();

    public static <T> T build(final Class<T> clazz) {

        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(sInterceptor)
                .build()
                .create(clazz);
    }

    private static class ApiInterceptor implements RequestInterceptor {

        @Override
        public void intercept(final RequestFacade requestFacade) {

            // Ensure the API has been properly initialized
            UWaterlooApi.checkAccess();

            requestFacade.addQueryParam(API_KEY, UWaterlooApi.getApiKey());
            requestFacade.addEncodedPathParam(FORMAT, UWaterlooApi.getDataFormat().getType());

        }

    }

}
