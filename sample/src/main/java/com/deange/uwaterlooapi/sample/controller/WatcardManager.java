package com.deange.uwaterlooapi.sample.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.watcard.WatcardCredentials;
import com.deange.uwaterlooapi.sample.Authorities;
import com.google.gson.Gson;

import javax.inject.Inject;

public class WatcardManager {

  private static final String PREF_FILE = Authorities.prefsFile("watcard");
  private static final String KEY_WATCARD = Authorities.key("watcard");

  private final SharedPreferences mPrefs;
  private final EncryptionController mEncryption;
  private final Gson mGson;

  @Inject
  WatcardManager(
      final Context context,
      final EncryptionController encryption,
      final Gson gson) {
    mPrefs = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    mEncryption = encryption;
    mGson = gson;
  }

  public void saveCredentials(final WatcardCredentials credentials) {
    putParcelable(KEY_WATCARD, credentials);
  }

  public WatcardCredentials getCredentials() {
    return getParcelable(KEY_WATCARD, WatcardCredentials.class);
  }

  public boolean hasWatcardCredentials() {
    return contains(KEY_WATCARD);
  }

  public void clearCredentials() {
    clear(KEY_WATCARD);
  }

  // Private implementation details

  private void putString(final String key, final String value) {
    final String encoded = mEncryption.encryptString(key, value);
    mPrefs.edit().putString(key, encoded).apply();
  }

  private String getString(final String key) {
    final String value = mPrefs.getString(key, null);
    return mEncryption.decryptString(key, value);
  }

  private void putParcelable(final String key, final Parcelable value) {
    putString(key, mGson.toJson(value));
  }

  private <T extends Parcelable> T getParcelable(final String key, Class<T> clazz) {
    return mGson.fromJson(getString(key), clazz);
  }

  private void clear(final String key) {
    mPrefs.edit().remove(key).apply();
  }

  private boolean contains(final String key) {
    return mPrefs.contains(key) && mPrefs.getAll().get(key) != null;
  }

}
