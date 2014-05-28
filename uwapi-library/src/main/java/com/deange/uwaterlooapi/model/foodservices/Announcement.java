package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Announcement extends BaseModel {

    @SerializedName("date")
    private String mDate;

    @SerializedName("ad_text")
    private String mText;

    public Date getDate() {
        return Formatter.parseDate(mDate, Formatter.YMD);
    }

    public String getRawDate() {
        return mDate;
    }

    public String getText() {
        return mText;
    }
}
