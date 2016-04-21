package com.deange.uwaterlooapi.model.poi;

import com.google.gson.annotations.SerializedName;

public class Photosphere extends BasicPointOfInterest {

    @SerializedName("url")
    String mUrl;

    /**
     * The URL of the photosphere
     */
    public String getUrl() {
        return mUrl;
    }
}
