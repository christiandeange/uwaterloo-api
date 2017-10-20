package com.deange.uwaterlooapi.sample.ui.modules.baseflow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deange.uwaterlooapi.sample.R;

import java.util.Map;
import java.util.concurrent.Callable;

import flow.Direction;
import flow.KeyChanger;
import flow.State;
import flow.TraversalCallback;

import static android.view.View.NO_ID;


public class ModuleChanger
    implements
    KeyChanger {

  private static final String TAG = "ModuleChanger";

  private final Callable<ViewGroup> mRootSupplier;

  public ModuleChanger(final Callable<ViewGroup> rootSupplier) {
    mRootSupplier = rootSupplier;
  }

  @Override
  public void changeKey(
      @Nullable final State outgoingState,
      @NonNull final State incomingState,
      @NonNull final Direction direction,
      @NonNull final Map<Object, Context> incomingContexts,
      @NonNull final TraversalCallback callback) {

    final ModuleKey inKey = incomingState.getKey();
    final Context context = incomingContexts.get(inKey);

    final ViewGroup root;
    try {
      root = mRootSupplier.call();
    } catch (final Exception e) {
      Log.w(TAG, "Ignoring traversal to " + inKey + ", could not acquire root ViewGroup", e);
      callback.onTraversalCompleted();
      return;
    }

    if (outgoingState != null) {
      // Save the old state and remove this view from the hierarchy
      final View oldView = root.getChildAt(0);

      final Screen oldScreen = (Screen) oldView.getTag(R.id.screen);
      if (oldScreen != null) {
        oldScreen.detach();
      }

      outgoingState.save(oldView);
      root.removeAllViews();
    }

    // Inflate the new child and assign it an id if it needs one
    // State will not be saved if it does not have an id
    final View view = LayoutInflater.from(context).inflate(inKey.layout(), root, false);
    if (view.getId() == NO_ID) {
      view.setId(View.generateViewId());
    }

    // Add the new view to the hierarchy
    incomingState.restore(view);
    root.addView(view, 0);

    final Screen<ModuleKey> screen = createScreen(inKey);
    screen.setKey(inKey);
    screen.takeView(view);
    view.setTag(R.id.screen, screen);

    // All done!
    callback.onTraversalCompleted();
  }

  @SuppressWarnings("unchecked")
  @NonNull
  private <T extends ModuleKey> Screen<T> createScreen(final T key) {
    if (key instanceof ScreenProvider) {
      return ((ScreenProvider) key).screen();
    }

    final Class<?> enclosingClass = findProperParentClass(key.getClass()).getEnclosingClass();
    if (enclosingClass != null && Screen.class.isAssignableFrom(enclosingClass)) {
      try {
        return (Screen<T>) enclosingClass.newInstance();
      } catch (final ReflectiveOperationException e) {
        throw new RuntimeException("Could not instantiate screen for " + key, e);
      }
    }

    throw new RuntimeException("Key " + key + " does not implement ScreenProvider and is not " +
                                   "enclosed in a parent Screen");
  }

  @SuppressWarnings("unchecked")
  private Class<? extends ModuleKey> findProperParentClass(final Class<? extends ModuleKey> clazz) {
    Class<? extends ModuleKey> parentClass = clazz;
    while (parentClass != null) {
      if (!parentClass.getName().contains("AutoValue_")) {
        return parentClass;
      }
      parentClass = (Class<? extends ModuleKey>) parentClass.getSuperclass();
    }
    return clazz;
  }
}
