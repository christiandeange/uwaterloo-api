package com.deange.uwaterlooapi.sample.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrInterface {

    @GET("?method=flickr.photos.getInfo")
    Call<Contract.Photo> getPhotoDetails(@Query("photo_id") final String photoId);

    @GET("?method=flickr.photos.getSizes")
    Call<Contract.Size> getPhotoSizes(@Query("photo_id") final String photoId);

}
