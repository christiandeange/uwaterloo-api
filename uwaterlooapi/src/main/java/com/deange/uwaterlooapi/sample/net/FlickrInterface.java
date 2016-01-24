package com.deange.uwaterlooapi.sample.net;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FlickrInterface {

    @GET("/?method=flickr.photos.getInfo")
    void getPhotoDetails(@Query("photo_id") final String photoId, Callback<Contract.Photo> callback);

    @GET("/?method=flickr.photos.getSizes")
    void getPhotoSizes(@Query("photo_id") final String photoId, Callback<Contract.Size> callback);

}
