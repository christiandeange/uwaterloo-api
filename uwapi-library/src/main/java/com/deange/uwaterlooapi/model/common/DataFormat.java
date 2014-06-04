package com.deange.uwaterlooapi.model.common;

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
