package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Meals extends BaseModel {

    @SerializedName("lunch")
    List<Meal> mLunch;

    @SerializedName("dinner")
    List<Meal> mDinner;

    /**
     * Lunch menu items
     */
    public List<Meal> getLunch() {
        return CollectionUtils.applyPolicy(mLunch);
    }

    /**
     * Dinner menu items
     */
    public List<Meal> getDinner() {
        return CollectionUtils.applyPolicy(mDinner);
    }
}
