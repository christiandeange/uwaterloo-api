package com.deange.uwaterlooapi.model.resources;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Printer extends BaseModel {

    @SerializedName("printer")
    String mPrinterName;

    @SerializedName("ad")
    String mActiveDirectory;

    @SerializedName("server")
    String mServer;

    @SerializedName("comment")
    String mComments;

    @SerializedName("driver")
    String mDriver;

    @SerializedName("room")
    String mRoom;

    @SerializedName("faculty")
    String mFaculty;

    /**
     * Name of the printer
     */
    public String getPrinterName() {
        return mPrinterName;
    }

    /**
     * Printers active directory id
     */
    public String getActiveDirectory() {
        return mActiveDirectory;
    }

    /**
     * Printer server name
     */
    public String getServer() {
        return mServer;
    }

    /**
     * Additional comments on the printer
     */
    public String getComments() {
        return mComments;
    }

    /**
     * Printer driver information
     */
    public String getDriverInfo() {
        return mDriver;
    }

    /**
     * Printer's physical room location
     */
    public String getRoom() {
        return mRoom;
    }

    /**
     * Faculty the printer belongs to
     */
    public String getFaculty() {
        return mFaculty;
    }
}
