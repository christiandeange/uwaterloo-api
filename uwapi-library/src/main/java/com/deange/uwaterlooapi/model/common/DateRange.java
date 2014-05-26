package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DateRange extends BaseModel {

    /**
     *  "date":{
     *      "week":12,
     *      "year":2013,
     *      "start":"2013-03-18",
     *      "end":"2013-03-22"
     *  },
     */

    public static final String YMD = "yyyy-MM-dd";

    @SerializedName("week")
    private int mWeek;

    @SerializedName("year")
    private int mYear;

    @SerializedName("start")
    private String mStart;

    @SerializedName("end")
    private String mEnd;


    public int getWeek() {
        return mWeek;
    }

    public int getYear() {
        return mYear;
    }

    public Date getStart() {
        return Formatter.parseDate(mStart, YMD);
    }

    public Date getEnd() {
        return Formatter.parseDate(mEnd, YMD);
    }
}
