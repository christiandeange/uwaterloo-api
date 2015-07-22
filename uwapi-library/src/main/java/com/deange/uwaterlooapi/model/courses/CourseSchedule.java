package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.deange.uwaterlooapi.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class CourseSchedule extends BaseModel {

    @SerializedName("subject")
    String mSubject;

    @SerializedName("catalog_number")
    String mCatalogNumber;

    @SerializedName("units")
    float mUnits;

    @SerializedName("title")
    String mTitle;

    @SerializedName("note")
    String mNotes;

    @SerializedName("class_number")
    int mClasNumber;

    @SerializedName("section")
    String mSection;

    @SerializedName("campus")
    String mCampus;

    @SerializedName("associated_class")
    int mAssociatedClassId;

    @SerializedName("related_component_1")
    String mRelatedComponent1;

    @SerializedName("related_component_2")
    String mRelatedComponent2;

    @SerializedName("enrollment_capacity")
    int mEnrollmentCapacity;

    @SerializedName("enrollment_total")
    int mEnrollmentTotal;

    @SerializedName("waiting_capacity")
    int mWaitingCapacity;

    @SerializedName("waiting_total")
    int mWaitingTotal;

    @SerializedName("topic")
    String mTopic;

    @SerializedName("reserves")
    List<Reserve> mReserves;

    @SerializedName("classes")
    List<Class> mClasses;

    @SerializedName("held_with")
    List<String> mHeldWith;

    @SerializedName("term")
    int mTerm;

    @SerializedName("academic_level")
    String mAcademicLevel;

    @SerializedName("last_updated")
    String mLastUpdated;

    /**
     * Requested subject acronym
     */
    public String getSubject() {
        return mSubject;
    }

    /**
     * Registrar assigned class number
     */
    public String getCatalogNumber() {
        return mCatalogNumber;
    }

    /**
     * Credit count for the mentioned course
     */
    public float getUnits() {
        return mUnits;
    }

    /**
     * Class name and title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Additional notes regarding enrollment for the given term
     */
    public String getNotes() {
        return mNotes;
    }

    /**
     * Associated term specific class enrollment number
     */
    public int getClasNumber() {
        return mClasNumber;
    }

    /**
     * Class instruction and number
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Name of the campus the course is being offered
     */
    public String getCampus() {
        return mCampus;
    }

    /**
     * ID of the associated class
     */
    public int getAssociatedClassId() {
        return mAssociatedClassId;
    }

    /**
     * Name of the first related course component
     */
    public String getRelatedComponent1() {
        return mRelatedComponent1;
    }

    /**
     * Name of the second related course component
     */
    public String getRelatedComponent2() {
        return mRelatedComponent2;
    }

    /**
     * Class enrollment capacity
     */
    public int getEnrollmentCapacity() {
        return mEnrollmentCapacity;
    }

    /**
     * Total current class enrollment
     */
    public int getEnrollmentTotal() {
        return mEnrollmentTotal;
    }

    /**
     * Class waiting capacity
     */
    public int getWaitingCapacity() {
        return mWaitingCapacity;
    }

    /**
     * Total current waiting students
     */
    public int getWaitingTotal() {
        return mWaitingTotal;
    }

    /**
     * Class discussion topic
     */
    public String getTopic() {
        return mTopic;
    }

    /**
     * Course specific enrollment reservation data
     */
    public List<Reserve> getReserves() {
        return CollectionUtils.applyPolicy(mReserves);
    }

    /**
     * Schedule data
     */
    public List<Class> getClasses() {
        return CollectionUtils.applyPolicy(mClasses);
    }

    /**
     * A list of classes the course is held with
     */
    public List<String> getHeldWith() {
        return CollectionUtils.applyPolicy(mHeldWith);
    }

    /**
     * 4 digit term representation
     */
    public int getTerm() {
        return mTerm;
    }

    /**
     * Undergraduate or graduate course classification
     */
    public String getAcademicLevel() {
        return mAcademicLevel;
    }

    /**
     * ISO8601 timestamp of when the data was last updated
     */
    public Date getLastUpdated() {
        return Formatter.parseDate(mLastUpdated, Formatter.ISO8601);
    }

    /**
     * ISO8601 timestamp of when the data was last updated as a string
     */
    public String getRawLastUpdated() {
        return mLastUpdated;
    }

}
