package com.deange.uwaterlooapi.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.poi.ATM;
import com.deange.uwaterlooapi.model.poi.Defibrillator;
import com.deange.uwaterlooapi.model.poi.GreyhoundStop;
import com.deange.uwaterlooapi.model.poi.Helpline;
import com.deange.uwaterlooapi.model.poi.Library;
import com.deange.uwaterlooapi.model.poi.Photosphere;
import com.deange.uwaterlooapi.sample.ui.modules.poi.PointsOfInterestIterator;

import java.util.List;

public class CombinedPointsOfInterestInfo
        extends BaseModel
        implements
        Parcelable {

    private List<ATM> mATMs;
    private List<GreyhoundStop> mGreyhounds;
    private List<Photosphere> mPhotospheres;
    private List<Helpline> mHelplines;
    private List<Library> mLibraries;
    private List<Defibrillator> mDefibrillators;

    public CombinedPointsOfInterestInfo() {
    }

    protected CombinedPointsOfInterestInfo(final Parcel in) {
        super(in);
        mATMs = in.createTypedArrayList(ATM.CREATOR);
        mGreyhounds = in.createTypedArrayList(GreyhoundStop.CREATOR);
        mPhotospheres = in.createTypedArrayList(Photosphere.CREATOR);
        mHelplines = in.createTypedArrayList(Helpline.CREATOR);
        mLibraries = in.createTypedArrayList(Library.CREATOR);
        mDefibrillators = in.createTypedArrayList(Defibrillator.CREATOR);
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(mATMs);
        dest.writeTypedList(mGreyhounds);
        dest.writeTypedList(mPhotospheres);
        dest.writeTypedList(mHelplines);
        dest.writeTypedList(mLibraries);
        dest.writeTypedList(mDefibrillators);
    }

    public static final Creator<CombinedPointsOfInterestInfo> CREATOR =
            new Creator<CombinedPointsOfInterestInfo>() {
                @Override
                public CombinedPointsOfInterestInfo createFromParcel(final Parcel in) {
                    return new CombinedPointsOfInterestInfo(in);
                }

                @Override
                public CombinedPointsOfInterestInfo[] newArray(final int size) {
                    return new CombinedPointsOfInterestInfo[size];
                }
            };

    public PointsOfInterestIterator getAllPointsOfInterest() {
        return new PointsOfInterestIterator(
                mATMs, mGreyhounds, mPhotospheres, mHelplines, mLibraries, mDefibrillators
        );
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

    public List<Library> getLibraries() {
        return mLibraries;
    }

    public void setLibraries(final List<Library> libraries) {
        mLibraries = libraries;
    }

    public List<Defibrillator> getDefibrillators() {
        return mDefibrillators;
    }

    public void setDefibrillators(final List<Defibrillator> defibrillators) {
        mDefibrillators = defibrillators;
    }
}
