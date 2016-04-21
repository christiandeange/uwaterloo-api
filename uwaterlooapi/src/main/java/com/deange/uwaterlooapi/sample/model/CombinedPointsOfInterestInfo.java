package com.deange.uwaterlooapi.sample.model;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.poi.ATM;
import com.deange.uwaterlooapi.model.poi.GreyhoundStop;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class CombinedPointsOfInterestInfo extends BaseModel {

    private List<ATM> mATMs;
    private List<GreyhoundStop> mGreyhounds;

    public List<ATM> getATMs() {
        return mATMs;
    }

    public void setATMs(final List<ATM> ATMs) {
        mATMs = ATMs;
    }

    public List<GreyhoundStop> getGreyhounds() {
        return mGreyhounds;
    }

    public void setGreyhounds(final List<GreyhoundStop> greyhounds) {
        mGreyhounds = greyhounds;
    }
}
