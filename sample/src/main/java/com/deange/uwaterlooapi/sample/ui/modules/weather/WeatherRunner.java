package com.deange.uwaterlooapi.sample.ui.modules.weather;

import com.deange.uwaterlooapi.sample.dagger.scoping.SingleIn;
import javax.inject.Inject;

@SingleIn(WeatherScreen.class)
public class WeatherRunner {

  @Inject WeatherRunner() {
  }
}
