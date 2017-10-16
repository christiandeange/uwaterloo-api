package com.deange.uwaterlooapi.model.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Root(name = "current_observation")
public class LegacyWeatherReading
    extends BaseModel
    implements
    Parcelable {

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

  public LegacyWeatherReading() {
  }

  protected LegacyWeatherReading(final Parcel in) {
    super(in);
    mCredit = in.readString();
    mUpdateTimes = in.readString();
    mLocation = in.readString();
    mLatitudeRaw = in.readString();
    mLongitudeRaw = in.readString();
    mElevationRaw = in.readString();
    mObservationYear = in.readInt();
    mObservationMonth = in.readString();
    mObservationDay = in.readInt();
    mObservationHour = in.readInt();
    mObservationMinute = in.readInt();
    mPrecipitation15Min = in.readFloat();
    mPrecipitation1Hr = in.readFloat();
    mPrecipitation24Hr = in.readFloat();
    mTemperature = in.readFloat();
    mTemperature24hMax = in.readFloat();
    mTemperature24hMin = in.readFloat();
    mIncomingShortWaveRadiation = in.readFloat();
    mReflectedShortWaveRadiation = in.readFloat();
    mWindSpeed = in.readFloat();
    mWindGust = in.readFloat();
    mWindDirection = in.readString();
    mRelativeHumidity = in.readFloat();
    mDewPoint = in.readFloat();
    mPressureKpa = in.readFloat();
    mPressureTrend = in.readString();
    mHumidexRaw = in.readString();
    mWindchillRaw = in.readString();
    if (in.readByte() == 0) {
      mLatitude = null;
    } else {
      mLatitude = in.readFloat();
    }
    if (in.readByte() == 0) {
      mLongitude = null;
    } else {
      mLongitude = in.readFloat();
    }
    if (in.readByte() == 0) {
      mElevation = null;
    } else {
      mElevation = in.readFloat();
    }
    mObservationTime = in.readString();
    if (in.readByte() == 0) {
      mHumidex = null;
    } else {
      mHumidex = in.readFloat();
    }
    if (in.readByte() == 0) {
      mWindchill = null;
    } else {
      mWindchill = in.readFloat();
    }
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mCredit);
    dest.writeString(mUpdateTimes);
    dest.writeString(mLocation);
    dest.writeString(mLatitudeRaw);
    dest.writeString(mLongitudeRaw);
    dest.writeString(mElevationRaw);
    dest.writeInt(mObservationYear);
    dest.writeString(mObservationMonth);
    dest.writeInt(mObservationDay);
    dest.writeInt(mObservationHour);
    dest.writeInt(mObservationMinute);
    dest.writeFloat(mPrecipitation15Min);
    dest.writeFloat(mPrecipitation1Hr);
    dest.writeFloat(mPrecipitation24Hr);
    dest.writeFloat(mTemperature);
    dest.writeFloat(mTemperature24hMax);
    dest.writeFloat(mTemperature24hMin);
    dest.writeFloat(mIncomingShortWaveRadiation);
    dest.writeFloat(mReflectedShortWaveRadiation);
    dest.writeFloat(mWindSpeed);
    dest.writeFloat(mWindGust);
    dest.writeString(mWindDirection);
    dest.writeFloat(mRelativeHumidity);
    dest.writeFloat(mDewPoint);
    dest.writeFloat(mPressureKpa);
    dest.writeString(mPressureTrend);
    dest.writeString(mHumidexRaw);
    dest.writeString(mWindchillRaw);
    if (mLatitude == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeFloat(mLatitude);
    }
    if (mLongitude == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeFloat(mLongitude);
    }
    if (mElevation == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeFloat(mElevation);
    }
    dest.writeString(mObservationTime);
    if (mHumidex == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeFloat(mHumidex);
    }
    if (mWindchill == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeFloat(mWindchill);
    }
  }

  public static final Creator<LegacyWeatherReading> CREATOR = new Creator<LegacyWeatherReading>() {
    @Override
    public LegacyWeatherReading createFromParcel(final Parcel in) {
      return new LegacyWeatherReading(in);
    }

    @Override
    public LegacyWeatherReading[] newArray(final int size) {
      return new LegacyWeatherReading[size];
    }
  };

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

    return new float[]{mLatitude, mLongitude};
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
    return DateUtils.parseDate(getRawObservationTime());
  }

  /**
   * ISO 8601 timestamp of weather recordings as a string
   */
  public String getRawObservationTime() {
    if (mObservationTime == null) {
      // "yyyy-MM-ddThh:mm:ss-E[SD]T"
      final Locale locale = Locale.getDefault();
      final TimeZone timezone = TimeZone.getTimeZone("America/New_York");
      final String timezoneId = timezone.getDisplayName(timezone.inDaylightTime(new Date()),
                                                        TimeZone.SHORT);
      mObservationTime = String.format(locale, "%d-%02d-%02dT%02d:%02d:%02d%s",
                                       mObservationYear,
                                       DateUtils.MONTHS.indexOf(mObservationMonth) + 1,
                                       mObservationDay,
                                       mObservationHour, mObservationMinute, 0, timezoneId);
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
