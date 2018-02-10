package com.deange.uwaterlooapi.sample.net;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class FlickrApi {

  private static final String BASE_URL = "https://api.flickr.com/services/rest/";
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
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getClient(this))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FlickrInterface.class);
  }

  private static OkHttpClient getClient(final FlickrApi api) {
    return new OkHttpClient.Builder().addInterceptor(new ApiInterceptor(api)).build();
  }

  private static class ApiInterceptor
      implements
      Interceptor {

    private final FlickrApi mApi;

    private ApiInterceptor(final FlickrApi api) {
      mApi = api;
    }

    @Override
    public Response intercept(final Chain chain) throws IOException {
      final HttpUrl.Builder urlBuilder = chain.request().url().newBuilder();

      urlBuilder.addQueryParameter(API_KEY, mApi.getApiKey());
      urlBuilder.addQueryParameter(FORMAT_KEY, FORMAT_VALUE);
      urlBuilder.addQueryParameter(CALLBACK_KEY, CALLBACK_VALUE);

      final Request request = chain.request().newBuilder().url(urlBuilder.build()).build();
      return chain.proceed(request);
    }
  }

}
