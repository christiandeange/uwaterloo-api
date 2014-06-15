package com.deange.uwaterlooapi.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ApiBuilder {

    public static final int VERSION = 2;
    public static final String BASE_URL = "https://api.uwaterloo.ca/v" + VERSION;

    public static final String API_KEY = "key";
    public static final String FORMAT = "format";

    /* package */ static ApiModelConverter sConverter = ApiModelConverter.newInstance();

    public static <T> T build(final UWaterlooApi api, final Class<T> clazz) {

        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(new ApiInterceptor(api))
                .setConverter(sConverter)
                .build()
                .create(clazz);
    }

    private static class ApiInterceptor implements RequestInterceptor {

        private UWaterlooApi mApi;

        private ApiInterceptor(UWaterlooApi api) {
            mApi = api;
        }

        @Override
        public void intercept(final RequestFacade requestFacade) {

            // Ensure the API has been properly initialized
            mApi.checkAccess();

            requestFacade.addQueryParam(API_KEY, mApi.getApiKey());
            requestFacade.addEncodedPathParam(FORMAT, mApi.getDataFormat().getType());

        }

    }

}
