package com.deange.uwaterlooapi.model.poi;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Defibrillator extends BasicPointOfInterest {

    @SerializedName("level")
    String mLevel;

    @SerializedName("room")
    String mRoom;

    @SerializedName("image")
    String mImage;

    public String getLevel() {
        return mLevel;
    }

    public String getRoom() {
        return mRoom;
    }

    public String getImage() {
        return mImage;
    }
}
