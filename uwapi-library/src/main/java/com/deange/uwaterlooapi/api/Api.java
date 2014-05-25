package com.deange.uwaterlooapi.api;

import android.net.Uri;

import com.deange.uwaterlooapi.http.QueryParams;
import com.deange.uwaterlooapi.utils.Pair;

/* package */ abstract class Api {

    private final String mName;

    /* package */ Api(final String name) {
        mName = name;
    }

    public static final int VERSION = 2;

    public static final String BASE_URL = "https://api.uwaterloo.ca/v" + VERSION;

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return getName();
    }

    protected String buildEndpointUrl(final String apiEndpoint, final QueryParams queryParams) {

        UWaterlooApi.checkAccess();

        final StringBuilder sb = new StringBuilder(BASE_URL);
        sb.append(apiEndpoint);
        sb.append(".");
        sb.append(UWaterlooApi.getDataFormat());

        sb.append("?key=");
        sb.append(Uri.encode(UWaterlooApi.getApiKey()));

        for (Pair<String, String> param : queryParams) {
            sb.append("&");
            sb.append(Uri.encode(param.first));
            sb.append("=");
            sb.append(Uri.encode(param.second));
        }

        return sb.toString();
    }


}
