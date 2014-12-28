package com.deange.uwaterlooapi.model.common;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class DummyResponse<T> extends SimpleResponse<T> {

    private final T mObj;

    private DummyResponse(final T obj) {
        mObj = obj;
    }

    public static <T> DummyResponse<T> inject(final T obj) {
        return new DummyResponse<>(obj);
    }

    @Override
    public T getData() {
        return mObj;
    }

    public static final class Serializer<T>
            implements JsonSerializer<DummyResponse<T>>, JsonDeserializer<DummyResponse<T>> {

        private static final String KEY_CLASS = "clazz";
        private static final String KEY_DATA = "data";
        private static final Gson sGson = new Gson();

        @Override
        public JsonElement serialize(final DummyResponse<T> src, final Type typeOfSrc,
                                     final JsonSerializationContext context) {
            final String clazz = src.mObj.getClass().getName();
            final String data = sGson.toJson(src.mObj);

            final JsonObject element = new JsonObject();
            element.addProperty(KEY_DATA, data);
            element.addProperty(KEY_CLASS, clazz);
            return element;
        }

        @Override
        public DummyResponse<T> deserialize(final JsonElement json, final Type typeOfT,
                                            final JsonDeserializationContext context) {
            final String clazz = json.getAsJsonObject().get(KEY_CLASS).getAsString();
            final String data = json.getAsJsonObject().get(KEY_DATA).getAsString();
            final T obj;
            try {
                 obj = (T) sGson.fromJson(data, Class.<T>forName(clazz));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return DummyResponse.<T>inject(null);
            }
            return DummyResponse.inject(obj);
        }
    }

}
