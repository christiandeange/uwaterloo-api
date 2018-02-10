package com.deange.uwaterlooapi.sample.model;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;

public class Author {

  @SerializedName("username")
  String mUsername;

  @SerializedName("realname")
  String mRealname;

  public String getUsername() {
    return mUsername;
  }

  public String getRealname() {
    return mRealname;
  }

  public String getPreferredName() {
    return !TextUtils.isEmpty(mRealname) ? mRealname : mUsername;
  }
}
