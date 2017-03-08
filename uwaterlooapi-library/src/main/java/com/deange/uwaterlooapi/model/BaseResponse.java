package com.deange.uwaterlooapi.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public abstract class BaseResponse {

    private static final Gson GSON = new Gson();

    public BaseResponse() {
        super();
    }

    public static final String META = "meta";

    @SerializedName(META)
    Metadata mMetadata;

    public Metadata getMetadata() {
        return mMetadata;
    }

    public abstract Object getData();

    public static <T extends BaseResponse> String serialize(final T obj) {

        if (obj == null) {
            return null;
        }

        final String clazz = obj.getClass().getName();
        final String data = GSON.toJson(obj);
        return clazz + ";" + data;
    }

    public static <T extends BaseResponse> T deserialize(final String obj) {

        if (TextUtils.isEmpty(obj)) {
            return null;
        }

        final int semicolonPos = obj.indexOf(';');
        final String clazzName = obj.substring(0, semicolonPos);
        final Class<T> clazz;
        try {
            //noinspection unchecked
            clazz = (Class<T>) Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        final String data = obj.substring(semicolonPos + 1, obj.length());
        return GSON.fromJson(data, clazz);
    }
}
