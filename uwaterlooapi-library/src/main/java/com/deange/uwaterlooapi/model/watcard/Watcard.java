package com.deange.uwaterlooapi.model.watcard;

import com.deange.uwaterlooapi.model.AbstractModel;
import com.google.auto.value.AutoValue;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@AutoValue
public abstract class Watcard
    extends AbstractModel {

  public abstract List<Row> accounts();

  public abstract BigDecimal total();

  public String totalFormatted() {
    return NumberFormat.getCurrencyInstance(Locale.CANADA).format(total());
  }

  public static Builder builder() {
    return new AutoValue_Watcard.Builder();
  }

  @AutoValue.Builder
  public static abstract class Builder {
    public abstract Builder setAccounts(final List<Row> accounts);

    public abstract Builder setTotal(final BigDecimal total);

    public abstract Watcard build();
  }

  @AutoValue
  public static abstract class Row
      extends AbstractModel {
    public abstract String account();

    public abstract BigDecimal limit();

    public abstract BigDecimal amount();

    public String limitFormatted() {
      return NumberFormat.getCurrencyInstance(Locale.CANADA).format(limit());
    }

    public String amountFormatted() {
      return NumberFormat.getCurrencyInstance(Locale.CANADA).format(amount());
    }

    public static Row create(
        final String account,
        final BigDecimal limit,
        final BigDecimal amount) {
      return new AutoValue_Watcard_Row(account, limit, amount);
    }
  }

}
