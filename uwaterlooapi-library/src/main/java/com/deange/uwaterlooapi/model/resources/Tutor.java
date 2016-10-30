package com.deange.uwaterlooapi.model.resources;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Tutor extends BaseModel {

    @SerializedName("subject")
    String mSubject;

    @SerializedName("catalog_number")
    String mCatalogNumber;

    @SerializedName("title")
    String mTitle;

    @SerializedName("tutors_count")
    int mTutorsCount;

    @SerializedName("contact_url")
    String mContactUrl;

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
