package com.deange.uwaterlooapi.sample.ui;

import com.deange.uwaterlooapi.sample.BuildConfig;
import com.deange.uwaterlooapi.sample.model.Photo;
import com.deange.uwaterlooapi.sample.net.Contract;
import com.deange.uwaterlooapi.sample.net.FlickrApi;

import java.util.Collections;
import java.util.Random;

public final class CoverPhotoPresenter {

    private static final FlickrApi API = new FlickrApi(BuildConfig.FLICKR_API_KEY);
    private static final Random sRandom = new Random();

    public static final String[] PHOTO_IDS = new String[] {
            "10395423826",
            "5134434028",
            "6415992131",
            "278502315",
            "6486673485",
            "6243671024",
            "10053104793",
            "5885526786",
            "6274158980",
            "4646129979",
            "15127909624",
            "291136236",
            "121557152",
    };

    private CoverPhotoPresenter() {
        throw new AssertionError();
    }

    public static Photo getPhoto(String photoId) {
        if (photoId == null) {
            photoId = PHOTO_IDS[sRandom.nextInt(PHOTO_IDS.length)];
        }

        final Contract.Photo photoDetails = API.get().getPhotoDetails(photoId);
        final Contract.Size photoSizes = API.get().getPhotoSizes(photoId);

        return new Photo(photoDetails.getDetails(), photoSizes.getSizes());
    }

}
