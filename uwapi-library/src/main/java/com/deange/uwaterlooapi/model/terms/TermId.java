package com.deange.uwaterlooapi.model.terms;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class TermId extends BaseModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    /**
     * Term's numeric ID
     */
    public int getId() {
        return mId;
    }

    /**
     * Parsed term name (human readable)
     */
    public String getName() {
        return mName;
    }

}
