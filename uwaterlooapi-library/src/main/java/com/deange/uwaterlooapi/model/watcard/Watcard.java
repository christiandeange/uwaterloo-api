package com.deange.uwaterlooapi.model.watcard;

import com.deange.uwaterlooapi.model.AutoBaseModel;
import com.google.auto.value.AutoValue;

import java.math.BigDecimal;
import java.util.List;

@AutoValue
public abstract class Watcard
        extends AutoBaseModel {

    public abstract List<Row> accounts();
    public abstract BigDecimal total();

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
            extends AutoBaseModel {
        public abstract String account();
        public abstract BigDecimal limit();
        public abstract BigDecimal amount();

        public static Row create(
                final String account,
                final BigDecimal limit,
                final BigDecimal amount) {
            return new AutoValue_Watcard_Row(account, limit, amount);
        }
    }

}
