package com.deange.uwaterlooapi.model;

import com.google.gson.annotations.SerializedName;

public class Metadata {

    /**
     *  "meta":{
     *      "requests":136,
     *      "timestamp":1381961484,
     *      "status":200,
     *      "message":"Request successful",
     *      "method_id":1291,
     *      "version":2.07,
     *      "method":{
     *          <p/>
     *      }
     *  }
     */

    public static final String REQUESTS = "requests";
    public static final String TIMESTAMP = "timestamp";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String METHOD_ID = "method_id";
    public static final String VERSION = "version";

    @SerializedName(REQUESTS)
    private int mRequests;

    @SerializedName(TIMESTAMP)
    private long mTimestamp;

    @SerializedName(STATUS)
    private int mStatus;

    @SerializedName(MESSAGE)
    private String mMessage;

    @SerializedName(METHOD_ID)
    private int mMethodId;

    @SerializedName(VERSION)
    private String mVersion;

    public int getRequests() {
        return mRequests;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public int getStatus() {
        return mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    public int getMethodId() {
        return mMethodId;
    }

    public String getVersion() {
        return mVersion;
    }
}
