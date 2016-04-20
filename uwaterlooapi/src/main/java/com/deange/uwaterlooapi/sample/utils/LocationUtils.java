package com.deange.uwaterlooapi.sample.utils;

import com.google.android.gms.maps.model.LatLng;

public final class LocationUtils {

    private LocationUtils() {
        throw new AssertionError();
    }

    public static LatLng getLatLng(final float[] location) {
        return new LatLng(location[0], location[1]);
    }

}
