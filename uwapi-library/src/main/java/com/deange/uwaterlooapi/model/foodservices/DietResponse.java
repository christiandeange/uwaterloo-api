package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DietResponse extends BaseResponse {

    @SerializedName("data")
    private List<Diet> mDiets;

    public List<Diet> getDiets() {
        return mDiets;
    }

}
