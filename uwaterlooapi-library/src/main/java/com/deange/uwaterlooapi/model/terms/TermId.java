package com.deange.uwaterlooapi.model.terms;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class TermId
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("id")
  int mId;

  @SerializedName("name")
  String mName;

  protected TermId(final Parcel in) {
    super(in);
    mId = in.readInt();
    mName = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mName);
  }

  public static final Creator<TermId> CREATOR = new Creator<TermId>() {
    @Override
    public TermId createFromParcel(final Parcel in) {
      return new TermId(in);
    }

    @Override
    public TermId[] newArray(final int size) {
      return new TermId[size];
    }
  };

  /**
   * Term's numeric ID
   */
  public int getId() {
    return mId;
  }

  /**
   * Parsed term name (human readable)
   */
  public String getName() {
    return mName;
  }

}
