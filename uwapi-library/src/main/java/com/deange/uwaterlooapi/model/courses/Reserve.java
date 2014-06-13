package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Reserve extends BaseModel {

    @SerializedName("reserve_group")
    private String mReserveGroup;

    @SerializedName("enrollment_capacity")
    private int mEnrollmentCapacity;

    @SerializedName("enrollment_total")
    private int mEnrollmentTotal;

    /**
     * Name of the reserved group
     */
    public String getReserveGroup() {
        return mReserveGroup;
    }

    /**
     * Total enrollment capacity of the group
     */
    public int getEnrollmentCapacity() {
        return mEnrollmentCapacity;
    }

    /**
     * Total reserve enrollment
     */
    public int getEnrollmentTotal() {
        return mEnrollmentTotal;
    }
}
