package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
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

    /**
     * Date of the menu (Y-m-d)
     */
    public Date getDate() {
        return Formatter.parseDate(mDate, Formatter.YMD);
    }

    /**
     * Date of the menu (Y-m-d) as a string
     */
    public String getRawDate() {
        return mDate;
    }

    /**
     * Day of the week
     */
    public String getDayOfWeek() {
        return mDayOfWeek;
    }

    /**
     * All the meals for the day
     */
    public Meals getMeals() {
        return mMeals;
    }

    /**
     * Additional announcements for the day
     */
    public String getNotes() {
        return mNotes;
    }
}
