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
import com.deange.uwaterlooapi.model.poi.ATM;
import com.deange.uwaterlooapi.model.poi.Defibrillator;
import com.deange.uwaterlooapi.model.poi.GreyhoundStop;
import com.deange.uwaterlooapi.model.poi.Helpline;
import com.deange.uwaterlooapi.model.poi.Library;
import com.deange.uwaterlooapi.model.poi.Photosphere;
import com.deange.uwaterlooapi.model.resources.GooseNest;
import com.deange.uwaterlooapi.model.resources.Printer;
import com.deange.uwaterlooapi.model.resources.Site;
import com.deange.uwaterlooapi.model.resources.Sunshiner;
import com.deange.uwaterlooapi.model.resources.Tutor;
import com.deange.uwaterlooapi.model.terms.InfoSession;
import com.deange.uwaterlooapi.model.terms.TermInfo;
import com.deange.uwaterlooapi.model.weather.WeatherReading;

public interface Responses {

  class Empty extends SimpleResponse<BaseModel> {
  }

  // FOOD SERVICES

  class Menus extends SimpleResponse<MenuInfo> {
  }

  class Announcements extends SimpleListResponse<Announcement> {
  }

  class Locations extends SimpleListResponse<Location> {
  }

  class Diets extends SimpleListResponse<Diet> {
  }

  class Notes extends SimpleListResponse<Note> {
  }

  class Outlets extends SimpleListResponse<Outlet> {
  }

  class Watcards extends SimpleListResponse<WatcardVendor> {
  }

  class Products extends SimpleResponse<Product> {
  }

  // COURSES

  class Courses extends SimpleListResponse<Course> {
  }

  class CoursesInfo extends SimpleResponse<CourseInfo> {
  }

  class CoursesSchedule extends SimpleListResponse<CourseSchedule> {
  }

  class Prerequisites extends SimpleResponse<PrerequisiteInfo> {
  }

  class ExamSchedule extends SimpleResponse<ExamInfo> {
  }

  // EVENTS

  class Events extends SimpleListResponse<Event> {
  }

  class EventDetails extends SimpleResponse<EventInfo> {
  }

  class News extends SimpleListResponse<NewsDetails> {
  }

  class NewsEntity extends SimpleResponse<NewsArticle> {
  }

  // WEATHER

  class Weather extends SimpleResponse<WeatherReading> {
  }

  // TERMS

  class Terms extends SimpleResponse<TermInfo> {
  }

  class TermExamSchedule extends SimpleListResponse<ExamInfo> {
  }

  class InfoSessions extends SimpleListResponse<InfoSession> {
  }

  // RESOURCES

  class Sites extends SimpleListResponse<Site> {
  }

  class Tutors extends SimpleListResponse<Tutor> {
  }

  class Printers extends SimpleListResponse<Printer> {
  }

  class GooseWatch extends SimpleListResponse<GooseNest> {
  }

  class Sunshine extends SimpleListResponse<Sunshiner> {
  }

  // BUILDINGS

  class Buildings extends SimpleListResponse<Building> {
  }

  class BuildingEntity extends SimpleResponse<Building> {
  }

  class RoomCourses extends SimpleListResponse<ClassroomCourses> {
  }

  // PARKING

  class Parking extends SimpleListResponse<ParkingLot> {
  }

  // POINTS OF INTEREST

  class ATMs extends SimpleListResponse<ATM> {
  }

  class Greyhound extends SimpleListResponse<GreyhoundStop> {
  }

  class Photospheres extends SimpleListResponse<Photosphere> {
  }

  class Helplines extends SimpleListResponse<Helpline> {
  }

  class Libraries extends SimpleListResponse<Library> {
  }

  class Defibrillators extends SimpleListResponse<Defibrillator> {
  }

}
