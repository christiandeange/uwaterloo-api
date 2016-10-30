package com.deange.uwaterlooapi.sample;

import android.content.Context;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public final class Analytics {

    private static String sUserId = null;
    private static final String INSTALLATION = "installation_id.config";

    private Analytics() {
        throw new AssertionError();
    }

    public static void init(final Context context) {
        if (sUserId == null) {
            final File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                sUserId = installation.exists()
                        ? readInstallationFile(installation)
                        : writeInstallationFile(installation);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (sUserId != null) {
            CrashReporting.setUserId(sUserId);
        }
    }

    public static void view(final String contentType) {
        Answers.getInstance().logContentView(new ContentViewEvent().putContentType(contentType));
    }

    public synchronized static String getUserId() {
        return sUserId;
    }

    private static String readInstallationFile(final File installation) throws IOException {
        final RandomAccessFile in = new RandomAccessFile(installation, "r");
        final byte[] bytes = new byte[(int) in.length()];
        in.readFully(bytes);
        in.close();
        return new String(bytes);
    }

    private static String writeInstallationFile(final File installation) throws IOException {
        final FileOutputStream out = new FileOutputStream(installation);
        final String id = generateNewId();
        out.write(id.getBytes());
        out.close();
        return id;
    }

    private static String generateNewId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
