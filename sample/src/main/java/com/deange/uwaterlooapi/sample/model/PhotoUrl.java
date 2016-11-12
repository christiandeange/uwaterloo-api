package com.deange.uwaterlooapi.sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PhotoUrl {

    public static final String PHOTO_PAGE = "photopage";

    @SerializedName("type")
    String mType;

    @SerializedName("_content")
    String mUrl;

    public String getType() {
        return mType;
    }

    public String getUrl() {
        return mUrl;
    }

    /* package */ static class UrlList {
        @SerializedName("url")
        List<PhotoUrl> mUrls;

        public List<PhotoUrl> getUrls() {
            return (mUrls == null) ? new ArrayList<>() : mUrls;
        }
    }

}
