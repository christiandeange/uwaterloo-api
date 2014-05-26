package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoteResponse extends BaseResponse {

    @SerializedName("data")
    private List<Note> mNotes;

    public List<Note> getNotes() {
        return mNotes;
    }

}
