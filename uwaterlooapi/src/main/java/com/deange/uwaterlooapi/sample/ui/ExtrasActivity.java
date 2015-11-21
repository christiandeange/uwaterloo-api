package com.deange.uwaterlooapi.sample.ui;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.deange.uwaterlooapi.sample.R;

import java.util.Random;

public class ExtrasActivity extends AppCompatActivity {

    private static final int STREAM = AudioManager.STREAM_MUSIC;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable mRumbleRunnable = new Runnable() {
        @Override
        public void run() {
            final float t = mDip * (mImageView.isPressed() ? 20f : 5f);
            mImageView.setTranslationX((float) (Math.random() * t));
            mImageView.setTranslationY((float) (Math.random() * t));

            final int sign = (int) Math.signum(Math.random() - 0.5f);
            final float scaleFactor = mImageView.isPressed() ? .1f : 0.01f;
            final float scale = 1 + (sign * ((float) Math.random() * scaleFactor));
            mImageView.setScaleX(scale);
            mImageView.setScaleY(scale);

            mHandler.post(mRumbleRunnable);
        }
    };

    private float mDip;
    private ImageView mImageView;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVolumeControlStream(STREAM);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mDip = getResources().getDisplayMetrics().density;

        setContentView(R.layout.activity_extras);

        mImageView = (ImageView) findViewById(R.id.extras_image_view);

        mHandler.post(mRumbleRunnable);

        mMediaPlayer = MediaPlayer.create(this, R.raw.extras_soundtrack);
        mMediaPlayer.setAudioStreamType(STREAM);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRumbleRunnable);
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;

        super.onDestroy();
    }
}
