package com.deange.uwaterlooapi.sample.ui.modules.baseflow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.deange.uwaterlooapi.sample.R;

import flow.State;

import static android.view.View.NO_ID;

public class ScreenChanger {

  public View createAndAttachView(ViewGroup root, Key key) {
    final Context context = root.getContext();

    // Inflate the new child and assign it an id if it needs one
    // State will not be saved if it does not have an id
    final LayoutInflater inflater = LayoutInflater.from(context);

    final View view;
    if (key instanceof ModuleKey) {
      view = inflater.inflate(R.layout.module_screen, root, false);
      root.addView(view, 0);

      final ViewStub container = (ViewStub) root.findViewById(R.id.container_stub);
      container.setLayoutResource(key.layout());
      container.inflate();

    } else {
      view = inflater.inflate(key.layout(), root, false);
      root.addView(view, 0);
    }

    return view;
  }

  public void save(View view, State state) {
    final Screen screen = (Screen) view.getTag(R.id.screen);
    if (screen == null) {
      return;
    }

    state.save(view);

    Bundle bundle = state.getBundle();
    if (bundle == null) {
      bundle = new Bundle();
      state.setBundle(bundle);
    }

    screen.onSave(bundle);
    screen.detach();
  }

  public void restore(View view, Screen screen, State state) {
    // Restore the view's state (if necessary)
    // Views MUST have an id set if they want their state saved
    if (view.getId() == NO_ID) {
      view.setId(View.generateViewId());
    }
    state.restore(view);

    // Make the screen aware of the view it's taking control of
    screen.takeView(view);

    final Bundle bundle = state.getBundle();
    if (bundle != null && !bundle.isEmpty()) {
      screen.onRestore(bundle);
    }

    view.setTag(R.id.screen, screen);
  }

}
