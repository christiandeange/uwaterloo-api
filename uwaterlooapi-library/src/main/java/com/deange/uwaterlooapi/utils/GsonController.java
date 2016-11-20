package com.deange.uwaterlooapi.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonController {
    public static final String TAG = GsonController.class.getSimpleName();

    private static Gson sCache = null;
    private static final Object sLock = new Object();

    public static synchronized void createInstance() {
        if (sCache == null) {
            sCache = new GsonBuilder().create();
        }
    }

    public static Gson getInstance() {
        synchronized (sLock) {
            if (sCache == null) {
                createInstance();
            }
            return sCache;
        }
    }
}
