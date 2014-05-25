package com.deange.uwaterlooapi.utils;

public class Pair<K, V> {
    public final K first;
    public final V second;

    public Pair(final K first, final V second) {
        this.first = first;
        this.second = second;
    }

    public static <K, V> Pair<K, V> make(final K first, final V second) {
        return new Pair<K, V>(first, second);
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Pair)) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) other;
        return Utils.equals(pair.first, first) && Utils.equals(pair.second, second);
    }

}
