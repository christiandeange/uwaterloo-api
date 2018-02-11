package com.deange.uwaterlooapi;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.deange.uwaterlooapi.ApiModelConverter.newCustomInstance;
import static com.deange.uwaterlooapi.ApiModelConverter.newGsonInstance;
import static com.deange.uwaterlooapi.ApiModelConverter.newXmlInstance;

/* package */ class ApiBuilder {

  private static Converter.Factory sCustomConverter = newCustomInstance();
  private static Converter.Factory sJsonConverter = newGsonInstance();
  private static Converter.Factory sXmlConverter = newXmlInstance();

  private final OkHttpClient mClient;
  private final RxJava2CallAdapterFactory mAdapterFactory;

  ApiBuilder(UWaterlooApi api) {
    mAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
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
        .addCallAdapterFactory(mAdapterFactory)
        .build()
        .create(clazz);
  }
}
