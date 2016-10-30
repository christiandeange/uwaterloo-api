package com.deange.uwaterlooapi.sample.model;

import com.deange.uwaterlooapi.model.BaseResponse;

public class CombinedCourseInfoResponse extends BaseResponse {

    private final CombinedCourseInfo mInfo;

    public CombinedCourseInfoResponse(final CombinedCourseInfo info) {
        mInfo = info;
    }

    @Override
    public CombinedCourseInfo getData() {
        return mInfo;
    }

}
