package com.deange.uwaterlooapi.model.weather;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class WeatherReading extends BaseModel {

    @SerializedName("latitude")
    float mLatitude;

    @SerializedName("longitude")
    float mLongitude;

    @SerializedName("elevation_m")
    float mElevation;

    @SerializedName("observation_time")
    String mObservationTime;

    @SerializedName("temperature_current_c")
    float mTemperature;

    @SerializedName("humidex_c")
    float mHumidex;

    @SerializedName("windchill_c")
    float mWindchill;

    @SerializedName("temperature_24hr_max_c")
    float mTemperature24hMax;

    @SerializedName("temperature_24hr_min_c")
    float mTemperature24hMin;

    @SerializedName("precipitation_15min_mm")
    float mPrecipitation15Min;

    @SerializedName("precipitation_1hr_mm")
    float mPrecipitation1Hr;

    @SerializedName("precipitation_24hr_mm")
    float mPrecipitation24Hr;

    @SerializedName("relative_humidity_percent")
    float mRelativeHumidity;

    @SerializedName("dew_point_c")
    float mDewPoint;

    @SerializedName("wind_speed_kph")
    float mWindSpeed;

    @SerializedName("wind_direction_degrees")
    float mWindDirection;

    @SerializedName("pressure_kpa")
    float mPressureKpa;

    @SerializedName("pressure_trend")
    String mPressureTrend;

    @SerializedName("incoming_shortwave_radiation_wm2")
    float mShortWaveRadiation;

    /**
     * Station's latitude + longitude
     */
    public float[] getLocation() {
        return new float[] { mLatitude, mLongitude };
    }

    /**
     * Station elevation in meters
     */
    public float getElevation() {
        return mElevation;
    }

    /**
     * ISO 8601 timestamp of weather recordings
     */
    public Date getObservationTime() {
        return Formatter.parseDate(mObservationTime);
    }

    /**
     * ISO 8601 timestamp of weather recordings as a string
     */
    public String getRawObservationTime() {
        return mObservationTime;
    }

    /**
     * Current temperature in celsius
     */
    public float getTemperature() {
        return mTemperature;
    }

    /**
     * Humidex temperature in celsius
     */
    public float getHumidex() {
        return mHumidex;
    }

    /**
     * Windchill in celsius
     */
    public float getWindchill() {
        return mWindchill;
    }

    /**
     * 24 hour maximum temperature in celsius
     */
    public float getTemperature24hMax() {
        return mTemperature24hMax;
    }

    /**
     * 24 hour minimum temperature in celsius
     */
    public float getTemperature24hMin() {
        return mTemperature24hMin;
    }

    /**
     * Precipitation reading for 15 minute interval in mm
     */
    public float getPrecipitation15Min() {
        return mPrecipitation15Min;
    }

    /**
     * Precipitation reading for 1 hour interval in mm
     */
    public float getPrecipitation1Hr() {
        return mPrecipitation1Hr;
    }

    /**
     * Precipitation reading for every 24 hour interval in mm
     */
    public float getPrecipitation24Hr() {
        return mPrecipitation24Hr;
    }

    /**
     * Relative humidity in percentage
     */
    public float getRelativeHumidity() {
        return mRelativeHumidity;
    }

    /**
     * Dew point in celsius
     */
    public float getDewPoint() {
        return mDewPoint;
    }

    /**
     * Wind speed in km per hour
     */
    public float getWindSpeed() {
        return mWindSpeed;
    }

    /**
     * Wind direction in degrees
     */
    public float getWindDirection() {
        return mWindDirection;
    }

    /**
     * Pressure in kilopascals
     */
    public float getPressureKpa() {
        return mPressureKpa;
    }

    /**
     * Word description of the current pressure trend
     */
    public String getPressureTrend() {
        return mPressureTrend;
    }

    /**
     * Incoming radiation in watts per meter square
     */
    public float getShortWaveRadiation() {
        return mShortWaveRadiation;
    }
}
