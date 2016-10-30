package com.deange.uwaterlooapi.sample;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public final class CrashReporting {

    private CrashReporting() {
        throw new AssertionError();
    }

    public static void init(final Context context) {
        Fabric.with(context.getApplicationContext(), new Crashlytics());
    }

    public static void setUserId(final String userId) {
        Crashlytics.getInstance().core.setUserIdentifier(userId);
    }

    public static void report(final Throwable t) {
        Crashlytics.getInstance().core.logException(t);
    }

    public static void report(final String tag, final String message) {
        Crashlytics.getInstance().core.log(Log.ERROR, tag, message);
    }

    public static void report(final String tag, final String message, final Throwable t) {
        report(tag, message);
        report(t);
    }
}
