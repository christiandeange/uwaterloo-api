package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Diet extends BaseModel {

    @SerializedName("diet_id")
    private int mId;

    @SerializedName("diet_type")
    private String mType;

    public int getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }
}
