package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Diet extends BaseModel {

    @SerializedName("diet_id")
    int mId;

    @SerializedName("diet_type")
    String mType;

    /**
     * Diet ID number
     */
    public int getId() {
        return mId;
    }

    /**
     * Diet type
     */
    public String getType() {
        return mType;
    }
}
