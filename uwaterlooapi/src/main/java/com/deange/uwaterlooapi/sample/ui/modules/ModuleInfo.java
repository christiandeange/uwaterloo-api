package com.deange.uwaterlooapi.sample.ui.modules;

import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;

public class ModuleInfo {

    public final Class<? extends BaseModuleFragment> fragment;
    public final boolean isBase;
    public final int icon;

    public ModuleInfo(final Class<? extends BaseModuleFragment> fragment, final boolean isBase,
                      final int icon) {
        this.fragment = fragment;
        this.isBase = isBase;
        this.icon = icon;
    }

    public static Builder newBuilder(final Class<? extends BaseModuleFragment> fragment) {
        return new Builder(fragment);
    }

    private static void validateFields(final Builder builder) {
        if (builder.mIsBase && builder.mIcon == 0) {
            throw new IllegalStateException("If module is a base one, it must have an icon");
        }
    }

    public static final class Builder {

        private final Class<? extends BaseModuleFragment> mFragment;
        private boolean mIsBase;
        private int mIcon;

        public Builder(final Class<? extends BaseModuleFragment> fragment) {
            mFragment = fragment;
        }

        public Builder base(final boolean isBase) {
            mIsBase = isBase;
            return this;
        }

        public Builder icon(final int icon) {
            mIcon = icon;
            return this;
        }

        public ModuleInfo build() {
            validateFields(this);
            return new ModuleInfo(
                    mFragment,
                    mIsBase,
                    mIcon);
        }

    }
}
