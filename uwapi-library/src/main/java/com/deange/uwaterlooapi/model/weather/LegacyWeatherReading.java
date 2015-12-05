package com.deange.uwaterlooapi.model.weather;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;

import org.parceler.Parcel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Parcel
@Root(name = "current_observation")
public class LegacyWeatherReading extends BaseModel {

    public static final String PRESSURE_STEADY = "steady";
    public static final String PRESSURE_RISING = "rising";
    public static final String PRESSURE_FALLING = "falling";

    @Element(name = "credit")
    String mCredit;

    @Element(name = "suggested_pickup")
    String mUpdateTimes;

    @Element(name = "location")
    String mLocation;

    @Element(name = "latitude")
    String mLatitudeRaw;

    @Element(name = "longitude")
    String mLongitudeRaw;

    @Element(name = "elevation")
    String mElevationRaw;

    @Element(name = "observation_year")
    int mObservationYear;

    @Element(name = "observation_month_text")
    String mObservationMonth;

    @Element(name = "observation_day")
    int mObservationDay;

    @Element(name = "observation_hour")
    int mObservationHour;

    @Element(name = "observation_minute")
    int mObservationMinute;

    @Element(name = "precipitation_15minutes_mm")
    float mPrecipitation15Min;

    @Element(name = "precipitation_1hr_mm")
    float mPrecipitation1Hr;

    @Element(name = "precipitation_24hr_mm")
    float mPrecipitation24Hr;

    @Element(name = "temperature_current_C")
    float mTemperature;

    @Element(name = "temperature_24hrmax_C")
    float mTemperature24hMax;

    @Element(name = "temperature_24hrmin_C")
    float mTemperature24hMin;

    @Element(name = "incoming_shortwave_radiation_WM2")
    float mIncomingShortWaveRadiation;

    @Element(name = "reflected_shortwave_radiation_WM2")
    float mReflectedShortWaveRadiation;

    @Element(name = "wind_speed_kph")
    float mWindSpeed;

    @Element(name = "wind_gust_kph")
    float mWindGust;

    @Element(name = "wind_direction")
    String mWindDirection;

    @Element(name = "relative_humidity_percent")
    float mRelativeHumidity;

    @Element(name = "dew_point_C")
    float mDewPoint;

    @Element(name = "pressure_kpa")
    float mPressureKpa;

    @Element(name = "pressure_trend")
    String mPressureTrend;

    @Element(name = "humidex_C")
    String mHumidexRaw;

    @Element(name = "windchill_C")
    String mWindchillRaw;

    Float mLatitude;
    Float mLongitude;
    Float mElevation;
    String mObservationTime;
    Float mHumidex;
    Float mWindchill;

    /**
     * Attribution to original source provider
     */
    public String getCredit() {
        return mCredit;
    }

    /**
     * How often this data is refreshed from the original source
     */
    public String getUpdateTimes() {
        return mUpdateTimes;
    }

    /**
     * Get the name of the original source
     */
    public String getSource() {
        return mLocation;
    }

    /**
     * Station's latitude + longitude
     */
    public float[] getLocation() {
        if (mLatitude == null || mLongitude == null) {
            mLatitude = dmsToDecimal(mLatitudeRaw);
            mLongitude = dmsToDecimal(mLongitudeRaw);
        }

        return new float[] { mLatitude, mLongitude };
    }

    /**
     * Station elevation in meters
     */
    public float getElevation() {
        if (mElevation == null) {
            final String trimmed = mElevationRaw.substring(0, mElevationRaw.indexOf(' ') - 1);
            mElevation = Float.parseFloat(trimmed);
        }
        return mElevation;
    }

    /**
     * ISO 8601 timestamp of weather recordings
     */
    public Date getObservationTime() {
        return Formatter.parseDate(getRawObservationTime());
    }

    /**
     * ISO 8601 timestamp of weather recordings as a string
     */
    public String getRawObservationTime() {
        if (mObservationTime == null) {
            // "yyyy-MM-ddThh:mm:ss-E[SD]T"
            final TimeZone timezone = TimeZone.getTimeZone("America/New_York");
            final String timezoneId = timezone.getDisplayName(timezone.inDaylightTime(new Date()), TimeZone.SHORT);
            mObservationTime =
                    mObservationYear
                    + "-"
                    + String.format("%02d", Formatter.MONTHS.indexOf(mObservationMonth) + 1)
                    + "-"
                    + String.format("%02d", mObservationDay)
                    + "T"
                    + String.format("%02d", mObservationHour)
                    + ":"
                    + String.format("%02d", mObservationMinute)
                    + ":"
                    + String.format("%02d", 0) // no seconds
                    + timezoneId;
        }
        return mObservationTime;
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
     * Current temperature in celsius
     */
    public float getTemperature() {
        return mTemperature;
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
     * Incoming radiation in watts per meter square
     */
    public float getIncomingShortWaveRadiation() {
        return mIncomingShortWaveRadiation;
    }

    /**
     * Reflected radiation in watts per meter square
     */
    public float getReflectedShortWaveRadiation() {
        return mReflectedShortWaveRadiation;
    }

    /**
     * Wind speed in km per hour
     */
    public float getWindSpeed() {
        return mWindSpeed;
    }

    /**
     * Wind guest in km per hour
     */
    public float getWindGust() {
        return mWindGust;
    }

    /**
     * Wind direction in cardinal
     */
    public String getWindDirection() {
        return mWindDirection;
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
     * Humidex temperature in celsius
     */
    public float getHumidex() {
        if (mHumidex == null) {
            try {
                mHumidex = Float.parseFloat(mHumidexRaw);
            } catch (final NumberFormatException ignored) {
                mHumidex = 0f;
            }
        }
        return mHumidex;
    }

    /**
     * Windchill in celsius
     */
    public float getWindchill() {
        if (mWindchill == null) {
            try {
                mWindchill = Float.parseFloat(mWindchillRaw);
            } catch (final NumberFormatException ignored) {
                mWindchill = 0f;
            }
        }
        return mWindchill;
    }

    private static float dmsToDecimal(final String dms) {
        final Pattern format = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+) (.)");
        final Matcher matcher = format.matcher(dms);

        if (!matcher.find()) {
            return 0;
        }

        final int d = Integer.parseInt(matcher.group(1));
        final int m = Integer.parseInt(matcher.group(2));
        final int s = Integer.parseInt(matcher.group(3));
        final char hemisphere = matcher.group(4).charAt(0);

        final float sign = (hemisphere == 'W' || hemisphere == 'S') ? -1 : 1;

        return sign * ((d) + (m / 60f) + (s / 3600f));
    }
}
