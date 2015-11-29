package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.OperatingHoursView;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

public class LocationFragment extends BaseModuleFragment<BaseResponse, Location> {

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_foodservices_location, null);
    }

    @Override
    public Location onLoadData() {
        return getModel();
    }

    @Override
    public void onBindData(final Metadata metadata, final Location location) {

        final View view = getView();

        final TextView titleView = (TextView) view.findViewById(R.id.list_location_title);
        final TextView locationView = (TextView) view.findViewById(R.id.list_location_building);
        final TextView descriptionView = (TextView) view.findViewById(R.id.list_location_description);
        final TextView openNowView = (TextView) view.findViewById(R.id.list_location_open_now);

        final OperatingHoursView hoursView = (OperatingHoursView) view.findViewById(R.id.list_location_hours);
        final TextView closedDays = (TextView) view.findViewById(R.id.list_location_closed_days);
        final TextView specialDays = (TextView) view .findViewById(R.id.list_location_special_hours);

        final String[] split = location.getName().split(" - ");

        titleView.setText(split[0]);
        ViewUtils.setText(locationView, (split.length == 2) ? split[1] : null);

        if (!TextUtils.isEmpty(location.getDescription())) {
            descriptionView.setText(Html.fromHtml(location.getDescription()));
        }

        if (location.isOpenNow()) {
            openNowView.setTextColor(getResources().getColor(R.color.foodservices_location_open));
            openNowView.setText(R.string.foodservices_location_open_now);
        } else {
            openNowView.setTextColor(getResources().getColor(R.color.foodservices_location_closed));
            openNowView.setText(R.string.foodservices_location_closed_now);
        }

        hoursView.setHours(location.getHours());

        final List<Location.Range> datesClosed = location.getDatesClosed();
        final List<Location.SpecialRange> specialHours = location.getSpecialOperatingHours();

        showSection(closedDays, !datesClosed.isEmpty());
        showSection(specialDays, !specialHours.isEmpty());
        closedDays.setText(Joiner.on("\n").joinObjects(datesClosed));
        specialDays.setText(Joiner.on("\n").joinObjects(specialHours));
    }

    @Override
    public String getContentType() {
        return ModuleType.LOCATION;
    }

    private void showSection(final TextView view, final boolean show) {

        final int visibility = show ? View.VISIBLE : View.GONE;
        final ViewGroup parent = (ViewGroup) view.getParent();
        final ViewGroup mainParent = (ViewGroup) parent.getParent();
        final int parentIndex = mainParent.indexOfChild(parent);
        final int dividerIndex = parentIndex - 1;

        mainParent.getChildAt(parentIndex).setVisibility(visibility);
        mainParent.getChildAt(dividerIndex).setVisibility(visibility);
    }
}
