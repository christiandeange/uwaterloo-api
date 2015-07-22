package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Class extends BaseModel {

    @SerializedName("date")
    ClassDate mDate;

    @SerializedName("location")
    Location mLocation;

    @SerializedName("instructors")
    List<String> mInstructors;

    /**
     * Date for course schedule
     */
    public ClassDate getDate() {
        return mDate;
    }

    /**
     * Name of the building for the class location
     */
    public String getBuilding() {
        return mLocation == null ? null : mLocation.mBuilding;
    }

    /**
     * Room number from the building for the class location
     */
    public String getRoom() {
        return mLocation == null ? null : mLocation.mRoom;
    }

    /**
     * Names of instructors teaching the course
     */
    public List<String> getInstructors() {
        return CollectionUtils.applyPolicy(mInstructors);
    }

    @Parcel
    public static final class Location {
        public Location() {

        }

        @SerializedName("building")
        String mBuilding;

        @SerializedName("room")
        String mRoom;

    }
}
