package com.deange.uwaterlooapi.model.terms;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;
import java.util.Map;

@Parcel
public class TermInfo extends BaseModel {

    @SerializedName("current_term")
    int mCurrentTerm;

    @SerializedName("previous_term")
    int mPreviousTerm;

    @SerializedName("next_term")
    int mNextTerm;

    @SerializedName("listings")
    Map<Integer, List<TermId>> mListings;

    /**
     * Current Term's numerical value
     */
    public int getCurrentTerm() {
        return mCurrentTerm;
    }

    /**
     * Previous term's numerical value
     */
    public int getPreviousTerm() {
        return mPreviousTerm;
    }

    /**
     * Upcoming term's numerical value
     */
    public int getNextTerm() {
        return mNextTerm;
    }

    /**
     * Term listings by year
     * <p />
     * First item is previous year, second is current, last is next year
     */
    public Map<Integer, List<TermId>> getListings() {
        return CollectionUtils.applyPolicy(mListings);
    }
}
