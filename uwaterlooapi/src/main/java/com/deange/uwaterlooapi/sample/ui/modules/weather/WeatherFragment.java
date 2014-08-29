package com.deange.uwaterlooapi.sample.ui.modules.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.weather.WeatherReading;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;

public class WeatherFragment extends BaseModuleFragment<Response.Weather, WeatherReading> {
    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        return null;
    }

    @Override
    public Response.Weather onLoadData(final UWaterlooApi api) {
        return api.Weather.getWeather();
    }

    @Override
    public void onBindData(final Metadata metadata, final WeatherReading data) {
        Toast.makeText(getActivity(), "Temp is " + data.getTemperature() + " ˚C",
                Toast.LENGTH_SHORT).show();
    }

}
