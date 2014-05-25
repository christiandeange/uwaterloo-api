package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.http.DataFormat;

public final class UWaterlooApi {

    private static String sApiKey;
    private static DataFormat sDataFormat = DataFormat.JSON;

    private UWaterlooApi() {
        // NO INSTANCE FOR YOU!
    }

    public static void init(final String apiKey) {
        sApiKey = apiKey;
    }

    /* package */ static String getApiKey() {
        return sApiKey;
    }

    /* package */ static DataFormat getDataFormat() {
        return sDataFormat;
    }

    public static void setDataFormat(final DataFormat format) {
        if (format == null) {
            throw new NullPointerException("Data format cannot be null!");
        }
        sDataFormat = format;
    }

    public static void checkAccess() {
        if (UWaterlooApi.getApiKey() == null) {
            throw new IllegalStateException("API key is null, did you forget to call UWaterlooApi.init()?");
        }
    }


    /**
     * APIs DEFINED BELOW
     */

}
