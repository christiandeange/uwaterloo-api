package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class ShowAllAutoCompleteTextView
    extends AppCompatAutoCompleteTextView {

  public ShowAllAutoCompleteTextView(final Context context) {
    super(context);
  }

  public ShowAllAutoCompleteTextView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  public ShowAllAutoCompleteTextView(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public boolean enoughToFilter() {
    return true;
  }

  @Override
  protected void onFocusChanged(
      final boolean focused,
      final int direction,
      final Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
    if (focused) {
      if (getFilter() != null) {
        performFiltering(getText(), KeyEvent.ACTION_DOWN);
      }
    }
  }
}
