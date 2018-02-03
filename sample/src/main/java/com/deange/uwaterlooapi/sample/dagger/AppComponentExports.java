package com.deange.uwaterlooapi.sample.dagger;

import com.deange.uwaterlooapi.sample.controller.NetworkController;
import com.deange.uwaterlooapi.sample.controller.WatcardManager;
import com.deange.uwaterlooapi.sample.ui.modules.weather.WeatherScreen;
import com.deange.uwaterlooapi.sample.utils.Px;
import com.google.gson.Gson;

public interface AppComponentExports {

  WeatherScreen.Component weatherScreen();

  WatcardManager watcardManager();

  NetworkController networkController();

  Gson gson();

  Px px();
}
