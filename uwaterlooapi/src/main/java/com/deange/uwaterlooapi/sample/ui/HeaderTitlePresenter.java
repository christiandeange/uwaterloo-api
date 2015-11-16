package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;

import org.joda.time.LocalTime;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class HeaderTitlePresenter {

    private static final int CAPTION_INDEX = new Random().nextInt(3);
    private static final Map<Integer, Integer> CAPTIONS;
    static {
        final Map<Integer, Integer> map = new HashMap<>();
        map.put(R.string.greeting_morning, R.array.subgreetings_morning);
        map.put(R.string.greeting_afternoon, R.array.subgreetings_afternoon);
        map.put(R.string.greeting_evening, R.array.subgreetings_evening);
        map.put(R.string.greeting_night, R.array.subgreetings_night);
        CAPTIONS = Collections.unmodifiableMap(map);
    }

    private HeaderTitlePresenter() {
        throw new AssertionError();
    }

    public static void show(
            final TextView titleView,
            final TextView subtitleView) {
        final Context context = titleView.getContext();

        final LocalTime now = LocalTime.now();
        final int hour = now.getHourOfDay();

        final int stringId;
        if (hour >= 6 && hour < 12) {
            stringId = R.string.greeting_morning;
        } else if (hour >= 12 && hour < 17) {
            stringId = R.string.greeting_afternoon;
        } else if (hour >= 17 && hour < 22) {
            stringId = R.string.greeting_evening;
        } else {
            stringId = R.string.greeting_night;
        }

        titleView.setText(stringId);
        subtitleView.setText(context.getResources().getStringArray(
                CAPTIONS.get(stringId))[CAPTION_INDEX]);
    }

}
