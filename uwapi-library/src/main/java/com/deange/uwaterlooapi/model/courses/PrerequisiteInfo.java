package com.deange.uwaterlooapi.model.courses;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.utils.CollectionUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Parcel
public class PrerequisiteInfo extends BaseModel {

    @SerializedName("subject")
    String mSubject;

    @SerializedName("catalog_number")
    String mCatalogNumber;

    @SerializedName("title")
    String mTitle;

    @SerializedName("prerequisites")
    String mPrerequisites;

    @SerializedName("prerequisites_parsed")
    PrerequisiteGroup mPrerequisiteGroup;

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
     * Class name and title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Raw listing of course prerequisites
     */
    public String getPrerequisites() {
        return mPrerequisites;
    }

    /**
     * Parsed prerequisites
     */
    public PrerequisiteGroup getPrerequisiteGroup() {
        return mPrerequisiteGroup;
    }

    /**
     * Determine if the set of provided courses satisfies the prerequisite requirements
     */
    public boolean prerequisitesSatisfied(final List<String> courses) {
        return mPrerequisiteGroup.prerequisitesSatisfied(new ArrayList<>(courses));
    }

    public static class Converter implements JsonDeserializer<PrerequisiteGroup> {

        @Override
        public PrerequisiteGroup deserialize(final JsonElement json, final Type typeOfT,
                                             final JsonDeserializationContext context) {
            return parseGroup(json.getAsJsonArray());
        }

        private PrerequisiteGroup parseGroup(final JsonArray elements) {

            final PrerequisiteGroup group = new PrerequisiteGroup();
            for (Object item : elements) {

                if (item instanceof JsonPrimitive) {
                    JsonPrimitive primitive = (JsonPrimitive) item;
                    if (primitive.isNumber()) {
                        group.mTotal = primitive.getAsInt();

                    } else if (primitive.isString()) {
                        group.mOptions.add(primitive.getAsString());
                    }

                } else if (item instanceof JsonArray) {
                    PrerequisiteGroup subGroups = parseGroup(((JsonArray) item));
                    if (subGroups != null) {
                        group.mSubOptions.add(subGroups);
                    }
                }

            }

            if (group.mSubOptions.isEmpty() && group.mOptions.isEmpty()) {
                group.mTotal = 0;
            }

            return group;
        }

    }

    @Parcel
    public static final class PrerequisiteGroup {

        // Sometimes a number is not provided, so we assume it is 1
        int mTotal = 1;

        List<String> mOptions = new ArrayList<>();

        List<PrerequisiteGroup> mSubOptions = new ArrayList<>();

        /**
         * The number of courses required to satisfy this group
         */
        public int getTotalRequiredCourses() {
            return mTotal;
        }

        /**
         * Prerequisite courses for this group
         */
        public List<String> getOptions() {
            return CollectionUtils.applyPolicy(mOptions);
        }

        /**
         * Other subgroups that can satisfy as a prerequisite for this group
         */
        public List<PrerequisiteGroup> getSubOptions() {
            return CollectionUtils.applyPolicy(mSubOptions);
        }

        /**
         * Determine if the set of provided courses satisfies the prerequisite requirements
         * for this group (supports recursive satisfiability)
         */
        public boolean prerequisitesSatisfied(final List<String> courses) {

            // Early exit
            if (mTotal == 0) return true;

            // Find the intersection of prerequisite courses and courses provided
            int satisfied = 0;
            final List<String> copy = new ArrayList<>(courses);
            if (copy.retainAll(mOptions)) {
                satisfied = copy.size();
            }

            // Courses alone fulfill prerequisite requirements
            if (satisfied >= mTotal) return true;

            // Retain only unused courses that weren't spent up
            courses.removeAll(copy);

            for (final PrerequisiteGroup subGroup : mSubOptions) {
                if (subGroup.prerequisitesSatisfied(courses)) {
                    if (++satisfied >= mTotal) {
                        return true;
                    }
                }
            }

            // Not enough courses to pass requisites
            return false;
        }
    }
}
