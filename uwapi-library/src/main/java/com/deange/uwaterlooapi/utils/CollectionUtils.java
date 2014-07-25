package com.deange.uwaterlooapi.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CollectionUtils {

    private static CollectionsPolicy sPolicy = CollectionsPolicy.MODIFIABLE;

    private CollectionUtils() {
        throw new UnsupportedOperationException();
    }

    public static void setPolicy(final CollectionsPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Policy cannot be null! " +
                    "If you wish to set the default, use CollectionsPolicy.MODIFIABLE");
        }
        sPolicy = policy;
    }

    public static CollectionsPolicy getPolicy() {
        return sPolicy;
    }

    public static <E> List<E> applyPolicy(final List<E> collection) {
        switch (sPolicy) {
            case MODIFIABLE:   return collection;
            case RETURN_COPY:  return new ArrayList<>(collection);
            case UNMODIFIABLE: return Collections.unmodifiableList(collection);
            default: return collection;
        }
    }

    public static <K, V> Map<K, V> applyPolicy(final Map<K, V> collection) {
        switch (sPolicy) {
            case MODIFIABLE:   return collection;
            case RETURN_COPY:  return new HashMap<>(collection);
            case UNMODIFIABLE: return Collections.unmodifiableMap(collection);
            default: return collection;
        }
    }

}
