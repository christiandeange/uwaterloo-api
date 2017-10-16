package com.deange.uwaterlooapi.model.watcard;

import com.deange.uwaterlooapi.model.AbstractModel;
import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

@AutoValue
public abstract class Transaction
    extends AbstractModel {

  public abstract TransactionDate date();

  public abstract BigDecimal amount();

  public abstract Type type();

  public abstract Vendor vendor();

  public static Builder builder() {
    return new AutoValue_Transaction.Builder();
  }

  @AutoValue.Builder
  public static abstract class Builder {
    public abstract Builder setDate(final TransactionDate date);

    public abstract Builder setAmount(final BigDecimal amount);

    public abstract Builder setType(final Type type);

    public abstract Builder setVendor(final Vendor vendor);

    public abstract Transaction build();
  }

  @AutoValue
  public static abstract class Type
      extends AbstractModel {
    public abstract int code();

    public abstract String category();

    public static Type create(final int code, final String category) {
      return new AutoValue_Transaction_Type(code, category);
    }
  }

  @AutoValue
  public static abstract class Vendor
      extends AbstractModel {
    public abstract int code();

    public abstract String category();

    public static Vendor create(final int code, final String category) {
      return new AutoValue_Transaction_Vendor(code, category);
    }
  }

}
