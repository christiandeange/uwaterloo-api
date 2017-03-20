package com.deange.uwaterlooapi.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseResponse;

public class CombinedCourseInfoResponse
        extends BaseResponse
        implements
        Parcelable {

    private final CombinedCourseInfo mInfo;

    public CombinedCourseInfoResponse(final CombinedCourseInfo info) {
        mInfo = info;
    }

    protected CombinedCourseInfoResponse(final Parcel in) {
        super(in);
        mInfo = in.readParcelable(CombinedCourseInfo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(mInfo, flags);
    }

    public static final Creator<CombinedCourseInfoResponse> CREATOR =
            new Creator<CombinedCourseInfoResponse>() {
                @Override
                public CombinedCourseInfoResponse createFromParcel(final Parcel in) {
                    return new CombinedCourseInfoResponse(in);
                }

                @Override
                public CombinedCourseInfoResponse[] newArray(final int size) {
                    return new CombinedCourseInfoResponse[size];
                }
            };

    @Override
    public CombinedCourseInfo getData() {
        return mInfo;
    }

}
