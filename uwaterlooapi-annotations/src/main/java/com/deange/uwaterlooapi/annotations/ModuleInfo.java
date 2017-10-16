package com.deange.uwaterlooapi.annotations;

public class ModuleInfo {

  public final Class fragment;
  public final boolean isBase;
  public final int layout;

  public ModuleInfo(final Class fragment, final int layout) {
    this.fragment = fragment;
    this.layout = layout;
    this.isBase = layout != 0;
  }
}
