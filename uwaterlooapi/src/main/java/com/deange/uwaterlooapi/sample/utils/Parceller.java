package com.deange.uwaterlooapi.sample.utils;

import android.text.TextUtils;

public final class Parceller {

    private Parceller() {
        throw new UnsupportedOperationException();
    }

    public static <T> String parcel(final T obj) {

        if (obj == null) {
            return null;
        }

        final String clazz = obj.getClass().getName();
        final String data = GsonController.getInstance().toJson(obj);
        return clazz + ";" + data;
    }

    public static <T> T unparcel(final String obj) {

        if (TextUtils.isEmpty(obj)) {
            return null;
        }

        final int semicolonPos = obj.indexOf(';');
        final String clazzName = obj.substring(0, semicolonPos);
        final Class<T> clazz;
        try {
            clazz = (Class<T>) Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        final String data = obj.substring(semicolonPos + 1, obj.length());
        return GsonController.getInstance().fromJson(data, clazz);
    }

}
