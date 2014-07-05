package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.courses.Course;
import com.deange.uwaterlooapi.model.courses.CourseInfo;
import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.model.courses.ExamInfo;
import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo;
import com.deange.uwaterlooapi.model.events.Event;
import com.deange.uwaterlooapi.model.events.EventInfo;
import com.deange.uwaterlooapi.model.foodservices.Announcement;
import com.deange.uwaterlooapi.model.foodservices.Diet;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.model.foodservices.MenuInfo;
import com.deange.uwaterlooapi.model.foodservices.Note;
import com.deange.uwaterlooapi.model.foodservices.Outlet;
import com.deange.uwaterlooapi.model.foodservices.Product;
import com.deange.uwaterlooapi.model.foodservices.WatcardVendor;
import com.deange.uwaterlooapi.model.news.NewsArticle;
import com.deange.uwaterlooapi.model.news.NewsDetails;
import com.deange.uwaterlooapi.model.resources.GooseNest;
import com.deange.uwaterlooapi.model.resources.Printer;
import com.deange.uwaterlooapi.model.resources.Tutor;
import com.deange.uwaterlooapi.model.terms.InfoSession;
import com.deange.uwaterlooapi.model.terms.TermInfo;
import com.deange.uwaterlooapi.model.weather.WeatherReading;

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

    public static class CoursesSchedule extends SimpleResponse<List<CourseSchedule>> { }

    public static class Prerequisites extends SimpleResponse<PrerequisiteInfo> { }

    public static class ExamSchedule extends SimpleResponse<ExamInfo> { }

    // EVENTS

    public static class Events extends SimpleResponse<List<Event>> { }

    public static class EventDetails extends SimpleResponse<EventInfo> { }

    public static class News extends SimpleResponse<List<NewsDetails>> { }

    public static class NewsEntity extends SimpleResponse<NewsArticle> { }

    // WEATHER

    public static class Weather extends SimpleResponse<WeatherReading> { }

    // TERMS

    public static class Terms extends SimpleResponse<TermInfo> { }

    public static class TermExamSchedule extends SimpleResponse<List<ExamInfo>> { }

    public static class InfoSessions extends SimpleResponse<List<InfoSession>> { }

    // RESOURCES

    public static class Tutors extends SimpleResponse<List<Tutor>> { }

    public static class Printers extends SimpleResponse<List<Printer>> { }

    public static class GooseWatch extends SimpleResponse<List<GooseNest>> { }

    // BUILDINGS

    public static class Buildings extends SimpleResponse<List<Building>> { }

    public static class BuildingEntity extends SimpleResponse<Building> { }

}
