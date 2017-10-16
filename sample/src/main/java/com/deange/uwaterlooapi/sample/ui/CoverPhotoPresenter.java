package com.deange.uwaterlooapi.sample.ui;

import android.util.Log;

import com.deange.uwaterlooapi.sample.BuildConfig;
import com.deange.uwaterlooapi.sample.model.Photo;
import com.deange.uwaterlooapi.sample.net.Contract;
import com.deange.uwaterlooapi.sample.net.FlickrApi;

import java.util.Random;
import java.util.concurrent.Semaphore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

  /**
   * Should *NOT* be called on the main thread!
   */
  public static Photo getPhoto(String photoId) {
    if (photoId == null) {
      photoId = PHOTO_IDS[sRandom.nextInt(PHOTO_IDS.length)];
    }

    Log.v(TAG, "Downloading photo " + photoId);

    final Semaphore semaphore = new Semaphore(1 - 2);
    final Photo photo = new Photo();

    API.get().getPhotoDetails(photoId).enqueue(new Callback<Contract.Photo>() {
      @Override
      public void onResponse(
          final Call<Contract.Photo> call,
          final Response<Contract.Photo> response) {
        photo.setDetails(response.body().getDetails());
        semaphore.release();
      }

      @Override
      public void onFailure(final Call<Contract.Photo> call, final Throwable t) {
        semaphore.release();
      }
    });

    API.get().getPhotoSizes(photoId).enqueue(new Callback<Contract.Size>() {
      @Override
      public void onResponse(
          final Call<Contract.Size> call,
          final Response<Contract.Size> response) {
        photo.setSizes(response.body().getSizes());
        semaphore.release();
      }

      @Override
      public void onFailure(final Call<Contract.Size> call, final Throwable t) {
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
