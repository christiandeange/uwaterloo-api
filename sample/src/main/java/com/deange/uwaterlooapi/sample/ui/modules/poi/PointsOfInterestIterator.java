package com.deange.uwaterlooapi.sample.ui.modules.poi;

import com.deange.uwaterlooapi.model.poi.BasicPointOfInterest;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class PointsOfInterestIterator
    implements
    Iterator<BasicPointOfInterest>,
    Iterable<BasicPointOfInterest> {

  private Queue<Iterator<? extends BasicPointOfInterest>> mIterators = new ArrayDeque<>();

  @SafeVarargs
  public PointsOfInterestIterator(final List<? extends BasicPointOfInterest>... lists) {
    if (lists != null) {
      for (final List<? extends BasicPointOfInterest> list : lists) {
        if (list != null) {
          mIterators.add(list.iterator());
        }
      }
    }
  }

  @Override
  public boolean hasNext() {
    Iterator<? extends BasicPointOfInterest> iterator = mIterators.peek();
    while (iterator != null) {
      if (iterator.hasNext()) {
        return true;
      }

      mIterators.remove();
      iterator = mIterators.peek();
    }

    return false;
  }

  @Override
  public BasicPointOfInterest next() {
    return mIterators.peek().next();
  }

  @Override
  public void remove() {
    final Iterator<? extends BasicPointOfInterest> iterator = mIterators.peek();
    if (iterator != null) {
      iterator.remove();
    }
  }

  @Override
  public Iterator<BasicPointOfInterest> iterator() {
    return this;
  }
}
