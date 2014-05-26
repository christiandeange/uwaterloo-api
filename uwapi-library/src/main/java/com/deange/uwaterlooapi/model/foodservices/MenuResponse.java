package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.model.common.DateRange;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuResponse extends BaseResponse {

    @SerializedName("data")
    private MenuData mData;

    public DateRange getDateRange() {
        return (mData == null) ? null : mData.mDateRange;
    }

    public List<Outlet> getOutlets() {
        return (mData == null) ? null : mData.mOutlets;
    }

    private static final class MenuData {

        @SerializedName("date")
        private DateRange mDateRange;

        @SerializedName("outlets")
        private List<Outlet> mOutlets;

    }
}
