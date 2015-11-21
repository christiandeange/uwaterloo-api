package com.deange.uwaterlooapi.sample.model;

import com.google.gson.annotations.SerializedName;

public class PhotoTitle {

    @SerializedName("_content")
    String mContent;

    public String getContent() {
        return mContent;
    }

    @Override
    public String toString() {
        return mContent;
    }
}
