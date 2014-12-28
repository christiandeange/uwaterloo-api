package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.model.foodservices.SpecialOperatingHours;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.view.ExpandablePanel;
import com.deange.uwaterlooapi.sample.ui.view.OperatingHoursView;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LocationAdapter extends ModuleAdapter {

    private final List<Location> mLocations;
    private final SparseBooleanArray mExpanded = new SparseBooleanArray();

    public LocationAdapter(final Context context, final List<Location> locations) {
        super(context);
        mLocations = locations;
    }

    @Override
    public View newView(final Context context, final int position, final ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_foodservices_location, null, false);
    }

    @Override
    public void bindView(final Context context, final int position, final View view) {

        final Location location = getItem(position);
        final ImageView logoView = (ImageView) view.findViewById(R.id.list_location_logo);
        final TextView titleView = (TextView) view.findViewById(R.id.list_location_title);

        final Spannable wordtoSpan = new SpannableString(location.getName());
        int hyphen = location.getName().indexOf('-');
        if (hyphen == -1) {
            hyphen = wordtoSpan.length();
        }
        wordtoSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, hyphen, 0);
        titleView.setText(wordtoSpan);

        Picasso.with(context)
               .load(location.getLogoUrl())
               .into(logoView);
    }

    @Override
    public int getCount() {
        return mLocations.size();
    }

    @Override
    public Location getItem(final int position) {
        return mLocations.get(position);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(final int position) {
        return true;
    }
}
