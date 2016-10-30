package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimpleListResponse<T extends BaseModel> extends BaseResponse {

    @SerializedName("data")
    List<T> mData;

    @Override
    public List<T> getData() {
        return mData;
    }

}
