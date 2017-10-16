package com.deange.uwaterlooapi.sample.model;

import java.util.List;

public class Photo {

  private PhotoDetails mDetails;
  private List<PhotoSize> mSizes;

  public Photo() {
  }

  public Photo(final PhotoDetails details, final List<PhotoSize> sizes) {
    mDetails = details;
    mSizes = sizes;
  }

  public PhotoDetails getDetails() {
    return mDetails;
  }

  public List<PhotoSize> getSizes() {
    return mSizes;
  }

  public void setDetails(final PhotoDetails details) {
    mDetails = details;
  }

  public void setSizes(final List<PhotoSize> sizes) {
    mSizes = sizes;
  }
}
