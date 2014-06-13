package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.model.courses.Course;
import com.deange.uwaterlooapi.model.courses.CourseInfo;
import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.model.foodservices.Announcement;
import com.deange.uwaterlooapi.model.foodservices.Diet;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.model.foodservices.MenuInfo;
import com.deange.uwaterlooapi.model.foodservices.Note;
import com.deange.uwaterlooapi.model.foodservices.Outlet;
import com.deange.uwaterlooapi.model.foodservices.Product;
import com.deange.uwaterlooapi.model.foodservices.WatcardVendor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    private Response() {
        // Not instantiable
    }

    // FOOD SERVICES

    public static class Menus extends SimpleResponse<MenuInfo> { }

    public static class Announcements extends SimpleResponse<List<Announcement>> { }

    public static class Locations extends SimpleResponse<List<Location>> { }

    public static class Diets extends SimpleResponse<List<Diet>> { }

    public static class Notes extends SimpleResponse<List<Note>> { }

    public static class Outlets extends SimpleResponse<List<Outlet>> { }

    public static class Watcards extends SimpleResponse<List<WatcardVendor>> { }

    public static class Products extends SimpleResponse<Product> { }

    // COURSES

    public static class Courses extends SimpleResponse<List<Course>> { }

    public static class CoursesInfo extends SimpleResponse<CourseInfo> { }

    public static class CoursesSchedule extends BaseResponse {

        @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"})
        @SerializedName("data")
        private List<CourseSchedule> mSchedule;

        /**
         * Stupid API always returns an array with only 1 element...
         */
        public CourseSchedule getData() {
            return (mSchedule == null || mSchedule.isEmpty()) ? null : mSchedule.get(0);
        }

    }

}
