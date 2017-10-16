package com.deange.uwaterlooapi.model.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.DateRange;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Event
    extends BaseModel
    implements
    Parcelable,
    Comparable<Event> {

  @SerializedName("id")
  int mId;

  @SerializedName("site")
  String mSite;

  @SerializedName("site_name")
  String mSiteName;

  @SerializedName("title")
  String mTitle;

  @SerializedName("times")
  List<DateRange> mTimes;

  @SerializedName("type")
  List<String> mTypes;

  @SerializedName("link")
  String mUrl;

  @SerializedName("updated")
  String mUpdated;

  protected Event(final Parcel in) {
    super(in);
    mId = in.readInt();
    mSite = in.readString();
    mSiteName = in.readString();
    mTitle = in.readString();
    mTimes = in.createTypedArrayList(DateRange.CREATOR);
    mTypes = in.createStringArrayList();
    mUrl = in.readString();
    mUpdated = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mSite);
    dest.writeString(mSiteName);
    dest.writeString(mTitle);
    dest.writeTypedList(mTimes);
    dest.writeStringList(mTypes);
    dest.writeString(mUrl);
    dest.writeString(mUpdated);
  }

  public static final Creator<Event> CREATOR = new Creator<Event>() {
    @Override
    public Event createFromParcel(final Parcel in) {
      return new Event(in);
    }

    @Override
    public Event[] newArray(final int size) {
      return new Event[size];
    }
  };

  /**
   * Unique event id
   */
  public int getId() {
    return mId;
  }

  /**
   * Site slug from /resources/sites
   */
  public String getSite() {
    return mSite;
  }

  /**
   * Full site name as from https://api.uwaterloo.ca/v2/resources/sites.json
   */
  public String getSiteName() {
    return mSiteName;
  }

  /**
   * Event title
   */
  public String getTitle() {
    return mTitle;
  }

  /**
   * The event's times
   */
  public List<DateRange> getTimes() {
    return mTimes;
  }

  /**
   * Types of the event
   */
  public List<String> getTypes() {
    return mTypes;
  }

  /**
   * URL of event link
   */
  public String getUrl() {
    return mUrl;
  }

  /**
   * ISO 8601 formatted updated date
   */
  public Date getLastUpdatedDate() {
    return DateUtils.parseDate(mUpdated);
  }

  /**
   * ISO 8601 formatted updated date as a string
   */
  public String getRawLastUpdatedDate() {
    return mUpdated;
  }

  @Override
  public int compareTo(final Event another) {
    final Date now = new Date();
    return getNext(mTimes, now).compareTo(getNext(another.mTimes, now));
  }

  public static Date getNext(final List<DateRange> ranges) {
    return getNext(ranges, new Date());
  }

  public static Date getNext(final List<DateRange> ranges, final Date now) {
    if (ranges == null || ranges.isEmpty()) {
      return new Date(0);
    }

    // Get the next one after now
    Date earliest = null;
    for (int i = 0; i < ranges.size(); i++) {
      Date compare = ranges.get(i).getStart();
      if (compare.after(now) && (earliest == null || compare.before(earliest))) {
        earliest = compare;
      }
    }

    if (earliest != null) {
      return earliest;
    }

    // Get the last one at all
    Date latest = ranges.get(0).getStart();
    for (int i = 1; i < ranges.size(); i++) {
      Date compare = ranges.get(i).getStart();
      if (!compare.before(latest)) {
        latest = compare;
      }
    }

    return latest;
  }
}
