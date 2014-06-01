package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.common.SimpleResponse;

import java.util.List;

public class Response {

    private Response() {
        // Not instantiable
    }

    public static class Menus extends SimpleResponse<MenuInfo> { }

    public static class Announcements extends SimpleResponse<List<Announcement>> { }

    public static class Locations extends SimpleResponse<List<Location>> { }

    public static class Diets extends SimpleResponse<List<Diet>> { }

    public static class Notes extends SimpleResponse<List<Note>> { }

    public static class Outlets extends SimpleResponse<List<Outlet>> { }

    public static class Watcards extends SimpleResponse<List<WatcardVendor>> { }

    public static class Products extends SimpleResponse<Product> { }

}
