package com.deange.uwaterlooapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/* package */ class ApiBuilder {

  static final int VERSION = 2;
  static final String BASE_URL = "https://api.uwaterloo.ca/v" + VERSION + "/";
  static final String API_KEY = "key";

  private static Converter.Factory sCustomConverter = ApiModelConverter.newCustomInstance();
  private static Converter.Factory sJsonConverter = ApiModelConverter.newGsonInstance();
  private static Converter.Factory sXmlConverter = ApiModelConverter.newXmlInstance();

  static <T> T buildJson(final UWaterlooApi api, final String baseUrl, final Class<T> clazz) {
    return build(api, baseUrl, clazz, sJsonConverter);
  }

  static <T> T buildXml(final UWaterlooApi api, final String baseUrl, final Class<T> clazz) {
    return build(api, baseUrl, clazz, sXmlConverter);
  }

  static <T> T buildCustom(final UWaterlooApi api, final String baseUrl, final Class<T> clazz) {
    return build(api, baseUrl, clazz, sCustomConverter);
  }

  private static <T> T build(
      final UWaterlooApi api,
      final String baseUrl,
      final Class<T> clazz,
      final Converter.Factory factory) {

    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(getClient(api))
        .addConverterFactory(factory)
        .build()
        .create(clazz);
  }

  private static OkHttpClient getClient(final UWaterlooApi api) {
    return new OkHttpClient.Builder()
        .cookieJar(new MemoryCookieJar())
        .addInterceptor(new UWaterlooApiInterceptor(api))
        .addInterceptor(new WatcardInterceptor(api))
        .build();
  }

  private static class MemoryCookieJar
      implements
      CookieJar {

    private final List<Cookie> mCookies = new ArrayList<>();

    @Override
    public void saveFromResponse(final HttpUrl url, final List<Cookie> cookies) {
      mCookies.addAll(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(final HttpUrl url) {
      final List<Cookie> cookies = new ArrayList<>();
      for (final Iterator<Cookie> iterator = mCookies.iterator(); iterator.hasNext(); ) {
        final Cookie cookie = iterator.next();
        if (cookie.expiresAt() < System.currentTimeMillis()) {
          iterator.remove();
          continue;
        }

        if (cookie.matches(url)) {
          cookies.add(cookie);
        }
      }

      return cookies;
    }
  }

}
