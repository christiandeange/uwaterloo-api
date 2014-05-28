package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementsResponse extends BaseResponse {

    @SerializedName("data")
    private List<Announcement> mAnnouncements;

    public List<Announcement> getAnnouncements() {
        return mAnnouncements;
    }

}
