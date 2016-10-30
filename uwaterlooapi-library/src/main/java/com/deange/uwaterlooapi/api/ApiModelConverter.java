package com.deange.uwaterlooapi.api;

import com.deange.uwaterlooapi.model.courses.CourseLocations;
import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo;
import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo.PrerequisiteGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiModelConverter {

    public static Converter.Factory newGsonInstance() {
        return GsonConverterFactory.create(getGson());
    }

    public static SimpleXmlConverterFactory newXmlInstance() {
        return SimpleXmlConverterFactory.create();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .setVersion(ApiBuilder.VERSION)
                .registerTypeAdapter(CourseLocations.class, new CourseLocations.Converter())
                .registerTypeAdapter(PrerequisiteGroup.class, new PrerequisiteInfo.Converter())
                .create();
    }

}
