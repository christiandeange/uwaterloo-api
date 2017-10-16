package com.deange.uwaterlooapi.sample.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.deange.uwaterlooapi.sample.utils.PlatformUtils;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

public class EncryptionController {

    private static EncryptionController sInstance;

    private final Context mContext;
    private final KeyStore mKeystore;

    public static void init(final Context context) {
        if (sInstance != null) {
            throw new IllegalStateException("EncryptionController already instantiated!");
        }
        sInstance = new EncryptionController(context);
    }

    @NonNull
    public static EncryptionController getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("EncryptionController not instantiated!");
        }
        return sInstance;
    }

    private EncryptionController(final Context context) {
        mContext = context.getApplicationContext();

        try {
            mKeystore = KeyStore.getInstance("AndroidKeyStore");
            mKeystore.load(null);

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String encryptString(final String key, final String text) {
        if (text == null) {
            return null;
        }

        try {
            ensureKey(key);

            final Cipher cipher = getCipher(key, Cipher.ENCRYPT_MODE);
            final byte[] values = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.encodeToString(values, Base64.DEFAULT);

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String decryptString(final String key, final String encrypted) {
        if (encrypted == null) {
            return null;
        }

        try {
            ensureKey(key);

            final Cipher cipher = getCipher(key, Cipher.DECRYPT_MODE);
            final byte[] values = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));
            return new String(values, "UTF-8");

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Cipher getCipher(final String key, final int mode)
            throws GeneralSecurityException {

        final KeyStore.Entry entry = mKeystore.getEntry(key, null);
        final KeyStore.PrivateKeyEntry privateEntry = (KeyStore.PrivateKeyEntry) entry;
        final Key signingKey = (mode == Cipher.ENCRYPT_MODE)
                ? privateEntry.getCertificate().getPublicKey()
                : privateEntry.getPrivateKey();

        final Cipher cipher = Cipher.getInstance(getTransformation(), getProvider());
        cipher.init(mode, signingKey);
        return cipher;
    }

    @SuppressLint("WrongConstant")
    private void ensureKey(final String key)
            throws GeneralSecurityException {

        // Create new key if needed
        if (!mKeystore.containsAlias(key)) {
            final Calendar start = Calendar.getInstance();
            final Calendar end = Calendar.getInstance();
            end.add(Calendar.YEAR, 1);

            final KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(mContext)
                    .setAlias(key)
                    .setSubject(new X500Principal("CN=Citrus"))
                    .setSerialNumber(BigInteger.ONE)
                    .setStartDate(start.getTime())
                    .setEndDate(end.getTime())
                    .build();

            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
            kpg.initialize(spec);
            kpg.generateKeyPair();
        }
    }

    private static String getTransformation() {
        return "RSA/ECB/PKCS1Padding";
    }

    private static String getProvider() {
        return PlatformUtils.hasMarshmallow()
                ? "AndroidKeyStoreBCWorkaround"
                : "AndroidOpenSSL";
    }
}
