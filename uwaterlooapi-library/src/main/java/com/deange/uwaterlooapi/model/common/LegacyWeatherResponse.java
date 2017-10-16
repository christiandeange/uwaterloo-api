package com.deange.uwaterlooapi.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.weather.LegacyWeatherReading;

public class LegacyWeatherResponse
    extends SimpleResponse<LegacyWeatherReading>
    implements
    Parcelable {

  public LegacyWeatherResponse(final LegacyWeatherReading data) {
    super(data);
  }

  protected LegacyWeatherResponse(final Parcel in) {
    super(in);
  }

  public static final Creator<LegacyWeatherResponse> CREATOR =
      new Creator<LegacyWeatherResponse>() {
        @Override
        public LegacyWeatherResponse createFromParcel(final Parcel in) {
          return new LegacyWeatherResponse(in);
        }

        @Override
        public LegacyWeatherResponse[] newArray(final int size) {
          return new LegacyWeatherResponse[size];
        }
      };
}
