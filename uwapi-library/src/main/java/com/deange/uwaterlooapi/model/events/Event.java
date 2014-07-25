package com.deange.uwaterlooapi.model.events;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.DateRange;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Event extends BaseModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("site")
    private String mSite;

    @SerializedName("site_name")
    private String mSiteName;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("times")
    private List<DateRange> mTimes;

    @SerializedName("type")
    private List<String> mTypes;

    @SerializedName("link")
    private String mUrl;

    @SerializedName("updated")
    private String mUpdated;

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
        return CollectionUtils.applyPolicy(mTimes);
    }

    /**
     * Types of the event
     */
    public List<String> getTypes() {
        return CollectionUtils.applyPolicy(mTypes);
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
        return Formatter.parseDate(mUpdated);
    }

    /**
     * ISO 8601 formatted updated date as a string
     */
    public String getRawLastUpdatedDate() {
        return mUpdated;
    }
}
