package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.deange.uwaterlooapi.model.foodservices.MenuInfo;
import com.deange.uwaterlooapi.model.foodservices.Outlet;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;

class OutletsAdapter extends ModuleAdapter {

  private MenuInfo mMenuItem;

  public OutletsAdapter(
      final Context context,
      final ModuleListItemListener listener,
      final MenuInfo menuItem) {
    super(context, listener);
    mMenuItem = menuItem;
  }

  @Override
  public View newView(final Context context, final int position, final ViewGroup parent) {
    return LayoutInflater.from(context)
                         .inflate(R.layout.list_item_foodservices_outlet, parent, false);
  }

  @Override
  public void bindView(final Context context, final int position, final View view) {
    final Outlet outlet = getItem(position);

    ((TextView) view.findViewById(R.id.outlet_name)).setText(outlet.getName());
  }

  @Override
  public int getCount() {
    return mMenuItem == null ? 0 : mMenuItem.getOutlets().size();
  }

  @Override
  public Outlet getItem(final int position) {
    return mMenuItem == null ? null : mMenuItem.getOutlets().get(position);
  }
}
