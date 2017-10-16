package com.deange.uwaterlooapi.model.courses;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CourseLocations
    extends BaseModel
    implements
    Parcelable {

  public static final String ONLINE = "online";
  public static final String ONLINE_ONLY = "online_only";
  public static final String ST_JEROME = "st_jerome";
  public static final String ST_JEROME_ONLY = "st_jerome_only";
  public static final String RENISON = "renison";
  public static final String RENISON_ONLY = "renison_only";
  public static final String CONGRAD_GREBEL = "conrad_grebel";
  public static final String CONGRAD_GREBEL_ONLY = "conrad_grebel_only";

  private static final List<String> OFFERED_PLACES;

  static {
    final List<String> places = new ArrayList<>();
    places.add(ONLINE);
    places.add(ONLINE_ONLY);
    places.add(ST_JEROME);
    places.add(ST_JEROME_ONLY);
    places.add(RENISON);
    places.add(RENISON_ONLY);
    places.add(CONGRAD_GREBEL);
    places.add(CONGRAD_GREBEL_ONLY);
    OFFERED_PLACES = Collections.unmodifiableList(places);
  }

  int mBitset;

  /* package */ CourseLocations() {
    mBitset = 0;
  }

  protected CourseLocations(final Parcel in) {
    super(in);
    mBitset = in.readInt();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mBitset);
  }

  public static final Creator<CourseLocations> CREATOR = new Creator<CourseLocations>() {
    @Override
    public CourseLocations createFromParcel(final Parcel in) {
      return new CourseLocations(in);
    }

    @Override
    public CourseLocations[] newArray(final int size) {
      return new CourseLocations[size];
    }
  };

  /**
   * Test for where a course is offered.
   * <p/>
   * Must be one of the constants defined in {@link CourseLocations}.
   *
   * @see #ONLINE
   * @see #ONLINE_ONLY
   * @see #ST_JEROME
   * @see #ST_JEROME_ONLY
   * @see #RENISON
   * @see #RENISON_ONLY
   * @see #CONGRAD_GREBEL
   * @see #CONGRAD_GREBEL_ONLY
   */
    /* package */ boolean isOfferedAt(final String offering) {
    if (!OFFERED_PLACES.contains(offering)) {
      throw new IllegalArgumentException("Invalid \'offering\' location. See this method's " +
                                             "javadoc for valid values.");
    }
    return (mBitset & bit(OFFERED_PLACES.indexOf(offering))) != 0;
  }

  private static int bit(int x) {
    return 1 << x;
  }

  private void set(final String place, final boolean offered) {
    final int bit = bit(OFFERED_PLACES.indexOf(place));
    mBitset = (offered) ? (mBitset | bit) : (mBitset & ~bit);
  }

  public static final class Converter implements JsonDeserializer {

    @Override
    public CourseLocations deserialize(
        final JsonElement json, final Type typeOfT,
        final JsonDeserializationContext context) {

      final CourseLocations locations = new CourseLocations();
      for (final Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
        locations.set(entry.getKey(), entry.getValue().getAsBoolean());
      }

      return locations;
    }
  }

}
