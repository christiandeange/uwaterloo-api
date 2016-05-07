package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.OperatingHoursView;
import com.deange.uwaterlooapi.sample.utils.FontUtils;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;

public class LocationFragment
        extends BaseModuleFragment<BaseResponse, Location> {

    @BindView(R.id.list_location_title) TextView mTitleView;
    @BindView(R.id.list_location_building) TextView mLocationView;
    @BindView(R.id.list_location_description) TextView mDescriptionView;
    @BindView(R.id.list_location_open_now) TextView mOpenNowView;
    @BindView(R.id.list_location_hours) OperatingHoursView mHoursView;
    @BindView(R.id.list_location_closed_days) TextView mClosedDays;
    @BindView(R.id.list_location_special_hours) TextView mSpecialDays;

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
        final View view = inflater.inflate(R.layout.fragment_foodservices_location, parent, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public Location onLoadData() {
        return getModel();
    }

    @Override
    public void onBindData(final Metadata metadata, final Location location) {
        final String[] split = location.getName().split(" - ");

        mTitleView.setText(split[0]);
        ViewUtils.setText(mLocationView, (split.length == 2) ? split[1] : null);

        if (!TextUtils.isEmpty(location.getDescription())) {
            mDescriptionView.setText(Html.fromHtml(location.getDescription()));
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
