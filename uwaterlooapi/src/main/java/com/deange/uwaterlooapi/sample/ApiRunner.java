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

    public static void runAll(final UWaterlooApi api) {
//        runFoodServices(api);
//        runCourses(api);
//        runEvents(api);
        runNews(api);
    }

    public static void runFoodServices(final UWaterlooApi api) {

        final int year = 2014;
        final int week = 10;

        final Response.Menus menu = api.FoodServices.getWeeklyMenu();
        final Response.Menus menu2 = api.FoodServices.getWeeklyMenu(year, week);
        final Response.Notes notes = api.FoodServices.getNotes();
        final Response.Notes notes2 = api.FoodServices.getNotes(year, week);
        final Response.Diets diets = api.FoodServices.getDiets();
        final Response.Outlets outlets = api.FoodServices.getOutlets();
        final Response.Locations locations = api.FoodServices.getLocations();
        final Response.Watcards watcard = api.FoodServices.getWatcardVendors();
        final Response.Announcements announcements = api.FoodServices.getAnnouncements();
        final Response.Announcements announcements2 = api.FoodServices.getAnnouncements(year, week);
        final Response.Products product = api.FoodServices.getProduct(1386);

        Log.v("TAG", "FoodServices requests completed.");
    }

    public static void runCourses(final UWaterlooApi api) {

        final String section = "CS";
        final String courseCode = "349";
        final int courseId = 13106;
        final int courseNumber = 3545;
        final int termId = 1145;

        final Response.Courses courses = api.CoursesApi.getCourseInfo(section);
        final Response.CoursesInfo course = api.CoursesApi.getCourseInfo(courseId);
        final Response.CoursesSchedule schedule = api.CoursesApi.getCourseSchedule(courseNumber, termId);
        final Response.CoursesInfo course2 = api.CoursesApi.getCourseInfo(section, courseCode);
        final Response.CoursesSchedule schedule2 = api.CoursesApi.getCourseSchedule(section, courseCode, termId);
        final Response.Prerequisites prerequisites = api.CoursesApi.getPrerequisites("PHYS", "375");
        final Response.ExamSchedule examSchedule = api.CoursesApi.getExamSchedule(section, courseCode);

        Log.v("TAG", "Courses requests completed.");
    }

    private static void runEvents(final UWaterlooApi api) {

        final int id = 1354;
        final String site = "centre-for-teaching-excellence";

        final Response.Events events = api.Events.getEvents();
        final Response.Events siteEvents = api.Events.getEvents(site);
        final Response.EventDetails details = api.Events.getEvents(site, id);

        Log.v("TAG", "Events requests completed.");
    }

    private static void runNews(final UWaterlooApi api) {

        final int id = 196;
        final String site = "games-institute";

        final Response.News news = api.News.getNews();
        final Response.News siteNews = api.News.getNews(site);
        final Response.NewsEntity entity = api.News.getNews(site, id);

        Log.v("TAG", "News requests completed.");
    }

}
