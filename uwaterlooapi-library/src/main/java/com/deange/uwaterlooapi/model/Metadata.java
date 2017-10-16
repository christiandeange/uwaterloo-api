package com.deange.uwaterlooapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Metadata
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("requests")
  int mRequests;

  @SerializedName("timestamp")
  long mTimestamp;

  @SerializedName("status")
  int mStatus;

  @SerializedName("message")
  String mMessage;

  @SerializedName("method_id")
  int mMethodId;

  @SerializedName("version")
  String mVersion;

  protected Metadata(final Parcel in) {
    super(in);
    mRequests = in.readInt();
    mTimestamp = in.readLong();
    mStatus = in.readInt();
    mMessage = in.readString();
    mMethodId = in.readInt();
    mVersion = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mRequests);
    dest.writeLong(mTimestamp);
    dest.writeInt(mStatus);
    dest.writeString(mMessage);
    dest.writeInt(mMethodId);
    dest.writeString(mVersion);
  }

  public static final Creator<Metadata> CREATOR = new Creator<Metadata>() {
    @Override
    public Metadata createFromParcel(final Parcel in) {
      return new Metadata(in);
    }

    @Override
    public Metadata[] newArray(final int size) {
      return new Metadata[size];
    }
  };

  /**
   * The number of times this method has been called from this API key
   */
  public int getRequests() {
    return mRequests;
  }

  /**
   * Current server time
   */
  public long getTimestamp() {
    return mTimestamp;
  }

  /**
   * HTTP/1.1 response code, as per RFC 2616
   */
  public int getStatus() {
    return mStatus;
  }

  /**
   * The response string from the server
   */
  public String getMessage() {
    return mMessage;
  }

  /**
   * The ID of the method called
   */
  public int getMethodId() {
    return mMethodId;
  }

  /**
   * The current version of this API
   */
  public String getVersion() {
    return mVersion;
  }
}
