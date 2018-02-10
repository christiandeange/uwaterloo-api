package com.deange.uwaterlooapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/* package */ class MemoryCookieJar
    implements
    CookieJar {

  private final List<Cookie> mCookies = new ArrayList<>();

  @Override
  public void saveFromResponse(final HttpUrl url, final List<Cookie> cookies) {
    mCookies.addAll(cookies);
  }

  @Override
  public List<Cookie> loadForRequest(final HttpUrl url) {
    final List<Cookie> cookies = new ArrayList<>();
    for (final Iterator<Cookie> iterator = mCookies.iterator(); iterator.hasNext(); ) {
      final Cookie cookie = iterator.next();
      if (cookie.expiresAt() < System.currentTimeMillis()) {
        iterator.remove();
        continue;
      }

      if (cookie.matches(url)) {
        cookies.add(cookie);
      }
    }

    return cookies;
  }
}
