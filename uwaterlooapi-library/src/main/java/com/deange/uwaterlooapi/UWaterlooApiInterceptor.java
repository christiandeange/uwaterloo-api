package com.deange.uwaterlooapi;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* package */ class UWaterlooApiInterceptor
    implements
    Interceptor {

  private UWaterlooApi mApi;

  UWaterlooApiInterceptor(final UWaterlooApi api) {
    mApi = api;
  }

  @Override
  public Response intercept(final Chain chain) throws IOException {
    mApi.checkAccess();

    Request request = chain.request();
    HttpUrl url = request.url();
    if (url.toString().startsWith(ApiBuilder.BASE_URL)) {
      url = url.newBuilder().addQueryParameter(ApiBuilder.API_KEY, mApi.getApiKey()).build();
      request = request.newBuilder().url(url).build();
    }

    return chain.proceed(request);
  }
}
