package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.BaseModel;
import com.deange.uwaterlooapi.model.buildings.Building;
import com.deange.uwaterlooapi.model.buildings.ClassroomCourses;
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
import com.deange.uwaterlooapi.model.parking.ParkingLot;
import com.deange.uwaterlooapi.model.resources.GooseNest;
import com.deange.uwaterlooapi.model.resources.Printer;
import com.deange.uwaterlooapi.model.resources.Site;
import com.deange.uwaterlooapi.model.resources.Tutor;
import com.deange.uwaterlooapi.model.terms.InfoSession;
import com.deange.uwaterlooapi.model.terms.TermInfo;
import com.deange.uwaterlooapi.model.weather.WeatherReading;

public final class Response {

    private Response() {
        // Not instantiable
    }

    public static class Empty extends SimpleResponse<BaseModel> { }

    // FOOD SERVICES

    public static class Menus extends SimpleResponse<MenuInfo> { }

    public static class Announcements extends SimpleListResponse<Announcement> { }

    public static class Locations extends SimpleListResponse<Location> { }

    public static class Diets extends SimpleListResponse<Diet> { }

    public static class Notes extends SimpleListResponse<Note> { }

    public static class Outlets extends SimpleListResponse<Outlet> { }

    public static class Watcards extends SimpleListResponse<WatcardVendor> { }

    public static class Products extends SimpleResponse<Product> { }

    // COURSES

    public static class Courses extends SimpleListResponse<Course> { }

    public static class CoursesInfo extends SimpleResponse<CourseInfo> { }

    public static class CoursesSchedule extends SimpleListResponse<CourseSchedule> { }

    public static class Prerequisites extends SimpleResponse<PrerequisiteInfo> { }

    public static class ExamSchedule extends SimpleResponse<ExamInfo> { }

    // EVENTS

    public static class Events extends SimpleListResponse<Event> { }

    public static class EventDetails extends SimpleResponse<EventInfo> { }

    public static class News extends SimpleListResponse<NewsDetails> { }

    public static class NewsEntity extends SimpleResponse<NewsArticle> { }

    // WEATHER

    public static class Weather extends SimpleResponse<WeatherReading> { }

    // TERMS

    public static class Terms extends SimpleResponse<TermInfo> { }

    public static class TermExamSchedule extends SimpleListResponse<ExamInfo> { }

    public static class InfoSessions extends SimpleListResponse<InfoSession> { }

    // RESOURCES

    public static class Sites extends SimpleListResponse<Site> { }

    public static class Tutors extends SimpleListResponse<Tutor> { }

    public static class Printers extends SimpleListResponse<Printer> { }

    public static class GooseWatch extends SimpleListResponse<GooseNest> { }

    // BUILDINGS

    public static class Buildings extends SimpleListResponse<Building> { }

    public static class BuildingEntity extends SimpleResponse<Building> { }

    public static class RoomCourses extends SimpleListResponse<ClassroomCourses> { }

    // PARKING

    public static class Parking extends SimpleListResponse<ParkingLot> { }

}
