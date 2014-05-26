package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Meal extends BaseModel {

    @SerializedName("product_id")
    private int mId;

    @SerializedName("product_name")
    private String mName;

    @SerializedName("diet_type")
    private String mDietType;


    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDietType() {
        return mDietType;
    }
}
