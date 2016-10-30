package com.deange.uwaterlooapi.model.events;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Website extends BaseModel {

    @SerializedName("title")
    String mTitle;

    @SerializedName("url")
    String mUrl;

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

}
