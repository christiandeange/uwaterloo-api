package com.deange.uwaterlooapi.model.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.Image;
import com.deange.uwaterlooapi.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class NewsArticle
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
  String mHtmlDescription;

  @SerializedName("audience")
  List<String> mAudience;

  @SerializedName("image")
  Image mImage;

  @SerializedName("site_id")
  String mSiteId;

  @SerializedName("site_name")
  String mSiteName;

  @SerializedName("revision_id")
  int mRevision;

  @SerializedName("published")
  String mPublished;

  @SerializedName("updated")
  String mUpdated;

  @SerializedName("link")
  String mLink;

  protected NewsArticle(final Parcel in) {
    super(in);
    mId = in.readInt();
    mTitle = in.readString();
    mDescription = in.readString();
    mHtmlDescription = in.readString();
    mAudience = in.createStringArrayList();
    mImage = in.readParcelable(Image.class.getClassLoader());
    mSiteId = in.readString();
    mSiteName = in.readString();
    mRevision = in.readInt();
    mPublished = in.readString();
    mUpdated = in.readString();
    mLink = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mTitle);
    dest.writeString(mDescription);
    dest.writeString(mHtmlDescription);
    dest.writeStringList(mAudience);
    dest.writeParcelable(mImage, flags);
    dest.writeString(mSiteId);
    dest.writeString(mSiteName);
    dest.writeInt(mRevision);
    dest.writeString(mPublished);
    dest.writeString(mUpdated);
    dest.writeString(mLink);
  }

  public static final Creator<NewsArticle> CREATOR = new Creator<NewsArticle>() {
    @Override
    public NewsArticle createFromParcel(final Parcel in) {
      return new NewsArticle(in);
    }

    @Override
    public NewsArticle[] newArray(final int size) {
      return new NewsArticle[size];
    }
  };

  /**
   * Unique news id
   */
  public int getId() {
    return mId;
  }

  /**
   * News title
   */
  public String getTitle() {
    return mTitle;
  }

  /**
   * News body
   */
  public String getDescription() {
    return mDescription;
  }

  /**
   * Raw news body (includes HTML markup)
   */
  public String getHtmlDescription() {
    return mHtmlDescription;
  }

  /**
   * Audience targeted by news item
   */
  public List<String> getAudience() {
    return mAudience;
  }

  /**
   * Image representing the news item
   */
  public Image getImage() {
    return mImage;
  }

  /**
   * Site slug as from https://api.uwaterloo.ca/v2/resources/sites.json
   */
  public String getSiteId() {
    return mSiteId;
  }

  /**
   * Full site name as from https://api.uwaterloo.ca/v2/resources/sites.json
   */
  public String getSiteName() {
    return mSiteName;
  }

  /**
   * Unique id of revision of news item
   */
  public int getRevision() {
    return mRevision;
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

}
