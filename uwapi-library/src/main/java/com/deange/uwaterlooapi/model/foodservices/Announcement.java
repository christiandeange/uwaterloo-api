package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Announcement extends BaseModel {

    @SerializedName("date")
    String mDate;

    @SerializedName("ad_text")
    String mText;

    /**
     * Advertisement date object
     */
    public Date getDate() {
        return Formatter.parseDate(mDate, Formatter.YMD);
    }

    /**
     * Advertisement date object as a string
     */
    public String getRawDate() {
        return mDate;
    }

    /**
     * Advertisement text
     */
    public String getText() {
        return mText;
    }
}
