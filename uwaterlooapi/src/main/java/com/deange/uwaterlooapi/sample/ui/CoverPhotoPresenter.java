package com.deange.uwaterlooapi.sample.ui;

import android.util.Log;

import com.deange.uwaterlooapi.sample.BuildConfig;
import com.deange.uwaterlooapi.sample.model.Photo;
import com.deange.uwaterlooapi.sample.net.Contract;
import com.deange.uwaterlooapi.sample.net.FlickrApi;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Semaphore;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public final class CoverPhotoPresenter {

    private static final String TAG = "CoverPhotoPresenter";

    private static final FlickrApi API = new FlickrApi(BuildConfig.FLICKR_API_KEY);
    private static final Random sRandom = new Random();

    public static final String[] PHOTO_IDS = new String[]{
            "10053104793",
            "10395423826",
            "121557152",
            "15024190034",
            "15127909624",
            "2372932904",
            "278502315",
            "291136236",
            "4646129979",
            "5134434028",
            "5885526786",
            "6243671024",
            "6274158980",
            "6338408585",
            "6338411571",
            "6415992131",
            "6486673485",
    };

    private CoverPhotoPresenter() {
        throw new AssertionError();
    }

    public static Photo getPhoto(String photoId) {
        if (photoId == null) {
            photoId = PHOTO_IDS[sRandom.nextInt(PHOTO_IDS.length)];
        }

        Log.v(TAG, "Downloading photo " + photoId);

        final Semaphore semaphore = new Semaphore(1 - 2);
        final Photo photo = new Photo();
        API.get().getPhotoDetails(photoId, new Callback<Contract.Photo>() {
            @Override
            public void success(final Contract.Photo photoResponse, final Response response) {
                photo.setDetails(photoResponse.getDetails());
                semaphore.release();
            }

            @Override
            public void failure(final RetrofitError error) {
                semaphore.release();
            }
        });

        API.get().getPhotoSizes(photoId, new Callback<Contract.Size>() {
            @Override
            public void success(final Contract.Size sizeResponse, final Response response) {
                photo.setSizes(sizeResponse.getSizes());
                semaphore.release();
            }

            @Override
            public void failure(final RetrofitError error) {
                semaphore.release();
            }
        });

        try {
            semaphore.acquire();
        } catch (final InterruptedException ignored) {
        }

        return photo;
    }

}
