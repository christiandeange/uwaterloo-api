package com.deange.uwaterlooapi.sample.ui.modules.baseflow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.deange.uwaterlooapi.sample.utils.Provider;

import java.util.Map;

import flow.Direction;
import flow.KeyChanger;
import flow.State;
import flow.TraversalCallback;


public class ModuleChanger
    implements
    KeyChanger {

  private static final String TAG = "ModuleChanger";

  private final ScreenChanger mChanger = new ScreenChanger();
  private final Provider<ViewGroup> mRootSupplier;

  public ModuleChanger(final Provider<ViewGroup> rootSupplier) {
    mRootSupplier = rootSupplier;
  }

  @Override
  public void changeKey(
      @Nullable final State outgoingState,
      @NonNull final State incomingState,
      @NonNull final Direction direction,
      @NonNull final Map<Object, Context> incomingContexts,
      @NonNull final TraversalCallback callback) {

    final Key inKey = incomingState.getKey();
    final ViewGroup root = mRootSupplier.get();

    if (outgoingState != null) {
      // Save the old state and remove this view from the hierarchy
      final View oldView = root.getChildAt(0);
      mChanger.save(oldView, outgoingState);
      root.removeAllViews();
    }

    final View view = mChanger.createAndAttachView(root, inKey);
    final Screen<Key> screen = createScreen(inKey);

    mChanger.restore(view, screen, incomingState);

    // All done!
    callback.onTraversalCompleted();
  }

  @SuppressWarnings("unchecked")
  @NonNull
  private <T extends Key> Screen<T> createScreen(final T key) {
    final Screen<T> screen;
    if (key instanceof ScreenProvider) {
      screen = ((ScreenProvider) key).screen();

    } else {
      final Class<?> enclosingClass = findProperParentClass(key.getClass()).getEnclosingClass();
      if (enclosingClass != null && Screen.class.isAssignableFrom(enclosingClass)) {
        try {
          screen = (Screen<T>) enclosingClass.newInstance();
        } catch (final ReflectiveOperationException e) {
          throw new RuntimeException("Could not instantiate screen for " + key, e);
        }
      } else {
        throw new RuntimeException("Key " + key + " does not implement ScreenProvider and is not " +
                                       "enclosed in a parent Screen");
      }
    }

    screen.setKey(key);
    return screen;
  }

  @SuppressWarnings("unchecked")
  private Class<? extends Key> findProperParentClass(final Class<? extends Key> clazz) {
    Class<? extends Key> parentClass = clazz;
    while (parentClass != null) {
      if (!parentClass.getName().contains("AutoValue_")) {
        return parentClass;
      }
      parentClass = (Class<? extends Key>) parentClass.getSuperclass();
    }
    return clazz;
  }

}
