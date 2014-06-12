package com.deange.uwaterlooapi.model.courses;

import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class CourseInfo extends Course {

    @SerializedName("instructions")
    private List<String> mInstructions;

    @SerializedName("prerequisites")
    private String mPrerequisites;

    @SerializedName("antirequisites")
    private String mAntirequisites;

    @SerializedName("corequisites")
    private String mCorequisites;

    @SerializedName("crosslistings")
    private String mCrosslistings;

    @SerializedName("terms_offered")
    private List<String> mTermsOffered;

    @SerializedName("notes")
    private String mNotes;

    @SerializedName("offerings")
    private CourseLocations mCourseLocations;

    @SerializedName("needs_department_consent")
    private boolean mNeedsDepartmentConsent;

    @SerializedName("needs_instructor_consent")
    private boolean mNeedsInstructorConsent;

    @SerializedName("extra")
    private List<String> mExtraInfo;

    @SerializedName("calendar_year")
    private String mYear;

    @SerializedName("url")
    private String mUrl;

    /**
     * Instruction types for the course (LEC, TUT, LAB etc)
     */
    public List<String> getInstructions() {
        return Collections.unmodifiableList(mInstructions);
    }

    /**
     * Prerequisite listing for the course
     */
    public String getPrerequisites() {
        return mPrerequisites;
    }

    /**
     * Antirequisite listing for the course
     */
    public String getAntirequisites() {
        return mAntirequisites;
    }

    /**
     * Corequisite listing for the course
     */
    public String getCorequisites() {
        return mCorequisites;
    }

    /**
     * Crosslisted courses
     */
    public String getCrosslistings() {
        return mCrosslistings;
    }

    /**
     * List of terms that the course is offered
     */
    public List<String> getTermsOffered() {
        return Collections.unmodifiableList(mTermsOffered);
    }

    /**
     * Additional notes on the course
     */
    public String getNotes() {
        return mNotes;
    }

    /**
     * Test for where a course is offered.
     * <p />
     * Must be one of the constants defined in {@link CourseLocations}.
     *
     * @see CourseLocations#ONLINE
     * @see CourseLocations#ONLINE_ONLY
     * @see CourseLocations#ST_JEROME
     * @see CourseLocations#ST_JEROME_ONLY
     * @see CourseLocations#RENISON
     * @see CourseLocations#RENISON_ONLY
     * @see CourseLocations#CONGRAD_GREBEL
     * @see CourseLocations#CONGRAD_GREBEL_ONLY
     *
     */
    public boolean isOfferedAt(final String offering) {
        return mCourseLocations.isOfferedAt(offering);
    }

    /**
     * Does enrollment require the department's permission
     */
    public boolean isDepartmentConsentRequired() {
        return mNeedsDepartmentConsent;
    }

    /**
     * Does enrollment require instructor's consent
     */
    public boolean isInstructorConsentRequired() {
        return mNeedsInstructorConsent;
    }

    /**
     * Any additional information associated with the course
     */
    public List<String> getExtraInfo() {
        return Collections.unmodifiableList(mExtraInfo);
    }

    /**
     * Last active year the course was offered
     */
    public String getYear() {
        return mYear;
    }

    /**
     * Last active year the course was offered
     * <p />
     * NOTE: EXPERIMENTAL. NO GUARANTEES MADE ABOUT VALIDITY
     */
    public Pair<Integer, Integer> getYearRange() {

        if (TextUtils.isEmpty(mYear) || mYear.length() != 4) {
            // fail fast
            return null;
        }

        final String first  = "20" + mYear.substring(0, 2);
        final String second = "20" + mYear.substring(2, 4);

        try {
            return Pair.create(Integer.parseInt(first), Integer.parseInt(second));

        } catch (final NumberFormatException e) {
            Log.w("CourseInfo", "Unexpected academic year: \'" + mYear + "\'", e);
            return null;
        }
    }

    /**
     * Course URL on the course calendar
     */
    public String getUrl() {
        return mUrl;
    }
}
