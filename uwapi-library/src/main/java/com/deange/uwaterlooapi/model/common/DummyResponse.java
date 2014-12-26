package com.deange.uwaterlooapi.model.common;

public class DummyResponse<T> extends SimpleResponse<T> {

    private final T mObj;

    private DummyResponse(final T obj) {
        mObj = obj;
    }

    public static <T> DummyResponse<T> inject(final T obj) {
        return new DummyResponse<>(obj);
    }

    @Override
    public T getData() {
        return mObj;
    }

}
