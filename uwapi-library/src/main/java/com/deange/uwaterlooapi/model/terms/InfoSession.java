package com.deange.uwaterlooapi.model.terms;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class InfoSession extends BaseModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("employer")
    private String mEmployer;

    @SerializedName("date")
    private String mDate;

    @SerializedName("start_time")
    private String mStartTime;

    @SerializedName("end_time")
    private String mEndTime;

    @SerializedName("location")
    private String mLocation;

    @SerializedName("website")
    private String mWebsite;

    @SerializedName("audience")
    private String mAudience;

    @SerializedName("programs")
    private String mPrograms;

    @SerializedName("description")
    private String mDescription;

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
