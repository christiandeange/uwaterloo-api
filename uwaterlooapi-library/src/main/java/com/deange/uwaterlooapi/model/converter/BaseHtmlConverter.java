package com.deange.uwaterlooapi.model.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* package */ abstract class BaseHtmlConverter<T>
    implements
    Converter<ResponseBody, T> {

  private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(
      Locale.CANADA);

  protected final HttpUrl mBaseUrl;

  public BaseHtmlConverter(final HttpUrl baseUrl) {
    mBaseUrl = baseUrl;
  }

  @Override
  public final T convert(final ResponseBody value) throws IOException {
    return parse(Jsoup.parse(value.byteStream(), "UTF-8", mBaseUrl.toString()));
  }

  public abstract T parse(final Document document);

  protected static BigDecimal parseAmount(String amount) {
    try {
      final double val = CURRENCY_FORMAT.parse(amount).doubleValue();
      return BigDecimal.valueOf(val);
    } catch (final ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
