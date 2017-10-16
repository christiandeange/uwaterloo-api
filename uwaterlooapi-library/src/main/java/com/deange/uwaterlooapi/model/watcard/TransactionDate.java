package com.deange.uwaterlooapi.model.watcard;

import com.deange.uwaterlooapi.model.AbstractModel;
import com.google.auto.value.AutoValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@AutoValue
public abstract class TransactionDate
    extends AbstractModel {

  private static final DateFormat FORMAT_PARSE = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
                                                                      Locale.CANADA);
  private static final DateFormat FORMAT_PRINT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
                                                                      Locale.CANADA);

  public abstract Date date();

  public static TransactionDate create(final Date date) {
    return new AutoValue_TransactionDate(date);
  }

  @Override
  public String toString() {
    return FORMAT_PRINT.format(date());
  }

  public static TransactionDate create(final String rawDate) {
    try {
      return new AutoValue_TransactionDate(FORMAT_PARSE.parse(rawDate));
    } catch (final ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
