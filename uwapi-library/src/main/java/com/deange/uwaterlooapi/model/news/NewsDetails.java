package com.deange.uwaterlooapi.model.news;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NewsDetails extends BaseModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("site")
    private String mSite;

    @SerializedName("link")
    private String mLink;

    @SerializedName("published")
    private String mPublished;

    @SerializedName("updated")
    private String mUpdated;

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
