package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Outlet extends BaseModel {

    @SerializedName("outlet_id")
    int mId;

    @SerializedName("outlet_name")
    String mName;

    @SerializedName("menu")
    List<Menu> mMenu;

    @SerializedName("has_breakfast")
    int mBreakfast;

    @SerializedName("has_lunch")
    int mLunch;

    @SerializedName("has_dinner")
    int mDinner;

    /**
     * Foodservices ID for the outlet
     */
    public int getId() {
        return mId;
    }

    /**
     * Name of the outlet
     */
    public String getName() {
        return mName;
    }

    /**
     * The outlet menu list
     * <p />
     * This field is only set for getMenu() request
     */
    public List<Menu> getMenu() {
        return CollectionUtils.applyPolicy(mMenu);
    }

    /**
     * If serves breakfast
     * <p />
     * This field is only set for getOutlets() request
     */
    public boolean servesBreakfast() {
        return convertBool(mBreakfast);
    }

    /**
     * If serves lunch
     * <p />
     * This field is only set for getOutlets() request
     */
    public boolean servesLunch() {
        return convertBool(mLunch);
    }

    /**
     * If serves dinner
     * <p />
     * This field is only set for getOutlets() request
     */
    public boolean servesDinner() {
        return convertBool(mDinner);
    }

    private static boolean convertBool(final int i) {
        return i != 0;
    }
}
