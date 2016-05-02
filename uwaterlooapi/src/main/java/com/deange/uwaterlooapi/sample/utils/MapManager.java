package com.deange.uwaterlooapi.sample.utils;

import android.Manifest;
import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class MapManager {

    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    public static void setLocationEnabled(final Activity activity, final GoogleMap map) {
        if (activity == null || map == null) {
            return;
        }

        if (!setLocationIfPossible(activity, map)) {
            Nammu.askForPermission(activity, LOCATION_PERMISSION, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    setLocationEnabled(activity, map);
                }

                @Override
                public void permissionRefused() {
                    // Can't do anything here
                }
            });
        }
    }

    private static boolean setLocationIfPossible(final Activity activity, final GoogleMap map) {
        if (hasPermission(activity)) {
            //noinspection MissingPermission
            map.setMyLocationEnabled(true);
            return true;

        } else {
            return false;
        }
    }

    private static boolean hasPermission(final Activity activity) {
        return Nammu.hasPermission(activity, LOCATION_PERMISSION);
    }
}
