package com.deange.uwaterlooapi.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Metadata {

    @SerializedName("requests")
    int mRequests;

    @SerializedName("timestamp")
    long mTimestamp;

    @SerializedName("status")
    int mStatus;

    @SerializedName("message")
    String mMessage;

    @SerializedName("method_id")
    int mMethodId;

    @SerializedName("version")
    String mVersion;

    /**
     * The number of times this method has been called from this API key
     */
    public int getRequests() {
        return mRequests;
    }

    /**
     * Current server time
     */
    public long getTimestamp() {
        return mTimestamp;
    }

    /**
     * HTTP/1.1 response code, as per RFC 2616
     */
    public int getStatus() {
        return mStatus;
    }

    /**
     * The response string from the server
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * The ID of the method called
     */
    public int getMethodId() {
        return mMethodId;
    }

    /**
     * The current version of this API
     */
    public String getVersion() {
        return mVersion;
    }
}
