package com.deange.uwaterlooapi.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse extends BaseModel {

    public BaseResponse() {
        super();
    }

    public static final String META = "meta";

    @SerializedName(META)
    private Metadata mMetadata;

    public Metadata getMetadata() {
        return mMetadata;
    }
}
