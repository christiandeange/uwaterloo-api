package com.deange.uwaterlooapi;

import com.deange.uwaterlooapi.model.converter.CustomConverterFactory;
import com.deange.uwaterlooapi.model.courses.CourseLocations;
import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo;
import com.deange.uwaterlooapi.model.courses.PrerequisiteInfo.PrerequisiteGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/* package */ class ApiModelConverter {

  static Converter.Factory newCustomInstance() {
    return CustomConverterFactory.create();
  }

  static Converter.Factory newGsonInstance() {
    return GsonConverterFactory.create(getGson());
  }

  static Converter.Factory newXmlInstance() {
    return SimpleXmlConverterFactory.create();
  }

  private static Gson getGson() {
    return new GsonBuilder()
        .setVersion(ApiBuilder.VERSION)
        .registerTypeAdapter(CourseLocations.class, new CourseLocations.Converter())
        .registerTypeAdapter(PrerequisiteGroup.class, new PrerequisiteInfo.Converter())
        .create();
  }

}
