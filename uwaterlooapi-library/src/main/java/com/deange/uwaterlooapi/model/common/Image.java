package com.deange.uwaterlooapi.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Image
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("id")
  int mId;

  @SerializedName("file")
  String mFile;

  @SerializedName("alt")
  String mAltText;

  @SerializedName("mime")
  String mMimeType;

  @SerializedName("size")
  int mSizeBytes;

  @SerializedName("width")
  int mWidth;

  @SerializedName("height")
  int mHeight;

  @SerializedName("url")
  String mUrl;

  protected Image(final Parcel in) {
    super(in);
    mId = in.readInt();
    mFile = in.readString();
    mAltText = in.readString();
    mMimeType = in.readString();
    mSizeBytes = in.readInt();
    mWidth = in.readInt();
    mHeight = in.readInt();
    mUrl = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mFile);
    dest.writeString(mAltText);
    dest.writeString(mMimeType);
    dest.writeInt(mSizeBytes);
    dest.writeInt(mWidth);
    dest.writeInt(mHeight);
    dest.writeString(mUrl);
  }

  public static final Creator<Image> CREATOR = new Creator<Image>() {
    @Override
    public Image createFromParcel(final Parcel in) {
      return new Image(in);
    }

    @Override
    public Image[] newArray(final int size) {
      return new Image[size];
    }
  };

  /**
   * Unique id of image
   */
  public int getId() {
    return mId;
  }

  /**
   * Relative link to image file path in filename.{format}
   */
  public String getFile() {
    return mFile;
  }

  /**
   * Image alternate text
   */
  public String getAltText() {
    return mAltText;
  }

  /**
   * Image MIME type in "string/{format}"
   */
  public String getMimeType() {
    return mMimeType;
  }

  /**
   * Image file size in bytes
   */
  public int getSizeInBytes() {
    return mSizeBytes;
  }

  /**
   * Image width in pixels
   */
  public int getWidth() {
    return mWidth;
  }

  /**
   * Image height in pixels
   */
  public int getHeight() {
    return mHeight;
  }

  /**
   * Full link to image resource
   */
  public String getUrl() {
    return mUrl;
  }

}
