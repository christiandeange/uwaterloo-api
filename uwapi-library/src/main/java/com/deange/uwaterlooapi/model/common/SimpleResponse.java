package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

public class SimpleResponse<T extends BaseModel> extends BaseResponse {

    @SerializedName("data")
    private T mData;

    public T getData() {
        return mData;
    }

}
