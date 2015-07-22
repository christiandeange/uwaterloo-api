package com.deange.uwaterlooapi.model.terms;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class InfoSession extends BaseModel {

    @SerializedName("id")
    int mId;

    @SerializedName("employer")
    String mEmployer;

    @SerializedName("date")
    String mDate;

    @SerializedName("start_time")
    String mStartTime;

    @SerializedName("end_time")
    String mEndTime;

    @SerializedName("location")
    String mLocation;

    @SerializedName("website")
    String mWebsite;

    @SerializedName("audience")
    String mAudience;

    @SerializedName("programs")
    String mPrograms;

    @SerializedName("description")
    String mDescription;

    /**
     * Information session id
     */
    public int getId() {
        return mId;
    }

    /**
     * Name of employer hosting session
     */
    public String getEmployer() {
        return mEmployer;
    }

    /**
     * Date of session
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Start time of session
     */
    public String getStartTime() {
        return mStartTime;
    }

    /**
     * End time of session
     */
    public String getEndTime() {
        return mEndTime;
    }

    /**
     * Location of session
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Employer's website
     */
    public String getWebsite() {
        return mWebsite;
    }

    /**
     * Target audience of session
     */
    public String getAudience() {
        return mAudience;
    }

    /**
     * Programs of study relevant to employer
     */
    public String getPrograms() {
        return mPrograms;
    }

    /**
     * Description of employer
     */
    public String getDescription() {
        return mDescription;
    }
}
