package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Note extends BaseModel {

    @SerializedName("date")
    private String mDate;

    @SerializedName("outlet_id")
    private int mOutletId;

    @SerializedName("outlet_name")
    private String mOutletName;

    @SerializedName("note")
    private String mNote;

    public int getOutletId() {
        return mOutletId;
    }

    public String getOutletName() {
        return mOutletName;
    }

    public String getNote() {
        return mNote;
    }

    public Date getDate() {
        return Formatter.parseDate(mDate, Formatter.YMD);
    }

    public String getRawDate() {
        return mDate;
    }
}
