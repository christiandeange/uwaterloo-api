package com.deange.uwaterlooapi.model.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NewsDetails
    extends BaseModel
    implements
    Parcelable,
    Comparable<NewsDetails> {

  @SerializedName("id")
  int mId;

  @SerializedName("title")
  String mTitle;

  @SerializedName("site")
  String mSite;

  @SerializedName("link")
  String mLink;

  @SerializedName("published")
  String mPublished;

  @SerializedName("updated")
  String mUpdated;

  protected NewsDetails(final Parcel in) {
    super(in);
    mId = in.readInt();
    mTitle = in.readString();
    mSite = in.readString();
    mLink = in.readString();
    mPublished = in.readString();
    mUpdated = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mTitle);
    dest.writeString(mSite);
    dest.writeString(mLink);
    dest.writeString(mPublished);
    dest.writeString(mUpdated);
  }

  public static final Creator<NewsDetails> CREATOR = new Creator<NewsDetails>() {
    @Override
    public NewsDetails createFromParcel(final Parcel in) {
      return new NewsDetails(in);
    }

    @Override
    public NewsDetails[] newArray(final int size) {
      return new NewsDetails[size];
    }
  };

  /**
   * Unique news id
   */
  public int getId() {
    return mId;
  }

  /**
   * News story title
   */
  public String getTitle() {
    return mTitle;
  }

  /**
   * News site slug
   */
  public String getSite() {
    return mSite;
  }

  /**
   * URL of news link
   */
  public String getLink() {
    return mLink;
  }

  /**
   * ISO 8601 formatted publish date
   */
  public Date getPublishedDate() {
    return DateUtils.parseDate(mPublished);
  }

  /**
   * ISO 8601 formatted publish date as a string
   */
  public String getRawPublishedDate() {
    return mPublished;
  }

  /**
   * ISO 8601 formatted update date
   */
  public Date getUpdatedDate() {
    return DateUtils.parseDate(mUpdated);
  }

  /**
   * ISO 8601 formatted update date as a string
   */
  public String getRawUpdatedDate() {
    return mUpdated;
  }

  @Override
  public int compareTo(final NewsDetails another) {
    return getPublishedDate().compareTo(another.getPublishedDate());
  }
}
