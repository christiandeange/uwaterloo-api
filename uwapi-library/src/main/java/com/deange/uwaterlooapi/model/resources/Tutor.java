package com.deange.uwaterlooapi.model.resources;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Tutor extends BaseModel {

    @SerializedName("subject")
    private String mSubject;

    @SerializedName("catalog_number")
    private String mCatalogNumber;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("tutors_count")
    private int mTutorsCount;

    @SerializedName("contact_url")
    private String mContactUrl;

    /**
     * Subject acronym
     */
    public String getSubject() {
        return mSubject;
    }

    /**
     * Course catalog number
     */
    public String getCatalogNumber() {
        return mCatalogNumber;
    }

    /**
     * Course title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Total number of tutors available for that course
     */
    public int getTutorsCount() {
        return mTutorsCount;
    }

    /**
     * Link to get tutor contact details
     */
    public String getContactUrl() {
        return mContactUrl;
    }
}
