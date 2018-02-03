package com.deange.uwaterlooapi.sample.ui.flow.base;

import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.squareup.coordinators.Coordinator;

public class ButterknifeCoordinator extends Coordinator {

  private Unbinder binder;

  @Override public void attach(View view) {
    binder = ButterKnife.bind(this, view);
  }

  @Override public void detach(View view) {
    binder.unbind();
    binder = null;
  }
}
