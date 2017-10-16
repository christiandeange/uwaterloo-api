package com.deange.uwaterlooapi.model.converter;

import com.deange.uwaterlooapi.model.watcard.Transactions;
import com.deange.uwaterlooapi.model.watcard.Watcard;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class CustomConverterFactory
    extends Converter.Factory {

  public static Converter.Factory create() {
    return new CustomConverterFactory();
  }

  private CustomConverterFactory() {
  }

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(
      final Type type,
      final Annotation[] annotations,
      final Retrofit retrofit) {

    if (type == Watcard.class) {
      return new WatcardConverter(retrofit.baseUrl());
    } else if (type == Transactions.class) {
      return new TransactionsConverter(retrofit.baseUrl());
    } else {
      return null;
    }
  }
}
