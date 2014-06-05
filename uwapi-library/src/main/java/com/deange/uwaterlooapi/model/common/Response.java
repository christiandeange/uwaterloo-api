package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.common.SimpleResponse;
import com.deange.uwaterlooapi.model.courses.Course;
import com.deange.uwaterlooapi.model.foodservices.Announcement;
import com.deange.uwaterlooapi.model.foodservices.Diet;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.model.foodservices.MenuInfo;
import com.deange.uwaterlooapi.model.foodservices.Note;
import com.deange.uwaterlooapi.model.foodservices.Outlet;
import com.deange.uwaterlooapi.model.foodservices.Product;
import com.deange.uwaterlooapi.model.foodservices.WatcardVendor;

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

}
