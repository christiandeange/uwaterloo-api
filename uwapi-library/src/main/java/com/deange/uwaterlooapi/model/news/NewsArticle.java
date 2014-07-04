package com.deange.uwaterlooapi.model.news;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.Image;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class NewsArticle extends BaseModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("description_raw")
    private String mHtmlDescription;

    @SerializedName("audience")
    private List<String> mAudience;

    @SerializedName("image")
    private Image mImage;

    @SerializedName("site_id")
    private String mSiteId;

    @SerializedName("site_name")
    private String mSiteName;

    @SerializedName("revision_id")
    private int mRevision;

    @SerializedName("published")
    private String mPublished;

    @SerializedName("updated")
    private String mUpdated;

    @SerializedName("link")
    private String mLink;

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
        return Formatter.parseDate(mPublished);
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
        return Formatter.parseDate(mUpdated);
    }

    /**
     * ISO 8601 formatted update date as a string
     */
    public String getRawUpdatedDate() {
        return mUpdated;
    }

}
