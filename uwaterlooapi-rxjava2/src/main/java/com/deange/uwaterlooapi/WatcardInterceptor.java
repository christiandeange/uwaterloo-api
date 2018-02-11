package com.deange.uwaterlooapi;

import com.deange.uwaterlooapi.model.watcard.WatcardCredentials;
import com.deange.uwaterlooapi.utils.Endpoints;
import io.reactivex.Observable;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

    if (!chain.request().url().toString().contains(Endpoints.WATCARDS)) {
      return response;
    }

    final HttpUrl responseUrl = response.networkResponse().request().url();
    final WatcardCredentials credentials = mApi.getWatcardCredentials();

    if (credentials != null && responseUrl.queryParameterNames().contains("ReturnUrl")) {
      // Login required
      final ResponseBody homepageBody = perform(mApi.watcards().homepage());
      final Document doc = Jsoup.parse(homepageBody.byteStream(), "UTF-8", Endpoints.WATCARDS);
      final String token = doc.select("input[name=__RequestVerificationToken]").first().val();
      perform(mApi.watcards().login(credentials.studentNumber(), credentials.pin(), token));

      // Retry original request
      response = chain.proceed(chain.request());
    }

    return response;
  }

  private <T> T perform(final Observable<T> call) throws IOException {
    return call.blockingFirst();
  }
}
