package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseMapFragment;
import com.deange.uwaterlooapi.sample.ui.view.OperatingHoursView;
import com.deange.uwaterlooapi.sample.utils.FontUtils;
import com.deange.uwaterlooapi.sample.utils.IntentUtils;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;

public class LocationFragment
        extends BaseMapFragment<BaseResponse, Location>
        implements
        OnMapReadyCallback {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.list_location_collapsing_toolbar) CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.list_location_description) TextView mDescriptionView;
    @BindView(R.id.list_location_open_now) TextView mOpenNowView;
    @BindView(R.id.list_location_hours) OperatingHoursView mHoursView;
    @BindView(R.id.list_location_closed_days) TextView mClosedDays;
    @BindView(R.id.list_location_special_hours) TextView mSpecialDays;

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
        final View view = inflater.inflate(R.layout.fragment_foodservices_location, parent, false);

        ButterKnife.bind(this, view);

        getHostActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        getHostActivity().getToolbar().setVisibility(View.GONE);
        getHostActivity().setSupportActionBar(mToolbar);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_location_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_maps) {
            openLocationInMaps();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Location onLoadData() {
        return getModel();
    }

    @Override
    public void onBindData(final Metadata metadata, final Location location) {
        mToolbarLayout.setTitle(getLocationTitle());

        final String description = location.getDescription();
        if (!TextUtils.isEmpty(description)) {
            mDescriptionView.setText(Html.fromHtml(description));
        }

        final Resources res = getResources();
        final Resources.Theme theme = getContext().getTheme();
        if (location.isOpenNow()) {
            mOpenNowView.setTextColor(ResourcesCompat.getColor(res, R.color.foodservices_location_open, theme));
            mOpenNowView.setText(R.string.foodservices_location_open_now);
        } else {
            mOpenNowView.setTextColor(ResourcesCompat.getColor(res, R.color.foodservices_location_closed, theme));
            mOpenNowView.setText(R.string.foodservices_location_closed_now);
        }

        mHoursView.setHours(location.getHours());

        final List<Location.Range> datesClosed = location.getDatesClosed();
        final List<Location.SpecialRange> specialHours = location.getSpecialOperatingHours();

        showSection(mClosedDays, !datesClosed.isEmpty());
        showSection(mSpecialDays, !specialHours.isEmpty());
        mClosedDays.setText(Joiner.on("\n").joinObjects(datesClosed));
        mSpecialDays.setText(Joiner.on("\n").joinObjects(specialHours));

        // Handle bolding of the current time range (if any)
        mHoursView.unbold();

        final Date now = new Date();

        for (int i = 0; i < datesClosed.size(); i++) {
            if (datesClosed.get(i).contains(now)) {
                boldField(mClosedDays, i);
                return;
            }
        }

        for (int i = 0; i < specialHours.size(); i++) {
            if (specialHours.get(i).contains(now)) {
                boldField(mSpecialDays, i);
                return;
            }
        }

        mHoursView.setTodayBold();
    }

    private void boldField(final TextView textView, final int field) {
        final String text = textView.getText().toString();

        int start = 0;
        for (int i = 0; i < field; i++) {
            start = text.indexOf('\n', start + 1);
            if (start == -1) {
                // Run out of fields!
                return;
            }
        }

        int end = text.indexOf('\n', start + 1);
        if (end == -1) {
            end = text.length();
        }

        final Typeface boldFont = FontUtils.getFont(FontUtils.MEDIUM);

        final SpannableString ss = new SpannableString(text);
        ss.setSpan(new CalligraphyTypefaceSpan(boldFont), start, end, 0);
        textView.setText(ss);
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

    @Override
    public void onMapClick(final LatLng latLng) {
        // Nothing to do here
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        final Location location = getModel();
        final float[] coordinates = location.getLocation();
        final LatLng latLng = new LatLng(coordinates[0], coordinates[1]);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        map.getUiSettings().setAllGesturesEnabled(false);
    }

    private void openLocationInMaps() {
        final Location location = getModel();
        final String uri = IntentUtils.makeGeoIntentString(location.getLocation(), getLocationTitle());

        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        final Context context = getActivity();
        if (IntentUtils.isIntentSupported(context, intent)) {
            startActivity(intent);
        }
    }

    private String getLocationTitle() {
        final Location location = getModel();
        return location.getName().split(" - ")[0];
    }

    @Override
    public String getContentType() {
        return ModuleType.LOCATION;
    }

    @Override
    public int getMapViewId() {
        return R.id.list_location_map_view;
    }
}
