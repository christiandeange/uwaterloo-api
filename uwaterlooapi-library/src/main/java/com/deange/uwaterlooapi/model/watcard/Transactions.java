package com.deange.uwaterlooapi.model.watcard;

import com.deange.uwaterlooapi.model.AbstractModel;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class Transactions
    extends AbstractModel {

  public abstract List<Transaction> transactions();

  public static Builder builder() {
    return new AutoValue_Transactions.Builder()
        .setTransactions(new ArrayList<>());
  }

  @AutoValue.Builder
  public static abstract class Builder {
    public abstract Builder setTransactions(final List<Transaction> transaction);

    /* package */
    abstract List<Transaction> transactions();

    public Builder addTransaction(final Transaction transaction) {
      transactions().add(transaction);
      return this;
    }

    public abstract Transactions build();
  }

}
