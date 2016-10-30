package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.common.DateRange;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class MenuInfo extends BaseModel {

    @SerializedName("date")
    DateRange mDateRange;

    @SerializedName("outlets")
    List<Outlet> mOutlets;

    /**
     * Menu date object
     */
    public DateRange getDateRange() {
        return mDateRange;
    }

    /**
     * Available outlets
     */
    public List<Outlet> getOutlets() {
        return CollectionUtils.applyPolicy(mOutlets);
    }

}
