package com.deange.uwaterlooapi.sample.ui.modules.base;

import com.deange.uwaterlooapi.model.AbstractModel;
import com.deange.uwaterlooapi.model.Metadata;

import java.util.List;

public abstract class AbstractModuleFragment<T extends AbstractModel>
        extends BaseModuleFragment<T, T> {

    @Override
    protected void deliverResponse(final T data) {
        deliverData(data);
    }

    public final void onBindData(final Metadata metadata, final T data) {
        onBindData(data);
    }

    public final void onBindData(final Metadata metadata, final List<T> data) {
        onBindData(data);
    }

    public void onBindData(final T data) {
        // Overriden by subclasses
    }

    public void onBindData(final List<T> data) {
        // Overriden by subclasses
    }

}
