package com.deange.uwaterlooapi.sample.model;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.poi.ATM;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class CombinedPointsOfInterestInfo extends BaseModel {

    private List<ATM> mATMs;

    public List<ATM> getATMs() {
        return mATMs;
    }

    public void setATMs(final List<ATM> ATMs) {
        mATMs = ATMs;
    }
}
