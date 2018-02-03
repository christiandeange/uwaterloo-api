package com.deange.uwaterlooapi.sample.ui.modules.weather;

import android.support.annotation.Nullable;
import android.view.View;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.dagger.scoping.SingleIn;
import com.deange.uwaterlooapi.sample.ui.flow.KeyPath;
import com.squareup.coordinators.Coordinator;
import dagger.Subcomponent;

import static com.deange.uwaterlooapi.sample.dagger.scoping.Components.component;
import static com.deange.uwaterlooapi.sample.ui.flow.KeyPathParceler.forInstance;

public class WeatherScreen extends KeyPath {
  private static final WeatherScreen INSTANCE = new WeatherScreen();

  @Override public int layoutId() {
    return R.layout.fragment_weather;
  }

  @Nullable @Override public Coordinator provideCoordinator(View view) {
    return component(view.getContext(), Component.class).coordinator();
  }

  public static final Creator<WeatherScreen> CREATOR = forInstance(INSTANCE);

  @Subcomponent
  @SingleIn(WeatherScreen.class)
  public interface Component {
    WeatherCoordinator coordinator();

    WeatherRunner weatherRunner();
  }
}
