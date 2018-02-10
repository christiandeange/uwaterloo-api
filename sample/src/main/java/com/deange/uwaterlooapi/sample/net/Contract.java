package com.deange.uwaterlooapi.sample.net;

import com.deange.uwaterlooapi.sample.model.PhotoDetails;
import com.deange.uwaterlooapi.sample.model.PhotoSize;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public interface Contract {

  class Size {
    @SerializedName("sizes")
    SizeInfo mSize;

    public List<PhotoSize> getSizes() {
      return (mSize == null) ? new ArrayList<>() : mSize.mSizes;
    }
  }

  class SizeInfo {
    @SerializedName("size")
    List<PhotoSize> mSizes;
  }

  class Photo {
    @SerializedName("photo")
    PhotoDetails mPhoto;

    public PhotoDetails getDetails() {
      return mPhoto;
    }
  }

}
