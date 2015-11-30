package com.deange.uwaterlooapi.sample.utils;

public final class MathUtils {

    private MathUtils() {
        throw new AssertionError();
    }

    public static float clamp(final float f, final float min, final float max) {
        return (f < min ? min : (f > max ? max : f));
    }

}
