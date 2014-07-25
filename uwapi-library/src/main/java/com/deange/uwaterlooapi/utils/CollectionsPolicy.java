package com.deange.uwaterlooapi.utils;

public enum CollectionsPolicy {

    /**
     * Return a copy of the response's collection
     */
    RETURN_COPY,

    /**
     * Directly return the response's collection
     */
    MODIFIABLE,

    /**
     * Return an unmodifiable reference to the response's collection
     */
    UNMODIFIABLE;

    public static void setPolicy(final CollectionsPolicy policy) {
        CollectionUtils.setPolicy(policy);
    }
}
