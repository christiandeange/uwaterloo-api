package com.deange.uwaterlooapi;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.deange.uwaterlooapi.utils.Endpoints.UWATERLOO;

/* package */ class UWaterlooApiInterceptor
    implements
    Interceptor {

  private UWaterlooApi mApi;

  UWaterlooApiInterceptor(final UWaterlooApi api) {
    mApi = api;
  }

  @Override
  public Response intercept(final Chain chain) throws IOException {
    final String apiKey = mApi.getApiKey();
    if (apiKey == null) {
      throw new IllegalStateException("API key is null");
    }

    Request request = chain.request();
    HttpUrl url = request.url();
    if (url.toString().startsWith(UWATERLOO)) {
      url = url.newBuilder().addQueryParameter("key", apiKey).build();
      request = request.newBuilder().url(url).build();
    }

    return chain.proceed(request);
  }
}
