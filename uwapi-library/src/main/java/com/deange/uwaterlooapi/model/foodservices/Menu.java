package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.DateRange;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Menu extends BaseModel {

    @SerializedName("date")
    private String mDate;

    @SerializedName("day")
    private String mDayOfWeek;

    @SerializedName("meals")
    private Meals mMeals;

    @SerializedName("notes")
    private String mNotes;

    public Date getDate() {
        return Formatter.parseDate(mDate, DateRange.YMD);
    }

    public String getDayOfWeek() {
        return mDayOfWeek;
    }

    public Meals getMeals() {
        return mMeals;
    }

    public String getNotes() {
        return mNotes;
    }
}
