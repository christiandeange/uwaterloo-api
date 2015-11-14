package com.deange.uwaterlooapi.model.events;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.DateRange;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class Event extends BaseModel
        implements Comparable<Event> {

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

    @Override
    public int compareTo(final Event another) {
        final Date now = new Date();
        return getNext(mTimes, now).compareTo(getNext(another.mTimes, now));
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
