package com.deange.uwaterlooapi.sample.ui.flow;

import android.os.Parcel;
import android.os.Parcelable;
import flow.StateParceler;
import java.lang.reflect.Array;

public class KeyPathParceler implements StateParceler {

  public static final KeyPathParceler INSTANCE = new KeyPathParceler();

  @Override
  public Parcelable wrap(Object instance) {
    return (KeyPath) instance;
  }

  @Override
  public Object unwrap(Parcelable parcelable) {
    return parcelable;
  }

  public static <T extends KeyPath> Parcelable.Creator<T> forInstance(T instance) {
    return new Parcelable.Creator<T>() {
      @Override
      public T createFromParcel(Parcel source) {
        return instance;
      }

      @Override
      public T[] newArray(int size) {
        //noinspection unchecked
        return (T[]) Array.newInstance(instance.getClass(), size);
      }
    };
  }
}
