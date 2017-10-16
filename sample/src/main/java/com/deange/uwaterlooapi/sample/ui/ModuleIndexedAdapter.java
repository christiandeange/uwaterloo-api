package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.widget.SectionIndexer;

import java.util.Arrays;

public abstract class ModuleIndexedAdapter<T>
    extends ModuleAdapter
    implements
    SectionIndexer {

  public ModuleIndexedAdapter(final Context context) {
    super(context);
  }

  public ModuleIndexedAdapter(final Context context, final ModuleListItemListener listener) {
    super(context, listener);
  }

  @Override
  public int getPositionForSection(final int sectionIndex) {
    final T obj = getSections()[sectionIndex];

    int first = 0;
    while (first < getCount() && !obj.equals(getFirstCharOf(first))) {
      first++;
    }

    return first;
  }

  @Override
  public int getSectionForPosition(final int position) {
    return Arrays.asList(getSections()).indexOf(getFirstCharOf(position));
  }

  @Override
  public abstract T[] getSections();

  public abstract T getFirstCharOf(final int position);
}
