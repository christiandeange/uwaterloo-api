package com.deange.uwaterlooapi.sample.model;

import com.deange.uwaterlooapi.model.BaseResponse;

public class CombinedPointsOfInterestInfoResponse extends BaseResponse {

    private final CombinedPointsOfInterestInfo mInfo;

    public CombinedPointsOfInterestInfoResponse(final CombinedPointsOfInterestInfo info) {
        mInfo = info;
    }

    @Override
    public CombinedPointsOfInterestInfo getData() {
        return mInfo;
    }

}
