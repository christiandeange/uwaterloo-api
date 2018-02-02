package com.deange.uwaterlooapi.sample.dagger;

import com.deange.uwaterlooapi.UWaterlooGsonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

  @Provides
  public static Gson providesGson() {
    return new GsonBuilder()
        .registerTypeAdapterFactory(UWaterlooGsonFactory.create())
        .create();
  }
}
