package com.deange.uwaterlooapi.sample.net;

import retrofit.http.GET;
import retrofit.http.Query;

public interface FlickrInterface {

    @GET("/?method=flickr.photos.getInfo")
    Contract.Photo getPhotoDetails(@Query("photo_id") final String photoId);

    @GET("/?method=flickr.photos.getSizes")
    Contract.Size getPhotoSizes(@Query("photo_id") final String photoId);

}
