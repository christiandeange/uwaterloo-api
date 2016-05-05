package com.deange.uwaterlooapi.sample.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NetworkController {

    private static NetworkController sInstance;
    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    private final Context mContext;
    private final ConnectivityManager mManager;
    private final Set<WeakReference<OnNetworkChangedListener>> mListeners = new HashSet<>();

    private NetworkInfo mCurrentNetwork;

    public static void init(final Context context) {
        if (sInstance != null) {
            throw new IllegalStateException("NetworkController already instantiated!");
        }
        sInstance = new NetworkController(context);
    }

    @NonNull
    public static NetworkController getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("NetworkController not instantiated!");
        }
        return sInstance;
    }

    private NetworkController(final Context context) {
        mContext = context.getApplicationContext();
        mManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        mContext.registerReceiver(new NetworkReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        // Don't call updateNetworkConnectivity() just yet
        mCurrentNetwork = mManager.getActiveNetworkInfo();
    }

    public boolean isConnected() {
        return mCurrentNetwork != null;
    }

    public void registerListener(final OnNetworkChangedListener listener) {
        mListeners.add(new WeakReference<>(listener));
    }

    public void unregisterListener(final OnNetworkChangedListener listener) {
        for (final WeakReference<OnNetworkChangedListener> listenerRef : mListeners) {
            if (listener == listenerRef.get()) {
                mListeners.remove(listenerRef);
                return;
            }
        }
    }

    private void updateNetworkConnectivity() {
        final NetworkInfo oldNetworkInfo = mCurrentNetwork;
        mCurrentNetwork = mManager.getActiveNetworkInfo();

        if (oldNetworkInfo == null && mCurrentNetwork != null && mCurrentNetwork.isConnected()) {
            broadcastConnectivityChanged(true);

        } else if (oldNetworkInfo != null && oldNetworkInfo.isConnected() && mCurrentNetwork == null) {
            broadcastConnectivityChanged(false);
        }
    }

    private void broadcastConnectivityChanged(final boolean changed) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                final Iterator<WeakReference<OnNetworkChangedListener>> iterator = mListeners.iterator();
                while (iterator.hasNext()) {
                    final WeakReference<OnNetworkChangedListener> listenerRef = iterator.next();
                    final OnNetworkChangedListener listener = listenerRef.get();
                    if (listener == null) {
                        iterator.remove();
                    } else {
                        listener.onNetworkChanged(changed);
                    }
                }

            }
        });
    }

    private final class NetworkReceiver
            extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            updateNetworkConnectivity();
        }
    }

    public interface OnNetworkChangedListener {
        void onNetworkChanged(final boolean connected);
    }
}
