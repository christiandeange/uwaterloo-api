package com.deange.uwaterlooapi.sample.model;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.poi.ATM;
import com.deange.uwaterlooapi.model.poi.GreyhoundStop;
import com.deange.uwaterlooapi.model.poi.Helpline;
import com.deange.uwaterlooapi.model.poi.Photosphere;
import com.deange.uwaterlooapi.sample.ui.modules.poi.PointsOfInterestIterator;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class CombinedPointsOfInterestInfo extends BaseModel {

    private List<ATM> mATMs;
    private List<GreyhoundStop> mGreyhounds;
    private List<Photosphere> mPhotospheres;
    private List<Helpline> mHelplines;

    public PointsOfInterestIterator getAllPointsOfInterest() {
        return new PointsOfInterestIterator(mATMs, mGreyhounds, mPhotospheres, mHelplines);
    }

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

    public List<Photosphere> getPhotospheres() {
        return mPhotospheres;
    }

    public void setPhotospheres(final List<Photosphere> photospheres) {
        mPhotospheres = photospheres;
    }

    public List<Helpline> getHelplines() {
        return mHelplines;
    }

    public void setHelplines(final List<Helpline> helplines) {
        mHelplines = helplines;
    }
}
