package com.deange.uwaterlooapi.model.watcard;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class WatcardCredentials
    implements
    Parcelable {

  public static WatcardCredentials create(final String studentNumber, final String pin) {
    return new AutoValue_WatcardCredentials(studentNumber, pin);
  }

  public abstract String studentNumber();

  public abstract String pin();

  public static TypeAdapter<WatcardCredentials> typeAdapter(final Gson gson) {
    return new AutoValue_WatcardCredentials.GsonTypeAdapter(gson);
  }

}
