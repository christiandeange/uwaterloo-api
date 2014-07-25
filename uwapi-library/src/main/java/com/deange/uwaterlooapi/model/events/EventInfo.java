package com.deange.uwaterlooapi.model.events;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.Image;
import com.deange.uwaterlooapi.model.common.MultidayDateRange;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class EventInfo extends BaseModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("description_raw")
    private String mDescriptionRaw;

    @SerializedName("times")
    private List<MultidayDateRange> mTimes;

    @SerializedName("cost")
    private String mCost;

    @SerializedName("audience")
    private List<String> mAudience;

    @SerializedName("tags")
    private List<String> mTags;

    @SerializedName("type")
    private List<String> mTypes;

    @SerializedName("website")
    private Website mSite;

    @SerializedName("host")
    private Website mHost;

    @SerializedName("image")
    private Image mImage;

    @SerializedName("location")
    private EventLocation mLocation;

    @SerializedName("site_name")
    private String mSiteName;

    @SerializedName("site_id")
    private String mSiteId;

    @SerializedName("revision_id")
    private int mRevisionId;

    @SerializedName("link")
    private String mLink;

    @SerializedName("link_calendar")
    private String mLinkCalendar;

    @SerializedName("updated")
    private String mUpdated;

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
        return CollectionUtils.applyPolicy(mTimes);
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
        return CollectionUtils.applyPolicy(mAudience);
    }

    /**
     * Tags related to event
     */
    public List<String> getTags() {
        return CollectionUtils.applyPolicy(mTags);
    }

    /**
     * Type of event
     */
    public List<String> getTypes() {
        return CollectionUtils.applyPolicy(mTypes);
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
        return Formatter.parseDate(mUpdated);
    }

    /**
     * ISO 8601 formatted updated date as a string
     */
    public String getRawLastUpdatedDate() {
        return mUpdated;
    }
}
