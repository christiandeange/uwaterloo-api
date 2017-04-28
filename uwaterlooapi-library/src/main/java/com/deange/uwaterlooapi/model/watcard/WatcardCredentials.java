package com.deange.uwaterlooapi.model.watcard;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class WatcardCredentials
        implements
        Parcelable {

    public static WatcardCredentials create(final String studentNumber, final String pin) {
        return new AutoValue_WatcardCredentials(studentNumber, pin);
    }

    public abstract String studentNumber();
    public abstract String pin();
}
