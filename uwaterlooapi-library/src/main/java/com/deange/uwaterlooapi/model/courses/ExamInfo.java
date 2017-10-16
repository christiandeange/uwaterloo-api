package com.deange.uwaterlooapi.model.courses;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamInfo
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("course")
  String mCourse;

  @SerializedName("sections")
  List<ExamSection> mSections;

  protected ExamInfo(final Parcel in) {
    super(in);
    mCourse = in.readString();
    mSections = in.createTypedArrayList(ExamSection.CREATOR);
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mCourse);
    dest.writeTypedList(mSections);
  }

  public static final Creator<ExamInfo> CREATOR = new Creator<ExamInfo>() {
    @Override
    public ExamInfo createFromParcel(final Parcel in) {
      return new ExamInfo(in);
    }

    @Override
    public ExamInfo[] newArray(final int size) {
      return new ExamInfo[size];
    }
  };

  public String getCourse() {
    return mCourse;
  }

  public List<ExamSection> getSections() {
    return mSections;
  }
}
