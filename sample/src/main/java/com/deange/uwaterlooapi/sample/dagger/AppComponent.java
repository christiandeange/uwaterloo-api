package com.deange.uwaterlooapi.sample.dagger;

import android.app.Application;
import android.content.Context;
import com.deange.uwaterlooapi.UWaterlooApi;
import com.deange.uwaterlooapi.sample.BuildConfig;
import com.deange.uwaterlooapi.sample.MainApplication;
import com.deange.uwaterlooapi.sample.controller.WatcardManager;
import dagger.Component;
import dagger.Provides;

@AppScope
@Component(modules = {
    AppComponent.Module.class,
    GsonModule.class,
})
public interface AppComponent
    extends AppComponentExports, AppComponentInjections {

  static AppComponent create(final Application application) {
    return DaggerAppComponent.builder().module(new Module(application)).build();
  }

  void inject(MainApplication application);

  @Component.Builder //
  interface Builder {
    Builder module(final Module module);

    AppComponent build();
  }

  @dagger.Module //
  class Module {
    private final Application mApplication;

    private Module(final Application application) {
      mApplication = application;
    }

    @Provides
    public Context providesContext() {
      return mApplication;
    }

    @Provides
    public UWaterlooApi providesUWaterlooApi(WatcardManager watcardManager) {
      UWaterlooApi api = new UWaterlooApi(BuildConfig.UWATERLOO_API_KEY);
      api.setWatcardCredentials(watcardManager.getCredentials());
      return api;
    }
  }
}
