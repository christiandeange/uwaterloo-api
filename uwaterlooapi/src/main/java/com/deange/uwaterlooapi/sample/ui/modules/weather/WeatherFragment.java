package com.deange.uwaterlooapi.sample.ui.modules.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.weather.WeatherReading;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.utils.DateUtils;

public class WeatherFragment extends BaseModuleFragment<Response.Weather, WeatherReading> {

    TextView mTemperatureView;
    TextView mLastUpdated;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_weather, null);

        mTemperatureView = (TextView) root.findViewById(R.id.weather_temperature);
        mLastUpdated = (TextView) root.findViewById(R.id.weather_last_updated);

        return root;
    }

    @Override
    public Response.Weather onLoadData(final UWaterlooApi api) {
        return api.Weather.getWeather();
    }

    @Override
    public void onBindData(final Metadata metadata, final WeatherReading data) {
        mTemperatureView.setText(Math.round(data.getTemperature()) + "˚C");
        mLastUpdated.setText(
                getString(R.string.weather_last_updated,
                        DateUtils.formatDate(data.getObservationTime())));



    }

}
