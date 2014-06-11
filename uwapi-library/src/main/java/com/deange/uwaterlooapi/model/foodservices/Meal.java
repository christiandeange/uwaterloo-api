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

    /**
     * The ID of the meal
     */
    public int getId() {
        return mId;
    }

    /**
     * The name of the meal
     */
    public String getName() {
        return mName;
    }

    /**
     * The diet type of the meal
     */
    public String getDietType() {
        return mDietType;
    }
}
