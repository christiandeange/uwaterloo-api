package com.deange.uwaterlooapi.sample.ui.modules.weather;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindDrawable;
import butterknife.BindView;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.dagger.scoping.SingleIn;
import com.deange.uwaterlooapi.sample.ui.flow.base.ButterknifeCoordinator;
import com.deange.uwaterlooapi.sample.ui.view.RangeView;
import com.deange.uwaterlooapi.sample.ui.view.SliceView;
import com.deange.uwaterlooapi.sample.ui.view.WaveView;
import javax.inject.Inject;

@SingleIn(WeatherScreen.class)
public class WeatherCoordinator extends ButterknifeCoordinator {

  @BindView(R.id.weather_scrollview) ScrollView scrollView;
  @BindView(R.id.weather_slider) SliceView sliceView;
  @BindView(R.id.weather_background) ImageView background;

  @BindView(R.id.weather_temperature_bar) View temperatureBar;
  @BindView(R.id.weather_temperature) TextView temperatureView;
  @BindView(R.id.weather_temperature_range) RangeView rangeView;
  @BindView(R.id.weather_min_temp) TextView minTempView;
  @BindView(R.id.weather_max_temp) TextView maxTempView;

  @BindView(R.id.weather_wind_direction_root) View windDirectionRoot;
  @BindView(R.id.weather_wind_direction) View windDirectionView;
  @BindView(R.id.weather_wind_speed) TextView windSpeedView;

  @BindView(R.id.weather_waveview) WaveView waveView;
  @BindView(R.id.weather_precipitation) TextView precipitationView;

  @BindView(R.id.weather_pressure_trend_layout) ViewGroup pressureLayout;
  @BindView(R.id.weather_pressure) TextView pressureDescription;
  @BindView(R.id.weather_pressure_trend) TextView pressureTrend;

  @BindView(R.id.weather_last_updated) TextView lastUpdated;
  @BindView(R.id.weather_spacer) View spacer;
  @BindView(R.id.weather_author_attribution) TextView author;

  @BindDrawable(R.drawable.ic_arrow_up) Drawable mArrowDrawable;

  @Inject WeatherCoordinator() {
  }

  @Override public void attach(View view) {
  }
}
