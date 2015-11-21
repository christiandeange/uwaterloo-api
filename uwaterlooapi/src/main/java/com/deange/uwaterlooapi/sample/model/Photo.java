package com.deange.uwaterlooapi.sample.model;

import java.util.List;

public class Photo {

    private final PhotoDetails mDetails;
    private final List<PhotoSize> mSizes;

    public Photo(final PhotoDetails details, final List<PhotoSize> sizes) {
        mDetails = details;
        mSizes = sizes;
    }

    public List<PhotoSize> getSizes() {
        return mSizes;
    }

    public PhotoDetails getDetails() {
        return mDetails;
    }
}
