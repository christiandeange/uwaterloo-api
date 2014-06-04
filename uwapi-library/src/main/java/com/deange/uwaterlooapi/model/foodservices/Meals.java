package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class Meals extends BaseModel {

    @SerializedName("lunch")
    private List<Meal> mLunch;

    @SerializedName("dinner")
    private List<Meal> mDinner;

    public List<Meal> getLunch() {
        return Collections.unmodifiableList(mLunch);
    }

    public List<Meal> getDinner() {
        return Collections.unmodifiableList(mDinner);
    }
}
