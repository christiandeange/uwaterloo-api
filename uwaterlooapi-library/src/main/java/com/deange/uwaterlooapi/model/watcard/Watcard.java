package com.deange.uwaterlooapi.model.watcard;

import com.deange.uwaterlooapi.model.AutoBaseModel;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Watcard
        extends AutoBaseModel {

    public abstract List<Row> accounts();
    public abstract Number total();

    public static Builder builder() {
        return new AutoValue_Watcard.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setAccounts(final List<Row> accounts);
        public abstract Builder setTotal(final Number total);
        public abstract Watcard build();
    }

    @AutoValue
    public static abstract class Row {
        public abstract String account();
        public abstract Number limit();
        public abstract Number amount();

        public static Row create(final String account, final Number limit, final Number amount) {
            return new AutoValue_Watcard_Row(account, limit, amount);
        }
    }

}
