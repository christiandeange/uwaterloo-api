package com.deange.uwaterlooapi.sample.controller;

import com.deange.uwaterlooapi.UWaterlooGsonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonController {

    private static Gson sDefaultGson;

    public static synchronized Gson getInstance() {
        if (sDefaultGson == null) {
            sDefaultGson = new GsonBuilder()
                    .registerTypeAdapterFactory(UWaterlooGsonFactory.create())
                    .create();
        }
        return sDefaultGson;
    }
}
