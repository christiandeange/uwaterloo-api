package com.deange.uwaterlooapi;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static com.deange.uwaterlooapi.ApiModelConverter.newCustomInstance;
import static com.deange.uwaterlooapi.ApiModelConverter.newGsonInstance;
import static com.deange.uwaterlooapi.ApiModelConverter.newXmlInstance;

/* package */ class ApiBuilder {

  private static Converter.Factory sCustomConverter = newCustomInstance();
  private static Converter.Factory sJsonConverter = newGsonInstance();
  private static Converter.Factory sXmlConverter = newXmlInstance();

  private final OkHttpClient mClient;

  ApiBuilder(UWaterlooApi api) {
    mClient = new OkHttpClient.Builder()
        .cookieJar(new MemoryCookieJar())
        .addInterceptor(new UWaterlooApiInterceptor(api))
        .addInterceptor(new WatcardInterceptor(api))
        .build();
  }

  <T> T buildJson(final String baseUrl, final Class<T> clazz) {
    return build(baseUrl, clazz, sJsonConverter);
  }

  <T> T buildXml(final String baseUrl, final Class<T> clazz) {
    return build(baseUrl, clazz, sXmlConverter);
  }

  <T> T buildCustom(final String baseUrl, final Class<T> clazz) {
    return build(baseUrl, clazz, sCustomConverter);
  }

  private <T> T build(
      final String baseUrl,
      final Class<T> clazz,
      final Converter.Factory factory) {

    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(mClient)
        .addConverterFactory(factory)
        .build()
        .create(clazz);
  }
}
