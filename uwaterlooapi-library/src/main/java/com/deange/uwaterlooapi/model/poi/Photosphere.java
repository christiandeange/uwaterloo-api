package com.deange.uwaterlooapi.model.poi;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
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
