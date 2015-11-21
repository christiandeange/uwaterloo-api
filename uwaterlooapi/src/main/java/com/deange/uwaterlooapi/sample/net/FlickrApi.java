package com.deange.uwaterlooapi.sample.net;

import com.deange.uwaterlooapi.sample.BuildConfig;
import com.google.gson.Gson;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public final class FlickrApi {

    private static final String BASE_URL = "https://api.flickr.com/services/rest";
    private static final String API_KEY = "api_key";
    private static final String FORMAT_KEY = "format";
    private static final String FORMAT_VALUE = "json";
    private static final String CALLBACK_KEY = "nojsoncallback";
    private static final String CALLBACK_VALUE = "1";

    private String mApiKey;
    private FlickrInterface mInterface;

    public FlickrApi(final String apiKey) {
        mApiKey = apiKey;
        mInterface = buildInterface();
    }

    public String getApiKey() {
        return mApiKey;
    }

    public FlickrInterface get() {
        return mInterface;
    }

    private FlickrInterface buildInterface() {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(new ApiInterceptor(this))
                .setConverter(new GsonConverter(new Gson(), "UTF-8"))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build()
                .create(FlickrInterface.class);
    }

    private static class ApiInterceptor implements RequestInterceptor {

        private FlickrApi mApi;

        private ApiInterceptor(FlickrApi api) {
            mApi = api;
        }

        @Override
        public void intercept(final RequestFacade requestFacade) {
            requestFacade.addQueryParam(API_KEY, mApi.getApiKey());
            requestFacade.addQueryParam(FORMAT_KEY, FORMAT_VALUE);
            requestFacade.addQueryParam(CALLBACK_KEY, CALLBACK_VALUE);
        }

    }

}
