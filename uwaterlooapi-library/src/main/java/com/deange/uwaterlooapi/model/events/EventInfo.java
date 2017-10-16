package com.deange.uwaterlooapi.model.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.Image;
import com.deange.uwaterlooapi.model.common.MultidayDateRange;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class EventInfo
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("id")
  int mId;

  @SerializedName("title")
  String mTitle;

  @SerializedName("description")
  String mDescription;

  @SerializedName("description_raw")
  String mDescriptionRaw;

  @SerializedName("times")
  List<MultidayDateRange> mTimes;

  @SerializedName("cost")
  String mCost;

  @SerializedName("audience")
  List<String> mAudience;

  @SerializedName("tags")
  List<String> mTags;

  @SerializedName("type")
  List<String> mTypes;

  @SerializedName("website")
  Website mSite;

  @SerializedName("host")
  Website mHost;

  @SerializedName("image")
  Image mImage;

  @SerializedName("location")
  EventLocation mLocation;

  @SerializedName("site_name")
  String mSiteName;

  @SerializedName("site_id")
  String mSiteId;

  @SerializedName("revision_id")
  int mRevisionId;

  @SerializedName("link")
  String mLink;

  @SerializedName("link_calendar")
  String mLinkCalendar;

  @SerializedName("updated")
  String mUpdated;

  protected EventInfo(final Parcel in) {
    super(in);
    mId = in.readInt();
    mTitle = in.readString();
    mDescription = in.readString();
    mDescriptionRaw = in.readString();
    mTimes = in.createTypedArrayList(MultidayDateRange.CREATOR);
    mCost = in.readString();
    mAudience = in.createStringArrayList();
    mTags = in.createStringArrayList();
    mTypes = in.createStringArrayList();
    mSite = in.readParcelable(Website.class.getClassLoader());
    mHost = in.readParcelable(Website.class.getClassLoader());
    mImage = in.readParcelable(Image.class.getClassLoader());
    mLocation = in.readParcelable(EventLocation.class.getClassLoader());
    mSiteName = in.readString();
    mSiteId = in.readString();
    mRevisionId = in.readInt();
    mLink = in.readString();
    mLinkCalendar = in.readString();
    mUpdated = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mTitle);
    dest.writeString(mDescription);
    dest.writeString(mDescriptionRaw);
    dest.writeTypedList(mTimes);
    dest.writeString(mCost);
    dest.writeStringList(mAudience);
    dest.writeStringList(mTags);
    dest.writeStringList(mTypes);
    dest.writeParcelable(mSite, flags);
    dest.writeParcelable(mHost, flags);
    dest.writeParcelable(mImage, flags);
    dest.writeParcelable(mLocation, flags);
    dest.writeString(mSiteName);
    dest.writeString(mSiteId);
    dest.writeInt(mRevisionId);
    dest.writeString(mLink);
    dest.writeString(mLinkCalendar);
    dest.writeString(mUpdated);
  }

  public static final Creator<EventInfo> CREATOR = new Creator<EventInfo>() {
    @Override
    public EventInfo createFromParcel(final Parcel in) {
      return new EventInfo(in);
    }

    @Override
    public EventInfo[] newArray(final int size) {
      return new EventInfo[size];
    }
  };

  /**
   * Unique event id
   */
  public int getId() {
    return mId;
  }

  /**
   * Event title
   */
  public String getTitle() {
    return mTitle;
  }

  /**
   * Event description
   */
  public String getDescription() {
    return mDescription;
  }

  /**
   * Raw event description (includes HTML markup)
   */
  public String getDescriptionRaw() {
    return mDescriptionRaw;
  }

  /**
   * The event's times
   */
  public List<MultidayDateRange> getTimes() {
    return mTimes;
  }

  /**
   * Cost of event
   */
  public String getCost() {
    return mCost;
  }

  /**
   * Audience targeted by event
   */
  public List<String> getAudience() {
    return mAudience;
  }

  /**
   * Tags related to event
   */
  public List<String> getTags() {
    return mTags;
  }

  /**
   * Type of event
   */
  public List<String> getTypes() {
    return mTypes;
  }

  /**
   * The event's website for more information
   */
  public Website getSite() {
    return mSite;
  }

  /**
   * The event's host's website for more information
   */
  public Website getHost() {
    return mHost;
  }

  /**
   * Image representing the event
   */
  public Image getImage() {
    return mImage;
  }

  /**
   * Location of the event
   */
  public EventLocation getLocation() {
    return mLocation;
  }

  /**
   * Full site name as from https://api.uwaterloo.ca/v2/resources/sites.json
   */
  public String getSiteName() {
    return mSiteName;
  }

  /**
   * Site slug as from https://api.uwaterloo.ca/v2/resources/sites.json
   */
  public String getSiteId() {
    return mSiteId;
  }

  /**
   * Unique id of revision of event
   */
  public int getRevisionId() {
    return mRevisionId;
  }

  /**
   * URL of event link
   */
  public String getLink() {
    return mLink;
  }

  /**
   * iCal feed of event
   */
  public String getLinkCalendar() {
    return mLinkCalendar;
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
}
