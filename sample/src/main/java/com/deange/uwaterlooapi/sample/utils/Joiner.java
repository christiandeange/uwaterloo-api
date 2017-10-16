package com.deange.uwaterlooapi.sample.utils;

import android.text.TextUtils;

import java.util.Collection;

public class Joiner {

  private String mConjunct;
  private String mDelimiter;

  private Joiner(final String delimiter) {
    mDelimiter = delimiter;
    mConjunct = mDelimiter;
  }

  public static Joiner on(final String delimiter) {
    return new Joiner(delimiter);
  }

  public static Joiner on(final char delimiter) {
    return new Joiner(String.valueOf(delimiter));
  }

  public Joiner conjunct(final String lastDelimiter) {
    mConjunct = lastDelimiter;
    return this;
  }

  public Joiner conjunct(final char lastDelimiter) {
    mConjunct = String.valueOf(lastDelimiter);
    return this;
  }

  public String join(final Collection<String> items) {
    return join(items.toArray(new String[items.size()]));
  }

  public <E> String joinObjects(final Collection<E> items) {
    String[] strItems = new String[items.size()];
    int count = 0;
    for (final E e : items) {
      strItems[count++] = String.valueOf(e);
    }

    return join(strItems);
  }

  public String join(final String... items) {

    if (items == null || items.length == 0) {
      return "";

    } else if (items.length == 1) {
      return items[0];

    } else {
      final StringBuilder sb = new StringBuilder(items[0]);
      for (int i = 1; i < items.length; i++) {
        if (!hasConjunct()) {
          sb.append(mDelimiter);
        } else if (items.length == 2) {
          sb.append(mConjunct);
        } else if (i == items.length - 1) {
          sb.append(mDelimiter);
          sb.append(mConjunct);
        } else {
          sb.append(mDelimiter);
        }

        sb.append(items[i]);
      }

      return sb.toString();
    }

  }

  private boolean hasConjunct() {
    return !TextUtils.equals(mDelimiter, mConjunct) && !TextUtils.isEmpty(mConjunct);
  }

}
