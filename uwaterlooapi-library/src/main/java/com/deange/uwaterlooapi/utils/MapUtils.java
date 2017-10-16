package com.deange.uwaterlooapi.utils;

import android.os.Parcel;

import com.deange.uwaterlooapi.model.BaseModel;

import java.util.Map;

public final class MapUtils {

  private MapUtils() {
    throw new AssertionError();
  }

  public static <K, V> void writeMap(final Parcel dest, final Map<K, V> map) {
    if (map == null) {
      dest.writeInt(-1);
    } else {
      dest.writeInt(map.size());
      for (Map.Entry<K, V> entry : map.entrySet()) {
        dest.writeValue(entry.getKey());
        dest.writeValue(entry.getValue());
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> readMap(final Parcel in, final Map<K, V> map) {
    final int size = in.readInt();
    if (size == -1) {
      return null;
    }

    for (int i = 0; i < size; ++i) {
      map.put(
          (K) in.readValue(BaseModel.class.getClassLoader()),
          (V) in.readValue(BaseModel.class.getClassLoader())
      );
    }

    return map;
  }

}
