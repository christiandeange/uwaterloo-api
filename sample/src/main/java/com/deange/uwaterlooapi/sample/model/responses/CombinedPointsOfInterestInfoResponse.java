package com.deange.uwaterlooapi.sample.model.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.sample.model.CombinedPointsOfInterestInfo;

public class CombinedPointsOfInterestInfoResponse
    extends BaseResponse
    implements
    Parcelable {

  private final CombinedPointsOfInterestInfo mInfo;

  public CombinedPointsOfInterestInfoResponse(final CombinedPointsOfInterestInfo info) {
    mInfo = info;
  }

  protected CombinedPointsOfInterestInfoResponse(final Parcel in) {
    super(in);
    mInfo = in.readParcelable(CombinedPointsOfInterestInfo.class.getClassLoader());
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeParcelable(mInfo, flags);
  }

  public static final Creator<CombinedPointsOfInterestInfoResponse> CREATOR =
      new Creator<CombinedPointsOfInterestInfoResponse>() {
        @Override
        public CombinedPointsOfInterestInfoResponse createFromParcel(final Parcel in) {
          return new CombinedPointsOfInterestInfoResponse(in);
        }

        @Override
        public CombinedPointsOfInterestInfoResponse[] newArray(final int size) {
          return new CombinedPointsOfInterestInfoResponse[size];
        }
      };

  @Override
  public CombinedPointsOfInterestInfo getData() {
    return mInfo;
  }

}
