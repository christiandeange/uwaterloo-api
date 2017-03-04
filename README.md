uwaterloo-api
=============

Android wrapper for the UWaterloo Open Data API

## Download

uwaterloo-api can easily be included in your app via Gradle. Because I'm too lazy to manage a maven repository and all that nonsense, you can just pull it in via jitpack:

```groovy
repositories {
    maven {
        url 'https://jitpack.io'
    }
}

dependencies {
    compile 'com.github.cdeange:uwaterloo-api:1.0.0'
}
```

uwaterloo-api requires at minimum Java 7 and Android 4.1 Jelly Bean (API 16).

## Sample

This library usage is being demonstrated by a sample app available on Google Play. If you'd like to try out the app and see what features you can use, download the sample app.

<a href='https://play.google.com/store/apps/details?id=com.deange.uwaterlooapi.sample'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/badge_new.png'/></a>

## Usage

If you haven't registered an API key yet, you'll need to do that first. Visit the [Open Data API](https://uwaterloo.ca/api/) homepage and register an API key with your name and email. There are no usage limits with the key, but you are still subject to the UWaterloo Open Data API's terms of use and license agreement.

```java
UWaterlooApi api = new UWaterlooApi("YOUR_API_KEY");
```

Each API is accessible via one of the members of a `UWaterlooApi`.
 - [FoodServices][]
 - [Courses][]
 - [Events][]
 - [News][]
 - [Weather][]
 - [Terms][]
 - [Resources][]
 - [Buildings][]
 - [Parking][]
 - [PointsOfInterest][]
 - [LegacyWeather][]

Each API method will return a Retrofit `Call` object. Calls may be executed synchronously with `call.execute()`, or asynchronously with `call.enqueue()`. For more information on how to interact with Calls, refer to the Retrofit reference [here](https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html).

### Examples

```java
// Don't actually do this, you should always be checking for errors
public <T> T perform(Call<T> call) throws IOException {
    return call.execute().body();
}
```

Reading the current temperature:

```java
WeatherReading reading = perform(api.Weather.getWeather()).getData();
Log.v(TAG, "The current temperature is " + reading.getTemperature() + "˚C");
```

Getting information about a food spot on campus:

```java
List<Location> locations = perform(api.FoodServices.getLocations()).getData();
for (Location location : locations) {
    Log.v(TAG, location.getName() + " is currently " + (location.isOpenNow() ? "open" : "closed"));
}
```

Preparing for your morning walk to class:

```java
List<GooseNest> nests = perform(api.Resources.getGeeseNests()).getData();
for (GooseNest nest : nests) {
    Log.v(TAG, "Goose nest reported! " + nest.getLocationDescription());
}
```


### Full Method Reference

#### Food Services
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getWeeklyMenu()` | [MenuInfo][] | [/foodservices/menu](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/menu.md) |
| `getNotes()` | List\<[Note][]\> | [/foodservices/notes](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/notes.md) |
| `getDiets()` | List\<[Diet][]\> | [/foodservices/diets](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/diets.md) |
| `getOutlets()` | List\<[Outlet][]\> | [/foodservices/outlets](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/outlets.md) |
| `getLocations()` | List\<[Location][]\> | [/foodservices/locations](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/locations.md) |
| `getWatcardVendors()` | List\<[WatcardVendor][]\> | [/foodservices/watcard](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/watcard.md) |
| `getAnnouncements()` | List\<[Announcement][]\> | [/foodservices/announcements](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/announcements.md) |
| `getProduct()` | [Product][] | [/foodservices/products/{product_id}](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/products_product_id.md) |
| `getWeeklyMenu()` | [MenuInfo][] | [/foodservices/{year}/{week}/menu](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/year_week_menu.md) |
| `getNotes()` | List\<[Note][]\> | [/foodservices/{year}/{week}/notes](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/year_week_notes.md) |
| `getAnnouncements()` | List\<[Announcement][]\> | [/foodservices/{year}/{week}/announcements](https://github.com/uWaterloo/api-documentation/blob/master/v2/foodservices/year_week_announcements.md) |

#### Courses
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getCourseInfo()` | List\<[Course][]\> | [/courses/{subject}](https://github.com/uWaterloo/api-documentation/blob/master/v2/courses/subject.md) |
| `getCourseInfo()` | [CourseInfo][] | [/courses/{course_id}](https://github.com/uWaterloo/api-documentation/blob/master/v2/courses/course_id.md) |
| `getCourseSchedule()` | List\<[CourseSchedule][]\> | [/courses/{class_number}/schedule](https://github.com/uWaterloo/api-documentation/blob/master/v2/courses/class_number_schedule.md) |
| `getCourseSchedule()` | List\<[CourseSchedule][]\> | [/courses/{class_number}/schedule?term={term}](https://github.com/uWaterloo/api-documentation/blob/master/v2/courses/class_number_schedule.md) |
| `getCourseInfo()` | [CourseInfo][] | [/courses/{subject}/{catalog_number}](https://github.com/uWaterloo/api-documentation/blob/master/v2/courses/subject_catalog_number.md) |
| `getCourseSchedule()` |  List\<[CourseSchedule][]\> | [/courses/{subject}/{catalog_number}/schedule](https://github.com/uWaterloo/api-documentation/blob/master/v2/courses/subject_catalog_number_schedule.md) |
| `getCourseSchedule()` |  List\<[CourseSchedule][]\> | [/courses/{subject}/{catalog_number}/schedule?term={term}](https://github.com/uWaterloo/api-documentation/blob/master/v2/courses/subject_catalog_number_schedule.md) |
| `getPrerequisites()` | [PrerequisiteInfo][] | [/courses/{subject}/{catalog_number}/prerequisites](https://github.com/uWaterloo/api-documentation/blob/master/v2/courses/subject_catalog_number_prerequisites.md) |
| `getExamSchedule()` | [ExamInfo][] | [/courses/{subject}/{catalog_number}/examschedule](https://github.com/uWaterloo/api-documentation/blob/master/v2/courses/subject_catalog_number_examschedule.md) |

#### Events
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getEvents()` | List\<[Event][]\> | [/events](https://github.com/uWaterloo/api-documentation/blob/master/v2/events/events.md) |
| `getEvents()` | List\<[Event][]\> | [/events/{site}](https://github.com/uWaterloo/api-documentation/blob/master/v2/events/events_site.md) |
| `getEvent()` | [EventInfo][] | [/events/{site}/{id}](https://github.com/uWaterloo/api-documentation/blob/master/v2/events/events_site_id.md) |

#### News
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getNews()` | List\<[NewsDetails][]\> | [/news](https://github.com/uWaterloo/api-documentation/blob/master/v2/events/news.md) |
| `getNews()` | List\<[NewsDetails][]\> | [/news/{site}](https://github.com/uWaterloo/api-documentation/blob/master/v2/news/news_site.md) |
| `getNews()` | [NewsArticle][] | [/news/{site}/{id}](https://github.com/uWaterloo/api-documentation/blob/master/v2/news/news_site_id.md) |

#### Weather
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getWeather()` | [WeatherReading][] | [/weather/current](https://github.com/uWaterloo/api-documentation/blob/master/v2/weather/current.md) |

#### Weather (Legacy)
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getWeather()` | [LegacyWeatherReading][] | [/weather/waterloo_weather_station_data.xml](http://www.civil.uwaterloo.ca/weather/waterloo_weather_station_data.xml) |

#### Terms
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getTermList()` | [TermInfo][] | [/terms/list](https://github.com/uWaterloo/api-documentation/blob/master/v2/terms/list.md) |
| `getExamSchedule()` | List\<[ExamInfo][]\> | [/terms/{term}/examschedule](https://github.com/uWaterloo/api-documentation/blob/master/v2/terms/term_examschedule.md) |
| `getSchedule()` | List\<[CourseSchedule][]\> | [/terms/{term}/{subject}/schedule](https://github.com/uWaterloo/api-documentation/blob/master/v2/terms/term_subject_schedule.md) |
| `getSchedule()` | List\<[CourseSchedule][]\> | [/terms/{term}/{subject}/{catalog_number}/schedule](https://github.com/uWaterloo/api-documentation/blob/master/v2/terms/term_subject_catalog_number_schedule.md) |
| `getInfoSessions()` | List\<[InfoSession][]\> | [/terms/{term}/infosessions](https://github.com/uWaterloo/api-documentation/blob/master/v2/terms/term_infosessions.md) |

#### Resources
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getSites()` | List\<[Site][]\> | [/resources/sites](https://github.com/uWaterloo/api-documentation/blob/master/v2/resources/sites.md) |
| `getTutors()` | List\<[Tutor][]\> | [/resources/tutors](https://github.com/uWaterloo/api-documentation/blob/master/v2/resources/tutors.md) |
| `getPrinters()` | List\<[Printer][]\> | [/resources/printers](https://github.com/uWaterloo/api-documentation/blob/master/v2/resources/printers.md) |
| `getInfoSessions()` | List\<[InfoSession][]\> | [/resources/infosessions](https://github.com/uWaterloo/api-documentation/blob/master/v2/resources/infosessions.md) |
| `getGeeseNests()` | List\<[GooseNest][]\> | [/resources/goosewatch](https://github.com/uWaterloo/api-documentation/blob/master/v2/resources/goosewatch.md) |
| `getSunshineList()` | List\<[Sunshiner][]\> | [/resources/sunshinelist](https://github.com/uWaterloo/api-documentation/blob/master/v2/resources/sunshinelist.md) |

#### Buildings
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getBuildings()` | List\<[Building][]\> | [/buildings/list](https://github.com/uWaterloo/api-documentation/blob/master/v2/buildings/list.md) |
| `getBuilding()` | [Building][] | [/buildings/{building_code}](https://github.com/uWaterloo/api-documentation/blob/master/v2/buildings/building_acronym.md) |
| `getClassroomCourses()` | List\<[ClassroomCourses][]\> | [/buildings/{building_code}/{room}/courses](https://github.com/uWaterloo/api-documentation/blob/master/v2/buildings/building_acronym_room_number_courses.md) |

#### Parking
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getParkingInfo()` | List\<[ParkingLot][]\> | [/parking/watpark](https://github.com/uWaterloo/api-documentation/blob/master/v2/parking/watpark.md) |

#### Points Of Interest
| Method | Return Type | Endpoint |
|--------|-------------|----------|
| `getATMs()` | List\<[ATM][]\> | [/poi/atms](https://github.com/uWaterloo/api-documentation/blob/master/v2/poi/atms.md) |
| `getGreyhoundStops()` | List\<[GreyhoundStop][]\> | [/poi/greyhound](https://github.com/uWaterloo/api-documentation/blob/master/v2/poi/greyhound.md) |
| `getPhotospheres()` | List\<[Photosphere][]\> | [/poi/photospheres](https://github.com/uWaterloo/api-documentation/blob/master/v2/poi/photospheres.md) |
| `getHelplines()` | List\<[Helpline][]\> | [/poi/helplines](https://github.com/uWaterloo/api-documentation/blob/master/v2/poi/helplines.md) |
| `getLibraries()` | List\<[Library][]\> | [/poi/libraries](https://github.com/uWaterloo/api-documentation/blob/master/v2/poi/libraries.md) |
| `getDefibrillators()` | List\<[Defibrillator][]\> | [/poi/defibrillators](https://github.com/uWaterloo/api-documentation/blob/master/v2/poi/defibrillators.md) |

## Contributing

Feel free to submit any pull requests or issues to this repository as you please. However, please keep it limited to things that directly address something directly provided by either the uwaterloo-api library or the sample app. If there is an issue with the actual data being returned by the API, please refer to the lovely maintainers over at the [Open Data API documentation](https://github.com/uWaterloo/api-documentation).

## License

```
Copyright (c) 2016 Christian De Angelis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```


[FoodServices]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/FoodServicesApi.java
[Courses]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/CoursesApi.java
[Events]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/EventsApi.java
[News]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/NewsApi.java
[Weather]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/WeatherApi.java
[Terms]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/TermsApi.java
[Resources]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/ResourcesApi.java
[Buildings]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/BuildingsApi.java
[Parking]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/ParkingApi.java
[PointsOfInterest]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/PointsOfInterestApi.java
[LegacyWeather]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/api/LegacyWeatherApi.java

[MenuInfo]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/foodservices/MenuInfo.java
[Note]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/foodservices/Note.java
[Diet]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/foodservices/Diet.java
[Outlet]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/foodservices/Outlet.java
[Location]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/foodservices/Location.java
[WatcardVendor]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/foodservices/WatcardVendor.java
[Announcement]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/foodservices/Announcement.java
[Product]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/foodservices/Product.java
[Course]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/courses/Course.java
[CourseInfo]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/courses/CourseInfo.java
[CourseSchedule]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/courses/CourseSchedule.java
[PrerequisiteInfo]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/courses/PrerequisiteInfo.java
[ExamInfo]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/courses/ExamInfo.java
[Event]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/events/Event.java
[EventInfo]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/events/EventInfo.java
[NewsDetails]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/news/NewsDetails.java
[NewsArticle]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/news/NewsArticle.java
[WeatherReading]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/weather/WeatherReading.java
[LegacyWeatherReading]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/weather/LegacyWeatherReading.java
[TermInfo]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/terms/TermInfo.java
[InfoSession]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/terms/InfoSession.java
[Site]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/resources/Site.java
[Tutor]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/resources/Tutor.java
[Printer]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/resources/Printer.java
[GooseNest]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/resources/GooseNest.java
[Sunshiner]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/resources/Sunshiner.java
[Building]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/buildings/Building.java
[ClassroomCourses]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/buildings/ClassroomCourses.java
[ParkingLot]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/parking/ParkingLot.java
[ATM]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/poi/ATM.java
[GreyhoundStop]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/poi/GreyhoundStop.java
[Photosphere]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/poi/Photosphere.java
[Helpline]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/poi/Helpline.java
[Library]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/poi/Library.java
[Defibrillator]: https://github.com/cdeange/uwaterloo-api/blob/master/uwaterlooapi-library/src/main/java/com/deange/uwaterlooapi/model/poi/Defibrillator.java