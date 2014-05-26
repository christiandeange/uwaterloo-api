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

    @SerializedName("requests")
    private int mRequests;

    @SerializedName("timestamp")
    private long mTimestamp;

    @SerializedName("status")
    private int mStatus;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("method_id")
    private int mMethodId;

    @SerializedName("version")
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
