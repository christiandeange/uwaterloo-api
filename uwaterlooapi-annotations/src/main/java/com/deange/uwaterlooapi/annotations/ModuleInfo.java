package com.deange.uwaterlooapi.annotations;

public class ModuleInfo {

    public final Class fragment;
    public final boolean isBase;
    public final int icon;

    public ModuleInfo(final Class fragment, final boolean isBase,
                      final int icon) {
        this.fragment = fragment;
        this.isBase = isBase;
        this.icon = icon;

        validateFields();
    }

    private void validateFields() {
        if (isBase && icon == 0) {
            throw new IllegalStateException("If module is a base one, it must have an icon");
        }
    }

}
