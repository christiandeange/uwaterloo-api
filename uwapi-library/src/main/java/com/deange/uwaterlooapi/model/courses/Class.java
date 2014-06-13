package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class Class extends BaseModel {

    @SerializedName("date")
    private ClassDate mDate;

    @SerializedName("location")
    private Location mLocation;

    @SerializedName("instructors")
    private List<String> mInstructors;

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
        return Collections.unmodifiableList(mInstructors);
    }

    private static final class Location {

        @SerializedName("building")
        private String mBuilding;

        @SerializedName("room")
        private String mRoom;

    }
}
