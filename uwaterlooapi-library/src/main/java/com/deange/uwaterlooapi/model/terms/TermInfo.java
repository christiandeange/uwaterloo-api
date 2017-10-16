package com.deange.uwaterlooapi.model.terms;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.MapUtils;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermInfo
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("current_term")
  int mCurrentTerm;

  @SerializedName("previous_term")
  int mPreviousTerm;

  @SerializedName("next_term")
  int mNextTerm;

  @SerializedName("listings")
  Map<Integer, List<TermId>> mListings;

  protected TermInfo(final Parcel in) {
    super(in);
    mCurrentTerm = in.readInt();
    mPreviousTerm = in.readInt();
    mNextTerm = in.readInt();
    mListings = MapUtils.readMap(in, new HashMap<Integer, List<TermId>>());
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mCurrentTerm);
    dest.writeInt(mPreviousTerm);
    dest.writeInt(mNextTerm);
    MapUtils.writeMap(dest, mListings);
  }

  public static final Creator<TermInfo> CREATOR = new Creator<TermInfo>() {
    @Override
    public TermInfo createFromParcel(final Parcel in) {
      return new TermInfo(in);
    }

    @Override
    public TermInfo[] newArray(final int size) {
      return new TermInfo[size];
    }
  };

  /**
   * Current Term's numerical value
   */
  public int getCurrentTerm() {
    return mCurrentTerm;
  }

  /**
   * Previous term's numerical value
   */
  public int getPreviousTerm() {
    return mPreviousTerm;
  }

  /**
   * Upcoming term's numerical value
   */
  public int getNextTerm() {
    return mNextTerm;
  }

  /**
   * Term listings by year
   * <p/>
   * First item is previous year, second is current, last is next year
   */
  public Map<Integer, List<TermId>> getListings() {
    return mListings;
  }
}
