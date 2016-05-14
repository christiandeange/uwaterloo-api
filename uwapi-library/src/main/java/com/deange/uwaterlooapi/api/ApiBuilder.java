package com.deange.uwaterlooapi.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;

public class ApiBuilder {

    public static final int VERSION = 2;
    public static final String BASE_URL = "https://api.uwaterloo.ca/v" + VERSION;
    public static final String API_KEY = "key";

    /* package */ static Converter sJsonConverter = ApiModelConverter.newGsonInstance();
    /* package */ static Converter sXmlConverter = ApiModelConverter.newXmlInstance();

    public static <T> T buildJson(final UWaterlooApi api, final Class<T> clazz) {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(new ApiInterceptor(api))
                .setConverter(sJsonConverter)
                .build()
                .create(clazz);
    }

    public static <T> T buildXml(final String baseUrl, final Class<T> clazz) {
        return new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setConverter(sXmlConverter)
                .build()
                .create(clazz);
    }

    private static class ApiInterceptor implements RequestInterceptor {

        private UWaterlooApi mApi;

        private ApiInterceptor(final UWaterlooApi api) {
            mApi = api;
        }

        @Override
        public void intercept(final RequestFacade requestFacade) {

            // Ensure the API has been properly initialized
            mApi.checkAccess();

            requestFacade.addQueryParam(API_KEY, mApi.getApiKey());
        }

    }

}
