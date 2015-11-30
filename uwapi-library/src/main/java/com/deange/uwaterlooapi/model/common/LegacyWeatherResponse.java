package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.model.weather.LegacyWeatherReading;

public class LegacyWeatherResponse extends BaseResponse {

    private final LegacyWeatherReading mReading;

    public LegacyWeatherResponse(final LegacyWeatherReading reading) {
        mReading = reading;
    }

    @Override
    public LegacyWeatherReading getData() {
        return mReading;
    }
}
