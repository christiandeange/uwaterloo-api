package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.util.SparseIntArray;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;

import org.joda.time.LocalTime;

public final class HeaderTitlePresenter {

  private static final SparseIntArray CAPTION_INDEX = new SparseIntArray();
  private static final SparseIntArray CAPTIONS = new SparseIntArray();

  static {
    CAPTIONS.put(R.string.greeting_morning, R.array.subgreetings_morning);
    CAPTIONS.put(R.string.greeting_afternoon, R.array.subgreetings_afternoon);
    CAPTIONS.put(R.string.greeting_evening, R.array.subgreetings_evening);
    CAPTIONS.put(R.string.greeting_night, R.array.subgreetings_night);
  }

  private HeaderTitlePresenter() {
    throw new AssertionError();
  }

  public static void show(
      final TextView titleView,
      final TextView subtitleView) {
    final Context context = titleView.getContext();

    final int hour = LocalTime.now().getHourOfDay();

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

    final String[] subtitleArray = context.getResources().getStringArray(CAPTIONS.get(stringId));

    final int captionIndex;
    if (CAPTION_INDEX.indexOfKey(stringId) < 0) {
      captionIndex = CAPTION_INDEX.get(stringId);
    } else {
      captionIndex = (int) (Math.random() * subtitleArray.length);
      CAPTION_INDEX.put(stringId, captionIndex);
    }

    subtitleView.setText(subtitleArray[captionIndex]);
  }

}
