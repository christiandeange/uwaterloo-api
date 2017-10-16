package com.deange.uwaterlooapi;

import com.deange.uwaterlooapi.api.WatcardApi;
import com.deange.uwaterlooapi.model.watcard.WatcardCredentials;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* package */ class WatcardInterceptor
    implements
    Interceptor {

  private final UWaterlooApi mApi;

  public WatcardInterceptor(final UWaterlooApi api) {
    mApi = api;
  }

  @Override
  public Response intercept(final Chain chain) throws IOException {
    Response response = chain.proceed(chain.request());

    if (!chain.request().url().toString().contains(WatcardApi.URL)) {
      return response;
    }

    final HttpUrl responseUrl = response.networkResponse().request().url();
    final WatcardCredentials credentials = mApi.getWatcardCredentials();

    if (responseUrl.queryParameterNames().contains("ReturnUrl") && credentials != null) {
      // Login required
      final ResponseBody homepageBody = mApi.Watcard.homepage().execute().body();
      final Document doc = Jsoup.parse(homepageBody.byteStream(), "UTF-8", WatcardApi.URL);
      final String token = doc.select("input[name=__RequestVerificationToken]").first().val();
      mApi.Watcard.login(credentials.studentNumber(), credentials.pin(), token).execute();

      // Retry original request
      response = chain.proceed(chain.request());
    }

    return response;
  }

}
