package com.deange.uwaterlooapi.http;

public enum DataFormat {

    JSON("json"),
    XML("xml");

    private String mDataType;

    private DataFormat(final String dataType) {
        mDataType = dataType;
    }

    public String getType() {
        return mDataType;
    }

    @Override
    public String toString() {
        return getType();
    }
}
