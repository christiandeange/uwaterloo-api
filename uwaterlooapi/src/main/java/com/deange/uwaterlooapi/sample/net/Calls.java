package com.deange.uwaterlooapi.sample.net;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calls {

    private Calls() {
        throw new AssertionError();
    }

    public static <T> Call<T> wrap(final T obj) {
        return new InternalCall<>(obj);
    }

    public static <T> T unwrap(final Call<T> call) {
        try {
            return call.execute().body();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final class InternalCall<T>
            implements
            Call<T> {

        private static final Handler sHandler = new Handler(Looper.getMainLooper());

        private final T mObj;

        private InternalCall(final T obj) {
            mObj = obj;
        }

        @Override
        public Response<T> execute() {
            return Response.success(mObj);
        }

        @Override
        public void enqueue(final Callback<T> callback) {
            // Yield execution to caller before returning success
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(InternalCall.this, execute());
                }
            });
        }

        @Override
        public boolean isExecuted() {
            return true;
        }

        @Override
        public void cancel() {
            // No-op
        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @SuppressWarnings("CloneDoesntCallSuperClone")
        @Override
        public Call<T> clone() {
            return new InternalCall<>(mObj);
        }

        @Override
        public Request request() {
            return new Request.Builder().build();
        }
    }

}
