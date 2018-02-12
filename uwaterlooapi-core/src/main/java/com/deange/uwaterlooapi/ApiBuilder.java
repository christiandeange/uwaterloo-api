package com.deange.uwaterlooapi;

import java.util.ArrayList;
import java.util.List;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static com.deange.uwaterlooapi.ApiModelConverter.newCustomInstance;
import static com.deange.uwaterlooapi.ApiModelConverter.newGsonInstance;
import static com.deange.uwaterlooapi.ApiModelConverter.newXmlInstance;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/* package */ class ApiBuilder {

  private static Converter.Factory sCustomConverter = newCustomInstance();
  private static Converter.Factory sJsonConverter = newGsonInstance();
  private static Converter.Factory sXmlConverter = newXmlInstance();

  private final OkHttpClient mClient;
  private final List<CallAdapter.Factory> mCallAdapters;

  public ApiBuilder(OkHttpClient client) {
    this(client, emptyList());
  }

  public ApiBuilder(OkHttpClient client, List<CallAdapter.Factory> callAdapters) {
    mClient = client;
    mCallAdapters = unmodifiableList(callAdapters);
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

    final Retrofit.Builder builder = new Retrofit.Builder();
    builder.baseUrl(baseUrl);
    builder.client(mClient);
    builder.addConverterFactory(factory);
    for (CallAdapter.Factory adapter : mCallAdapters) {
      builder.addCallAdapterFactory(adapter);
    }

    return builder.build().create(clazz);
  }

  static class Builder {
    private final List<Interceptor> interceptors = new ArrayList<>();
    private final List<CallAdapter.Factory> callAdapters = new ArrayList<>();
    private CookieJar cookieJar;

    public Builder addInterceptor(Interceptor interceptor) {
      interceptors.add(interceptor);
      return this;
    }

    public Builder addCallAdapterFactory(CallAdapter.Factory callAdapter) {
      callAdapters.add(callAdapter);
      return this;
    }

    public Builder setCookieJar(CookieJar cookieJar) {
      this.cookieJar = cookieJar;
      return this;
    }

    public ApiBuilder build() {
      final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
      clientBuilder.cookieJar(cookieJar);
      for (Interceptor interceptor : interceptors) {
        clientBuilder.addInterceptor(interceptor);
      }

      return new ApiBuilder(clientBuilder.build(), callAdapters);
    }
  }
}
