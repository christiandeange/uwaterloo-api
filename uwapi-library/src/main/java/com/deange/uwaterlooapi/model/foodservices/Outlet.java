package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Utils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Outlet extends BaseModel {

    @SerializedName("outlet_id")
    private int mId;

    @SerializedName("outlet_name")
    private String mName;

    @SerializedName("menu")
    private List<Menu> mMenu;

    @SerializedName("has_breakfast")
    private int mBreakfast;

    @SerializedName("has_lunch")
    private int mLunch;

    @SerializedName("has_dinner")
    private int mDinner;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    /**
     * This field is only set for getMenu() request
     */
    public List<Menu> getMenu() {
        return mMenu;
    }

    /**
     * This field is only set for getOutlets() request
     */
    public boolean servesBreakfast() {
        return Utils.convertBool(mBreakfast);
    }

    /**
     * This field is only set for getOutlets() request
     */
    public boolean servesLunch() {
        return Utils.convertBool(mLunch);
    }

    /**
     * This field is only set for getOutlets() request
     */
    public boolean servesDinner() {
        return Utils.convertBool(mDinner);
    }
}
