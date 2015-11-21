package com.deange.uwaterlooapi.sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoDetails {
    /**
     "urls":{
     "url":[
     {
     "type":"photopage",
     "_content":"https:\/\/www.flickr.com\/photos\/manjos\/5134434028\/"
     }
     ]
     }
     */

    @SerializedName("owner")
    Author mAuthor;

    @SerializedName("title")
    PhotoTitle mTitle;

    @SerializedName("urls")
    PhotoUrl.UrlList mUrls;

    public Author getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return (mTitle == null) ? "Untitled" : mTitle.toString();
    }

    public List<PhotoUrl> getUrls() {
        return mUrls.getUrls();
    }
}
