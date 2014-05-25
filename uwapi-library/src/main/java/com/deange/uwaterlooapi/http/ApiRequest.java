package com.deange.uwaterlooapi.http;

import android.util.Log;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.utils.GsonController;
import com.deange.uwaterlooapi.utils.Utils;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class ApiRequest {

    private static final String TAG = ApiRequest.class.getSimpleName();

    private static final OkHttpClient sClient = new OkHttpClient();

    private static OkHttpClient getClient() {
        return sClient;
    }

    public static <T extends BaseResponse> T get(final Type clazz, final String url) throws IOException {

        // Create HTTP request
        final HttpURLConnection connection = getClient().open(new URL(url));

        // Retrieve GET response
        final InputStream in = connection.getInputStream();
        final String outputJson = Utils.streamToString(in);
        in.close();

        try {
            // Return parsed object
            return GsonController.getInstance().fromJson(outputJson, clazz);

        } catch (final Exception ex) {
            Log.w(TAG, "GET from " + url + " failed with " + connection.getResponseCode() + ".");
            ex.printStackTrace();
            return null;
        }

    }

}
