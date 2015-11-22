/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deange.uwaterlooapi.sample.ui.view;

import android.animation.TimeAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ExtrasActivity;

import java.util.ArrayList;

@SuppressWarnings({ "PointlessBooleanExpression", "ConstantConditions" })
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class GooseView extends FrameLayout {
    public static final String TAG = GooseView.class.getSimpleName();
    public static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);
    public static final boolean DEBUG_DRAW = false; // DEBUG
    public static final boolean DEBUG_IDDQD = false;
    public static final float DEBUG_SPEED_MULTIPLIER = 1f; // 0.1f;

    public static void L(String s, Object... objects) {
        if (DEBUG) {
            Log.d(TAG, String.format(s, objects));
        }
    }

    public static final boolean AUTOSTART = true;
    public static final boolean HAVE_STARS = true;
    final static int[] POPS = {
            // <resid, spinny> pairs
            R.drawable.content_obstacle, 1,
    };

    private static class Params {
        public float TRANSLATION_PER_SEC;
        public int OBSTACLE_SPACING, OBSTACLE_PERIOD;
        public int BOOST_DV;
        public int PLAYER_HIT_SIZE;
        public int PLAYER_SIZE;
        public int OBSTACLE_RADIUS, OBSTACLE_STEM_WIDTH;
        public int OBSTACLE_GAP;
        public int OBSTACLE_MIN;
        public int BUILDING_WIDTH_MIN, BUILDING_WIDTH_MAX;
        public int BUILDING_HEIGHT_MIN;
        public int CLOUD_SIZE_MIN, CLOUD_SIZE_MAX;
        public int STAR_SIZE_MIN, STAR_SIZE_MAX;
        public int G;
        public int MAX_V;
        public float SCENERY_Z, OBSTACLE_Z, PLAYER_Z, PLAYER_Z_BOOST, HUD_Z;

        public Params(Resources res) {
            TRANSLATION_PER_SEC = res.getDimension(R.dimen.translation_per_sec);
            OBSTACLE_SPACING = res.getDimensionPixelSize(R.dimen.obstacle_spacing);
            OBSTACLE_PERIOD = (int) (OBSTACLE_SPACING / TRANSLATION_PER_SEC);
            BOOST_DV = res.getDimensionPixelSize(R.dimen.boost_dv);
            PLAYER_HIT_SIZE = res.getDimensionPixelSize(R.dimen.player_hit_size);
            PLAYER_SIZE = res.getDimensionPixelSize(R.dimen.player_size);
            OBSTACLE_RADIUS = res.getDimensionPixelSize(R.dimen.obstacle_width);
            OBSTACLE_STEM_WIDTH = res.getDimensionPixelSize(R.dimen.obstacle_stem_width);
            OBSTACLE_GAP = res.getDimensionPixelSize(R.dimen.obstacle_gap);
            OBSTACLE_MIN = res.getDimensionPixelSize(R.dimen.obstacle_height_min);
            BUILDING_HEIGHT_MIN = res.getDimensionPixelSize(R.dimen.building_height_min);
            BUILDING_WIDTH_MIN = res.getDimensionPixelSize(R.dimen.building_width_min);
            BUILDING_WIDTH_MAX = res.getDimensionPixelSize(R.dimen.building_width_max);
            CLOUD_SIZE_MIN = res.getDimensionPixelSize(R.dimen.cloud_size_min);
            CLOUD_SIZE_MAX = res.getDimensionPixelSize(R.dimen.cloud_size_max);
            STAR_SIZE_MIN = res.getDimensionPixelSize(R.dimen.star_size_min);
            STAR_SIZE_MAX = res.getDimensionPixelSize(R.dimen.star_size_max);
            G = res.getDimensionPixelSize(R.dimen.G);
            MAX_V = res.getDimensionPixelSize(R.dimen.max_v);
            SCENERY_Z = res.getDimensionPixelSize(R.dimen.scenery_z);
            OBSTACLE_Z = res.getDimensionPixelSize(R.dimen.obstacle_z);
            PLAYER_Z = res.getDimensionPixelSize(R.dimen.player_z);
            PLAYER_Z_BOOST = res.getDimensionPixelSize(R.dimen.player_z_boost);
            HUD_Z = res.getDimensionPixelSize(R.dimen.hud_z);
        }
    }

    private TimeAnimator mAnim;
    private TextView mScoreField;
    private View mSplash;
    private Player mDroid;
    private ArrayList<Obstacle> mObstaclesInPlay = new ArrayList<>();
    private float t;
    private int mScore;
    private float mLastPipeTime; // in sec
    private int mWidth, mHeight;
    private boolean mAnimating, mPlaying;
    private boolean mFrozen; // after death, a short backoff
    private int mTimeOfDay;
    private static final int DAY = 0, NIGHT = 1, TWILIGHT = 2, SUNSET = 3;
    private static final int[][] SKIES = {
            { 0xFFC0C0FF, 0xFFA0A0FF }, // DAY
            { 0xFF000010, 0xFF000000 }, // NIGHT
            { 0xFF000040, 0xFF000010 }, // TWILIGHT
            { 0xFFA08020, 0xFF204080 }, // SUNSET
    };
    private static Params PARAMS;

    public GooseView(Context context) {
        this(context, null);
    }

    public GooseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GooseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(true);
        PARAMS = new Params(getResources());
        mTimeOfDay = irand(0, SKIES.length);
    }

    @Override
    public boolean willNotDraw() {
        return !DEBUG;
    }

    public float getGameTime() {
        return t;
    }

    public void setScoreField(TextView tv) {
        mScoreField = tv;
        if (tv != null) {
            tv.setTranslationZ(PARAMS.HUD_Z);
            if (!(mAnimating && mPlaying)) {
                tv.setTranslationY(-500);
            }
        }
    }

    public void setSplash(View v) {
        mSplash = v;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        stop();
        reset();
        if (AUTOSTART) {
            start(false);
        }
    }

    final float hsv[] = { 0, 0, 0 };

    private void reset() {
        L("reset");
        final Drawable sky = new GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP,
                SKIES[mTimeOfDay]
        );
        setBackground(sky);
        setScaleX((frand() > 0.5f) ? -1 : 1);
        setScore(0);
        int i = getChildCount();
        while (i-- > 0) {
            final View v = getChildAt(i);
            if (v instanceof GameView) {
                removeViewAt(i);
            }
        }
        mObstaclesInPlay.clear();
        mWidth = getWidth();
        mHeight = getHeight();
        boolean showingSun = (mTimeOfDay == DAY || mTimeOfDay == SUNSET) && frand() > 0.25;
        if (showingSun) {
            final Star sun = new Star(getContext());
            sun.setBackgroundResource(R.drawable.sun);
            final int w = getResources().getDimensionPixelSize(R.dimen.sun_size);
            sun.setTranslationX(frand(w, mWidth - w));
            if (mTimeOfDay == DAY) {
                sun.setTranslationY(frand(w, (mHeight * 0.66f)));
                sun.getBackground().setTint(0);
            } else {
                sun.setTranslationY(frand(mHeight * 0.66f, mHeight - w));
                sun.getBackground().setTintMode(PorterDuff.Mode.SRC_ATOP);
                sun.getBackground().setTint(0xC0FF8000);
            }
            addView(sun, new LayoutParams(w, w));
        }
        if (!showingSun) {
            final boolean dark = mTimeOfDay == NIGHT || mTimeOfDay == TWILIGHT;
            final float ff = frand();
            if ((dark && ff < 0.75f) || ff < 0.5f) {
                final Star moon = new Star(getContext());
                moon.setBackgroundResource(R.drawable.moon);
                moon.getBackground().setAlpha(dark ? 255 : 128);
                moon.setScaleX(frand() > 0.5 ? -1 : 1);
                moon.setRotation(moon.getScaleX() * frand(5, 30));
                final int w = getResources().getDimensionPixelSize(R.dimen.sun_size);
                moon.setTranslationX(frand(w, mWidth - w));
                moon.setTranslationY(frand(w, mHeight - w));
                addView(moon, new LayoutParams(w, w));
            }
        }
        final int mh = mHeight / 6;
        final boolean cloudless = frand() < 0.25;
        final int N = 20;
        for (i = 0; i < N; i++) {
            final float r1 = frand();
            final Scenery s;
            if (HAVE_STARS && r1 < 0.3 && mTimeOfDay != DAY) {
                s = new Star(getContext());
            } else if (r1 < 0.6 && !cloudless) {
                s = new Cloud(getContext());
            } else {
                s = new Building(getContext());
                s.z = (float) i / N;
                s.setTranslationZ(PARAMS.SCENERY_Z * (1 + s.z));
                s.v = 0.85f * s.z; // buildings move proportional to their distance
                hsv[0] = 175;
                hsv[1] = 0.25f;
                hsv[2] = 1 * s.z;
                s.setBackgroundColor(Color.HSVToColor(hsv));
                s.h = irand(PARAMS.BUILDING_HEIGHT_MIN, mh);
            }
            final LayoutParams lp = new LayoutParams(s.w, s.h);
            if (s instanceof Building) {
                lp.gravity = Gravity.BOTTOM;
            } else {
                lp.gravity = Gravity.TOP;
                final float r = frand();
                if (s instanceof Star) {
                    lp.topMargin = (int) (r * r * mHeight);
                } else {
                    lp.topMargin = (int) (1 - r * r * mHeight / 2) + mHeight / 2;
                }
            }
            addView(s, lp);
            s.setTranslationX(frand(-lp.width, mWidth + lp.width));
        }
        mDroid = new Player(getContext());
        mDroid.setX(mWidth / 2);
        mDroid.setY(mHeight / 2);
        addView(mDroid, new LayoutParams(PARAMS.PLAYER_SIZE, PARAMS.PLAYER_SIZE));
        mAnim = new TimeAnimator();
        mAnim.setTimeListener(new TimeAnimator.TimeListener() {
            @Override
            public void onTimeUpdate(TimeAnimator timeAnimator, long t, long dt) {
                step(t, dt);
            }
        });
    }

    private void setScore(int score) {
        mScore = score;
        if (mScoreField != null) mScoreField.setText(String.valueOf(score));
    }

    private void addScore(int incr) {
        setScore(mScore + incr);
    }

    private void start(boolean startPlaying) {
        L("start(startPlaying=%s)", startPlaying ? "true" : "false");
        if (startPlaying) {
            mPlaying = true;
            t = 0;
            // there's a sucker born every OBSTACLE_PERIOD
            mLastPipeTime = getGameTime() - PARAMS.OBSTACLE_PERIOD;
            if (mSplash != null && mSplash.getAlpha() > 0f) {
                mSplash.setTranslationZ(PARAMS.HUD_Z);
                mSplash.animate().alpha(0).translationZ(0).setDuration(400);
                mScoreField.animate().translationY(0)
                           .setInterpolator(new DecelerateInterpolator())
                           .setDuration(1500);
            }
            mScoreField.setTextColor(Color.BLACK);
            mScoreField.setBackgroundResource(R.drawable.scorecard);
            mDroid.setVisibility(View.VISIBLE);
            mDroid.setX(mWidth / 2);
            mDroid.setY(mHeight / 2);
            mDroid.setScaleY(-1);
        } else {
            mDroid.setVisibility(View.GONE);
        }
        if (!mAnimating) {
            mAnim.start();
            mAnimating = true;
        }
    }

    private void stop() {
        if (mAnimating) {
            mAnim.cancel();
            mAnim = null;
            mAnimating = false;
            mScoreField.setTextColor(Color.WHITE);
            mScoreField.setBackgroundResource(R.drawable.scorecard_gameover);
            mTimeOfDay = irand(0, SKIES.length); // for next reset
            mFrozen = true;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFrozen = false;
                }
            }, 250);
        }
    }

    public static float lerp(float x, float a, float b) {
        return (b - a) * x + a;
    }

    public static float rlerp(float v, float a, float b) {
        return (v - a) / (b - a);
    }

    public static float clamp(float f) {
        return f < 0f ? 0f : f > 1f ? 1f : f;
    }

    public static float frand() {
        return (float) Math.random();
    }

    public static float frand(float a, float b) {
        return lerp(frand(), a, b);
    }

    public static int irand(int a, int b) {
        return (int) lerp(frand(), (float) a, (float) b);
    }

    private void step(long t_ms, long dt_ms) {
        t = t_ms / 1000f; // seconds
        float dt = dt_ms / 1000f;
        if (DEBUG) {
            t *= DEBUG_SPEED_MULTIPLIER;
            dt *= DEBUG_SPEED_MULTIPLIER;
        }
        // 1. Move all objects and update bounds
        final int N = getChildCount();
        int i = 0;
        for (; i < N; i++) {
            final View v = getChildAt(i);
            if (v instanceof GameView) {
                ((GameView) v).step(t_ms, dt_ms, t, dt);
            }
        }
        // 2. Check for altitude
        if (mPlaying && mDroid.below(mHeight)) {
            if (DEBUG_IDDQD) {
                poke();
            } else {
                L("player hit the floor");
                stop();
            }
        }
        // 3. Check for obstacles
        boolean passedBarrier = false;
        for (int j = mObstaclesInPlay.size(); j-- > 0; ) {
            final Obstacle ob = mObstaclesInPlay.get(j);
            if (mPlaying && ob.intersects(mDroid) && !DEBUG_IDDQD) {
                L("player hit an obstacle");
                if (ob instanceof MagicPop) youCantSeeMe();
                stop();
            } else if (ob.cleared(mDroid)) {
                if (ob instanceof Stem) passedBarrier = true;
                mObstaclesInPlay.remove(j);
            }
        }
        if (mPlaying && passedBarrier) {
            addScore(1);
        }
        // 4. Handle edge of screen
        // Walk backwards to make sure removal is safe
        while (i-- > 0) {
            final View v = getChildAt(i);
            if (v instanceof Obstacle) {
                if (v.getTranslationX() + v.getWidth() < 0) {
                    removeViewAt(i);
                }
            } else if (v instanceof Scenery) {
                final Scenery s = (Scenery) v;
                if (v.getTranslationX() + s.w < 0) {
                    v.setTranslationX(getWidth());
                }
            }
        }
        // 3. Time for more obstacles!
        if (mPlaying && (t - mLastPipeTime) > PARAMS.OBSTACLE_PERIOD) {
            mLastPipeTime = t;
            final int obstacley = (int) (Math.random()
                    * (mHeight - 2 * PARAMS.OBSTACLE_MIN - PARAMS.OBSTACLE_GAP)) + PARAMS.OBSTACLE_MIN;
            final int inset = (PARAMS.OBSTACLE_RADIUS - PARAMS.OBSTACLE_STEM_WIDTH) / 2;
            final int yinset = PARAMS.OBSTACLE_RADIUS / 2;
            final int d1 = irand(0, 250);
            final Obstacle s1 = new Stem(getContext(), obstacley - yinset, false);
            addView(s1, new LayoutParams(
                    PARAMS.OBSTACLE_STEM_WIDTH,
                    (int) s1.h,
                    Gravity.TOP | Gravity.START));
            s1.setTranslationX(mWidth + inset);
            s1.setTranslationY(-s1.h - yinset);
            s1.setTranslationZ(PARAMS.OBSTACLE_Z * 0.75f);
            s1.animate()
              .translationY(0)
              .setStartDelay(d1)
              .setDuration(250);
            mObstaclesInPlay.add(s1);
            final Obstacle p1 = makePop(getContext(), PARAMS.OBSTACLE_RADIUS);
            addView(p1, new LayoutParams(
                    PARAMS.OBSTACLE_RADIUS,
                    PARAMS.OBSTACLE_RADIUS,
                    Gravity.TOP | Gravity.START));
            p1.setTranslationX(mWidth);
            p1.setTranslationY(-PARAMS.OBSTACLE_RADIUS);
            p1.setTranslationZ(PARAMS.OBSTACLE_Z);
            p1.setScaleX(0.25f);
            p1.setScaleY(0.25f);
            p1.animate()
              .translationY(s1.h - inset)
              .scaleX(1f)
              .scaleY(1f)
              .setStartDelay(d1)
              .setDuration(250);
            mObstaclesInPlay.add(p1);
            final int d2 = irand(0, 250);
            final Obstacle s2 = new Stem(getContext(),
                    mHeight - obstacley - PARAMS.OBSTACLE_GAP - yinset,
                    true);
            addView(s2, new LayoutParams(
                    PARAMS.OBSTACLE_STEM_WIDTH,
                    (int) s2.h,
                    Gravity.TOP | Gravity.START));
            s2.setTranslationX(mWidth + inset);
            s2.setTranslationY(mHeight + yinset);
            s2.setTranslationZ(PARAMS.OBSTACLE_Z * 0.75f);
            s2.animate()
              .translationY(mHeight - s2.h)
              .setStartDelay(d2)
              .setDuration(400);
            mObstaclesInPlay.add(s2);
            final Obstacle p2 = makePop(getContext(), PARAMS.OBSTACLE_RADIUS);
            addView(p2, new LayoutParams(
                    PARAMS.OBSTACLE_RADIUS,
                    PARAMS.OBSTACLE_RADIUS,
                    Gravity.TOP | Gravity.START));
            p2.setTranslationX(mWidth);
            p2.setTranslationY(mHeight);
            p2.setTranslationZ(PARAMS.OBSTACLE_Z);
            p2.setScaleX(0.25f);
            p2.setScaleY(0.25f);
            p2.animate()
              .translationY(mHeight - s2.h - yinset)
              .scaleX(1f)
              .scaleY(1f)
              .setStartDelay(d2)
              .setDuration(400);
            mObstaclesInPlay.add(p2);
        }
        if (DEBUG_DRAW) invalidate();
    }

    private void youCantSeeMe() {
        final Activity activity = ((Activity) getContext());
        activity.startActivity(new Intent(getContext(), ExtrasActivity.class));
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public Pop makePop(Context context, float h) {
        if (irand(0, 2) == 0) {
            return new MagicPop(context, h);
        } else {
            return new Pop(context, h);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (DEBUG) L("touch: %s", ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                poke();
                return true;
            case MotionEvent.ACTION_UP:
                unpoke();
                return true;
        }
        return false;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        if (DEBUG) L("trackball: %s", ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                poke();
                return true;
            case MotionEvent.ACTION_UP:
                unpoke();
                return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent ev) {
        if (DEBUG) L("keyDown: %d", keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_SPACE:
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                poke();
                return true;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent ev) {
        if (DEBUG) L("keyDown: %d", keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_SPACE:
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                unpoke();
                return true;
        }
        return false;
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent ev) {
        if (DEBUG) L("generic: %s", ev);
        return false;
    }

    private void poke() {
        L("poke");
        if (mFrozen) return;
        if (!mAnimating) {
            reset();
            start(true);
        } else if (!mPlaying) {
            start(true);
        }
        mDroid.boost();
        if (DEBUG) {
            mDroid.dv *= DEBUG_SPEED_MULTIPLIER;
            mDroid.animate().setDuration((long) (200 / DEBUG_SPEED_MULTIPLIER));
        }
    }

    private void unpoke() {
        L("unboost");
        if (mFrozen) return;
        if (!mAnimating) return;
        mDroid.unboost();
    }

    @Override
    @SuppressLint("DrawAllocation")
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if (!DEBUG_DRAW) return;

        // DEBUG SHIT
        final Paint pt = new Paint();
        pt.setColor(0xFFFFFFFF);
        final int L = mDroid.corners.length;
        final int N = L / 2;
        for (int i = 0; i < N; i++) {
            final int x = (int) mDroid.corners[i * 2];
            final int y = (int) mDroid.corners[i * 2 + 1];
            c.drawCircle(x, y, 4, pt);
            c.drawLine(x, y,
                    mDroid.corners[(i * 2 + 2) % L],
                    mDroid.corners[(i * 2 + 3) % L],
                    pt);
        }
        pt.setStyle(Paint.Style.STROKE);
        pt.setStrokeWidth(getResources().getDisplayMetrics().density);
        final int M = getChildCount();
        pt.setColor(0x8000FF00);
        for (int i = 0; i < M; i++) {
            final View v = getChildAt(i);
            if (v == mDroid) continue;
            if (!(v instanceof GameView)) continue;
            if (v instanceof Pop) {
                final Pop p = (Pop) v;
                c.drawCircle(p.cx, p.cy, p.r, pt);
            } else {
                final Rect r = new Rect();
                v.getHitRect(r);
                c.drawRect(r, pt);
            }
        }
        pt.setColor(Color.BLACK);
        final StringBuilder sb = new StringBuilder("obstacles: ");
        for (Obstacle ob : mObstaclesInPlay) {
            sb.append(ob.hitRect.toShortString());
            sb.append(" ");
        }
        pt.setTextSize(20f);
        c.drawText(sb.toString(), 20, 100, pt);
    }

    private interface GameView {
        void step(long t_ms, long dt_ms, float t, float dt);
    }

    private class Player extends ImageView implements GameView {
        public float dv;
        private boolean mBoosting;
        private final float[] sHull = new float[]{
                // @formatter:off
                0.875f,	        0f,
                0.885416667f,	0.338283828f,
                0.989583333f,	0.47029703f,
                0.989583333f,	0.717821782f,
                0.90625f,	    1f,
                0.8125f,	    1f,
                0.78125f,	    0.808580858f,
                0.166666667f,	1f,
                0f,	            1f,
                0f,	            0.899339934f,
                0.364583333f,	0.701320132f,
                0.479166667f,	0.618811881f,
                0.583333333f,	0.585808581f,
                0.239583333f,	0.264026403f,
                0.322916667f,	0.239273927f,
                0.625f,	        0.297029703f,
                0.770833333f,	0.404290429f,
                0.770833333f,	0.090759076f,
                // @formatter:on
        };
        public final float[] corners = new float[sHull.length];

        public Player(Context context) {
            super(context);
            setBackgroundResource(R.drawable.content_goose);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int w = view.getWidth();
                    final int h = view.getHeight();
                    final int ix = (int) (w * 0.3f);
                    final int iy = (int) (h * 0.2f);
                    outline.setOval(ix, iy, w - ix, h - iy);
                }
            });
        }

        public void prepareCheckIntersections() {
            final int inset = (PARAMS.PLAYER_SIZE - PARAMS.PLAYER_HIT_SIZE) / 2;
            final int scale = PARAMS.PLAYER_HIT_SIZE;
            final int N = sHull.length / 2;
            for (int i = 0; i < N; i++) {
                corners[i * 2] = scale * sHull[i * 2] + inset;
                corners[i * 2 + 1] = scale * sHull[i * 2 + 1] + inset;
            }
            final Matrix m = getMatrix();
            m.mapPoints(corners);
        }

        public boolean below(int h) {
            final int N = corners.length / 2;
            for (int i = 0; i < N; i++) {
                final int y = (int) corners[i * 2 + 1];
                if (y >= h) return true;
            }
            return false;
        }

        public void step(long t_ms, long dt_ms, float t, float dt) {
            if (getVisibility() != View.VISIBLE) return; // not playing yet
            if (mBoosting) {
                dv = -PARAMS.BOOST_DV;
            } else {
                dv += PARAMS.G;
            }
            if (dv < -PARAMS.MAX_V) dv = -PARAMS.MAX_V;
            else if (dv > PARAMS.MAX_V) dv = PARAMS.MAX_V;
            final float y = getTranslationY() + dv * dt;
            setTranslationY(y < 0 ? 0 : y);
            setRotation(
                    90 + lerp(clamp(rlerp(dv, PARAMS.MAX_V, -1 * PARAMS.MAX_V)), 90, -90));
            prepareCheckIntersections();
        }

        public void boost() {
            mBoosting = true;
            dv = -PARAMS.BOOST_DV;
            animate().cancel();
            animate()
                    .scaleX(1.25f)
                    .scaleY(1.25f)
                    .translationZ(PARAMS.PLAYER_Z_BOOST)
                    .setDuration(100);
            setScaleX(1.25f);
            setScaleY(1.25f);
        }

        public void unboost() {
            mBoosting = false;
            animate().cancel();
            animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .translationZ(PARAMS.PLAYER_Z)
                    .setDuration(200);
        }
    }

    private class Obstacle extends View implements GameView {
        public float h;
        public final Rect hitRect = new Rect();

        public Obstacle(Context context, float h) {
            super(context);
            setBackgroundColor(0xFFFF0000);
            this.h = h;
        }

        public boolean intersects(Player p) {
            final int N = p.corners.length / 2;
            for (int i = 0; i < N; i++) {
                final int x = (int) p.corners[i * 2];
                final int y = (int) p.corners[i * 2 + 1];
                if (hitRect.contains(x, y)) return true;
            }
            return false;
        }

        public boolean cleared(Player p) {
            final int N = p.corners.length / 2;
            for (int i = 0; i < N; i++) {
                final int x = (int) p.corners[i * 2];
                if (hitRect.right >= x) return false;
            }
            return true;
        }

        @Override
        public void step(long t_ms, long dt_ms, float t, float dt) {
            setTranslationX(getTranslationX() - PARAMS.TRANSLATION_PER_SEC * dt);
            getHitRect(hitRect);
        }
    }

    private class Pop extends Obstacle {
        int mRotate;
        int cx, cy, r;

        public Pop(Context context, float h) {
            super(context, h);
            int idx = 2 * irand(0, POPS.length / 2);
            setBackgroundResource(POPS[idx]);
            setScaleX(frand() < 0.5f ? -1 : 1);
            mRotate = POPS[idx + 1] == 0 ? 0 : (frand() < 0.5f ? -1 : 1);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int pad = (int) (getWidth() * 0.02f);
                    outline.setOval(pad, pad, getWidth() - pad, getHeight() - pad);
                }
            });
        }

        public boolean intersects(Player p) {
            final int N = p.corners.length / 2;
            for (int i = 0; i < N; i++) {
                final int x = (int) p.corners[i * 2];
                final int y = (int) p.corners[i * 2 + 1];
                if (Math.hypot(x - cx, y - cy) <= r) return true;
            }
            return false;
        }

        @Override
        public void step(long t_ms, long dt_ms, float t, float dt) {
            super.step(t_ms, dt_ms, t, dt);
            if (mRotate != 0) {
                setRotation(getRotation() + dt * 45 * mRotate);
            }
            cx = (hitRect.left + hitRect.right) / 2;
            cy = (hitRect.top + hitRect.bottom) / 2;
            r = getWidth() / 2;
        }
    }

    public class MagicPop extends Pop {
        public MagicPop(final Context context, final float h) {
            super(context, h);
            setBackgroundResource(R.drawable.extra_image);
        }
    }

    private class Stem extends Obstacle {
        Paint mPaint = new Paint();
        Path mShadow = new Path();
        boolean mDrawShadow;
        final GradientDrawable mGradient = new GradientDrawable();

        public Stem(Context context, float h, boolean drawShadow) {
            super(context, h);
            mDrawShadow = drawShadow;
            mPaint.setColor(0xFFAAAAAA);
            setBackground(null);
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            setWillNotDraw(false);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRect(0, 0, getWidth(), getHeight());
                }
            });
        }

        @Override
        public void onDraw(Canvas c) {
            final int w = c.getWidth();
            final int h = c.getHeight();
            mGradient.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            mGradient.setGradientCenter(w * 0.75f, 0);
            mGradient.setColors(new int[]{ 0xFFFFFFFF, 0xFFAAAAAA });
            mGradient.setBounds(0, 0, w, h);
            mGradient.draw(c);
            if (!mDrawShadow) return;
            mShadow.reset();
            mShadow.moveTo(0, 0);
            mShadow.lineTo(w, 0);
            mShadow.lineTo(w, PARAMS.OBSTACLE_RADIUS / 2 + w * 1.5f);
            mShadow.lineTo(0, PARAMS.OBSTACLE_RADIUS / 2);
            mShadow.close();
            c.drawPath(mShadow, mPaint);
        }
    }

    private class Scenery extends FrameLayout implements GameView {
        public float z;
        public float v;
        public int h, w;

        public Scenery(Context context) {
            super(context);
        }

        @Override
        public void step(long t_ms, long dt_ms, float t, float dt) {
            setTranslationX(getTranslationX() - PARAMS.TRANSLATION_PER_SEC * dt * v);
        }
    }

    private class Building extends Scenery {
        public Building(Context context) {
            super(context);
            w = irand(PARAMS.BUILDING_WIDTH_MIN, PARAMS.BUILDING_WIDTH_MAX);
            h = 0; // will be setup later, along with z
            setTranslationZ(PARAMS.SCENERY_Z);
        }
    }

    private class Cloud extends Scenery {
        public Cloud(Context context) {
            super(context);
            setBackgroundResource(frand() < 0.01f ? R.drawable.cloud_off : R.drawable.cloud);
            getBackground().setAlpha(0x40);
            w = h = irand(PARAMS.CLOUD_SIZE_MIN, PARAMS.CLOUD_SIZE_MAX);
            z = 0;
            v = frand(0.15f, 0.5f);
        }
    }

    private class Star extends Scenery {
        public Star(Context context) {
            super(context);
            setBackgroundResource(R.drawable.star);
            w = h = irand(PARAMS.STAR_SIZE_MIN, PARAMS.STAR_SIZE_MAX);
            v = z = 0;
        }
    }
}