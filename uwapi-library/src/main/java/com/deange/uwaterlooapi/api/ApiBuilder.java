package com.deange.uwaterlooapi.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ApiBuilder {

    public static final int VERSION = 2;
    public static final String BASE_URL = "https://api.uwaterloo.ca/v" + VERSION + "/";
    public static final String API_KEY = "key";

    /* package */ static Converter.Factory sJsonConverter = ApiModelConverter.newGsonInstance();
    /* package */ static Converter.Factory sXmlConverter = ApiModelConverter.newXmlInstance();

    public static <T> T buildJson(final UWaterlooApi api, final Class<T> clazz) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient(api))
                .addConverterFactory(sJsonConverter)
                .build()
                .create(clazz);
    }

    public static <T> T buildXml(final String baseUrl, final Class<T> clazz) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(sXmlConverter)
                .build()
                .create(clazz);
    }

    private static OkHttpClient getClient(final UWaterlooApi api) {
        return new OkHttpClient.Builder().addInterceptor(new ApiInterceptor(api)).build();
    }

    private static class ApiInterceptor
            implements
            Interceptor {

        private UWaterlooApi mApi;

        private ApiInterceptor(final UWaterlooApi api) {
            mApi = api;
        }

        @Override
        public Response intercept(final Chain chain) throws IOException {
            mApi.checkAccess();

            final HttpUrl.Builder urlBuilder = chain.request().url().newBuilder();
            urlBuilder.addQueryParameter(API_KEY, mApi.getApiKey());
            final Request request = chain.request().newBuilder().url(urlBuilder.build()).build();

            return chain.proceed(request);
        }
    }

}
