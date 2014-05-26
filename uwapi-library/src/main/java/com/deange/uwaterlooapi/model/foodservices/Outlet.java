package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Outlet extends BaseModel {

    @SerializedName("outlet_id")
    private int mId;

    @SerializedName("outlet_name")
    private String mName;

    @SerializedName("menu")
    private List<Menu> mMenu;


    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<Menu> getMenu() {
        return mMenu;
    }
}
