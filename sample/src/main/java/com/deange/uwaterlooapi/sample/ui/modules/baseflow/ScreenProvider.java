package com.deange.uwaterlooapi.sample.ui.modules.baseflow;

import android.support.annotation.NonNull;

public interface ScreenProvider<T extends ModuleKey> {
  @NonNull Screen<T> screen();
}
