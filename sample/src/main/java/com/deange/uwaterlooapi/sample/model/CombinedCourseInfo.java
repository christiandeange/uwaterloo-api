package com.deange.uwaterlooapi.sample.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.courses.CourseInfo;
import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.model.courses.ExamInfo;
import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo;
import java.util.ArrayList;
import java.util.List;

public class CombinedCourseInfo
    extends BaseModel
    implements
    Parcelable {

  private Metadata mMetadata;
  private CourseInfo mCourseInfo;
  private PrerequisiteInfo mPrerequisites;
  private List<CourseSchedule> mSchedules;
  private ExamInfo mExams;

  public CombinedCourseInfo() {
  }

  protected CombinedCourseInfo(final Parcel in) {
    super(in);
    mMetadata = in.readParcelable(Metadata.class.getClassLoader());
    mCourseInfo = in.readParcelable(CourseInfo.class.getClassLoader());
    mPrerequisites = in.readParcelable(PrerequisiteInfo.class.getClassLoader());
    mSchedules = in.createTypedArrayList(CourseSchedule.CREATOR);
    mExams = in.readParcelable(ExamInfo.class.getClassLoader());
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeParcelable(mMetadata, flags);
    dest.writeParcelable(mCourseInfo, flags);
    dest.writeParcelable(mPrerequisites, flags);
    dest.writeTypedList(mSchedules);
    dest.writeParcelable(mExams, flags);
  }

  public static final Creator<CombinedCourseInfo> CREATOR = new Creator<CombinedCourseInfo>() {
    @Override
    public CombinedCourseInfo createFromParcel(final Parcel in) {
      return new CombinedCourseInfo(in);
    }

    @Override
    public CombinedCourseInfo[] newArray(final int size) {
      return new CombinedCourseInfo[size];
    }
  };

  public Metadata getMetadata() {
    return mMetadata;
  }

  public void setMetadata(Metadata metadata) {
    mMetadata = metadata;
  }

  public CourseInfo getCourseInfo() {
    return mCourseInfo;
  }

  public void setCourseInfo(final CourseInfo courseInfo) {
    mCourseInfo = courseInfo;
  }

  public PrerequisiteInfo getPrerequisites() {
    return mPrerequisites;
  }

  public void setPrerequisites(final PrerequisiteInfo prerequisites) {
    mPrerequisites = prerequisites;
  }

  public List<CourseSchedule> getSchedules() {
    return mSchedules;
  }

  public void setSchedules(final List<CourseSchedule> schedules) {
    mSchedules = new ArrayList<>(schedules);
  }

  public ExamInfo getExams() {
    return mExams;
  }

  public void setExams(final ExamInfo exams) {
    mExams = exams;
  }
}
