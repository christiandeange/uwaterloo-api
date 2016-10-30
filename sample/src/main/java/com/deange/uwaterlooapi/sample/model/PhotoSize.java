package com.deange.uwaterlooapi.sample.model;

import com.google.gson.annotations.SerializedName;

public class PhotoSize {

    @SerializedName("label")
    String mLabel;

    @SerializedName("width")
    int mWidth;

    @SerializedName("height")
    int mHeight;

    @SerializedName("source")
    String mSource;

    @SerializedName("url")
    String mUrl;

    @SerializedName("media")
    String mMedia;

    public String getLabel() {
        return mLabel;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public String getSource() {
        return mSource;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getMedia() {
        return mMedia;
    }
}
