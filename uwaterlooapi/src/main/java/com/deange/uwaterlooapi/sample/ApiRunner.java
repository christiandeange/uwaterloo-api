package com.deange.uwaterlooapi.sample;

import android.util.Log;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.common.Response;

/**
 * THIS CLASS IS TEMPORARY
 * IT IS FOR INITIAL DEVELOPMENT PURPOSES ONLY
 */
public final class ApiRunner {

    private ApiRunner() {
        // Uninstantiable
    }

    public static void runAll() {
//        runFoodServices();
        runCourses();
    }

    public static void runFoodServices() {

        final int year = 2014;
        final int week = 10;

        final Response.Menus menu = UWaterlooApi.FoodServices.getWeeklyMenu();
        final Response.Menus menu2 = UWaterlooApi.FoodServices.getWeeklyMenu(year, week);
        final Response.Notes notes = UWaterlooApi.FoodServices.getNotes();
        final Response.Notes notes2 = UWaterlooApi.FoodServices.getNotes(year, week);
        final Response.Diets diets = UWaterlooApi.FoodServices.getDiets();
        final Response.Outlets outlets = UWaterlooApi.FoodServices.getOutlets();
        final Response.Locations locations = UWaterlooApi.FoodServices.getLocations();
        final Response.Watcards watcard = UWaterlooApi.FoodServices.getWatcardVendors();
        final Response.Announcements announcements = UWaterlooApi.FoodServices.getAnnouncements();
        final Response.Announcements announcements2 = UWaterlooApi.FoodServices.getAnnouncements(year, week);
        final Response.Products product = UWaterlooApi.FoodServices.getProduct(1386);

        Log.v("TAG", "FoodServices requests completed.");
    }

    public static void runCourses() {

        final String section = "CS";
        final int courseCode = 349;
        final int courseId = 13106;

        final Response.Courses courses = UWaterlooApi.CoursesApi.getCourseInfo(section);
        final Response.CoursesInfo course = UWaterlooApi.CoursesApi.getCourseInfo(courseId);

        Log.v("TAG", "Courses requests completed.");
    }

}
