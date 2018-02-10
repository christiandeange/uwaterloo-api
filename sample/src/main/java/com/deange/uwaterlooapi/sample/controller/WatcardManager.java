package com.deange.uwaterlooapi.sample.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.deange.uwaterlooapi.model.watcard.WatcardCredentials;
import com.deange.uwaterlooapi.sample.Authorities;

public class WatcardManager {

  private static final String PREF_FILE = Authorities.prefsFile("watcard");
  private static final String KEY_WATCARD = Authorities.key("watcard");
  private static WatcardManager sInstance;

  private final SharedPreferences mPrefs;

  public static void init(final Context context) {
    if (sInstance != null) {
      throw new IllegalStateException("WatcardManager already instantiated!");
    }
    sInstance = new WatcardManager(context);
  }

  @NonNull
  public static WatcardManager getInstance() {
    if (sInstance == null) {
      throw new IllegalStateException("WatcardManager not instantiated!");
    }
    return sInstance;
  }

  private WatcardManager(final Context context) {
    mPrefs = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
  }

  public void saveCredentials(final WatcardCredentials credentials) {
    final String json = GsonController.getInstance().toJson(credentials);
    putString(KEY_WATCARD, json);
  }

  public WatcardCredentials getCredentials() {
    final String json = getString(KEY_WATCARD);
    return GsonController.getInstance().fromJson(json, WatcardCredentials.class);
  }

  public boolean hasWatcardCredentials() {
    return contains(KEY_WATCARD);
  }

  public void clearCredentials() {
    clear(KEY_WATCARD);
  }

  // Private implementation details

  private void putString(final String key, final String value) {
    final String encoded = EncryptionController.getInstance().encryptString(key, value);
    mPrefs.edit().putString(key, encoded).apply();
  }

  private String getString(final String key) {
    final String value = mPrefs.getString(key, null);
    return EncryptionController.getInstance().decryptString(key, value);
  }

  private void clear(final String key) {
    mPrefs.edit().remove(key).apply();
  }

  private boolean contains(final String key) {
    return mPrefs.contains(key) && mPrefs.getAll().get(key) != null;
  }

}
