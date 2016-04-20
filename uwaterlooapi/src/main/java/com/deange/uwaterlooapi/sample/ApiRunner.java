package com.deange.uwaterlooapi.sample;

import android.util.Log;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.weather.LegacyWeatherReading;

/**
 * THIS CLASS IS TEMPORARY
 * IT IS FOR INITIAL DEVELOPMENT PURPOSES ONLY
 */
public final class ApiRunner {

    private ApiRunner() {
        throw new AssertionError();
    }

    public static void runAll(final UWaterlooApi api) {
//        runFoodServices(api);
//        runCourses(api);
//        runEvents(api);
//        runNews(api);
//        runWeather(api);
//        runTerms(api);
//        runResources(api);
//        runBuildings(api);
//        runParking(api);
        runPointsOfInterest(api);
    }

    private static void runFoodServices(final UWaterlooApi api) {

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

    private static void runCourses(final UWaterlooApi api) {

        final String section = "CS";
        final String courseCode = "349";
        final int courseId = 13106;
        final int courseNumber = 3545;
        final int termId = 1145;

        final Response.Courses courses = api.Courses.getCourseInfo(section);
        final Response.CoursesInfo course = api.Courses.getCourseInfo(courseId);
        final Response.CoursesSchedule schedule = api.Courses.getCourseSchedule(courseNumber, termId);
        final Response.CoursesInfo course2 = api.Courses.getCourseInfo(section, courseCode);
        final Response.CoursesSchedule schedule2 = api.Courses.getCourseSchedule(section, courseCode, termId);
        final Response.Prerequisites prerequisites = api.Courses.getPrerequisites("PHYS", "375");
        final Response.ExamSchedule examSchedule = api.Courses.getExamSchedule(section, courseCode);

        Log.v("TAG", "Courses requests completed.");
    }

    private static void runEvents(final UWaterlooApi api) {

        final int id = 1354;
        final String site = "centre-for-teaching-excellence";

        final Response.Events events = api.Events.getEvents();
        final Response.Events siteEvents = api.Events.getEvents(site);
        final Response.EventDetails details = api.Events.getEvent(site, id);

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

    private static void runWeather(final UWaterlooApi api) {

        final Response.Weather weather = api.Weather.getWeather();
        final LegacyWeatherReading legacyWeather = api.LegacyWeather.getWeather();

        Log.v("TAG", "Weather requests completed.");
    }

    private static void runTerms(final UWaterlooApi api) {

        final int termId = 1145;
        final String subject = "CS";
        final String courseCode = "349";

        final Response.Terms terms = api.Terms.getTermList();
        final Response.TermExamSchedule exams = api.Terms.getExamSchedule(termId);
        final Response.CoursesSchedule course = api.Terms.getSchedule(termId, subject);
        final Response.CoursesSchedule course2 = api.Terms.getSchedule(termId, subject, courseCode);
        final Response.InfoSessions sessions = api.Terms.getInfoSessions(termId);

        Log.v("TAG", "Terms requests completed.");
    }

    private static void runResources(final UWaterlooApi api) {

        final Response.Sites sites = api.Resources.getSites();
        final Response.Tutors tutors = api.Resources.getTutors();
        final Response.Printers printers = api.Resources.getPrinters();
        final Response.InfoSessions sessions = api.Resources.getInfoSessions();
        final Response.GooseWatch geese = api.Resources.getGeeseNests();

        Log.v("TAG", "Resources requests completed.");
    }

    private static void runBuildings(final UWaterlooApi api) {

        final String buildingCode = "MC";
        final String room = "2038";

        final Response.Buildings buildings = api.Buildings.getBuildings();
        final Response.BuildingEntity building = api.Buildings.getBuilding(buildingCode);
        final Response.RoomCourses courses = api.Buildings.getClassroomCourses(buildingCode, room);

        Log.v("TAG", "Buildings requests completed.");
    }

    private static void runParking(final UWaterlooApi api) {

        final Response.Parking parking = api.Parking.getParkingInfo();

        Log.v("TAG", "Parking requests completed.");
    }

    private static void runPointsOfInterest(final UWaterlooApi api) {

        final Response.ATMs atms = api.PointsOfInterest.getATMs();

        Log.v("TAG", "POI requests completed.");
    }

}
