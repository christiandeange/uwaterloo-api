package com.deange.uwaterlooapi.sample.ui.modules.events;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Image;
import com.deange.uwaterlooapi.model.common.MultidayDateRange;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.events.Event;
import com.deange.uwaterlooapi.model.events.EventInfo;
import com.deange.uwaterlooapi.model.events.EventLocation;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.WrapContentListView;
import com.deange.uwaterlooapi.sample.utils.DateUtils;
import com.deange.uwaterlooapi.sample.utils.IntentUtils;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@ModuleFragment(path = "/events/*/*")
public class EventFragment
        extends BaseModuleFragment<Response.EventDetails, EventInfo> {

    private View mRoot;

    @Bind(R.id.event_title) TextView mTitleView;
    @Bind(R.id.event_image) ImageView mImageBanner;
    @Bind(R.id.event_audience) TextView mAudienceView;
    @Bind(R.id.event_cost) TextView mCostView;
    @Bind(R.id.event_location) TextView mLocationView;

    @Bind(R.id.event_banner_root) View mBannerRoot;
    @Bind(R.id.event_spacer) View mSpacer;
    @Bind(R.id.event_times) ListView mTimesListView;
    @Bind(R.id.event_description) TextView mDescriptionView;

    @Override
    protected View getContentView(
            final LayoutInflater inflater,
            final Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_event, null);

        ButterKnife.bind(this, mRoot);

        mBannerRoot.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(
                    final View v,
                    final int left,
                    final int top,
                    final int right,
                    final int bottom,
                    final int oldLeft,
                    final int oldTop,
                    final int oldRight,
                    final int oldBottom) {

                mSpacer.post(new Runnable() {
                    @Override
                    public void run() {
                        mSpacer.getLayoutParams().height = v.getMeasuredHeight();
                        mSpacer.requestLayout();
                    }
                });

            }
        });

        return mRoot;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    @Override
    public Response.EventDetails onLoadData(final UWaterlooApi api) {
        final Event event = getModel();

        return api.Events.getEvent(event.getSite(), event.getId());
    }

    @Override
    public void onBindData(final Metadata metadata, final EventInfo data) {
        mTitleView.setText(Html.fromHtml(data.getTitle()).toString());
        mDescriptionView.setText(Html.fromHtml(data.getDescription()).toString());

        final String audience = !data.getAudience().isEmpty()
                ? getString(R.string.event_audience, Joiner.on(", ").join(data.getAudience()))
                : null;

        final String cost = data.getCost();

        SpannableString locationText = null;
        final EventLocation location = data.getLocation();
        if (location != null) {
            mLocationView.setVisibility(View.VISIBLE);
            mLocationView.setMovementMethod(LinkMovementMethod.getInstance());

            final String locationName = location.getName();
            locationText = new SpannableString(locationName);
            locationText.setSpan(
                    new URLSpan(IntentUtils.makeGeoIntentString(location.getLocation(), locationName)),
                    0, locationText.length(), 0);
        }

        ViewUtils.setText(mAudienceView, audience);
        ViewUtils.setText(mCostView, cost);
        ViewUtils.setText(mLocationView, locationText);

        final Image image = data.getImage();
        final String url = (image != null) ? image.getUrl() : null;

        if (url == null) {
            mImageBanner.setVisibility(View.GONE);

        } else {
            mImageBanner.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(url).into(mImageBanner);
        }

        mTimesListView.setAdapter(new TimesAdapter(getContext(), data.getTimes()));
    }

    private static class TimesAdapter extends ModuleAdapter {

        private final List<MultidayDateRange> mTimes;

        public TimesAdapter(final Context context, final List<MultidayDateRange> times) {
            super(context);
            mTimes = times;
        }

        @Override
        public void bindView(final Context context, final int position, final View view) {
            final MultidayDateRange range = getItem(position);

            final String startDate = DateUtils.formatDate(mContext, range.getStart());
            final String startTime = DateUtils.formatTime(mContext, range.getStart());
            final String endDate = DateUtils.formatDate(mContext, range.getStart());
            final String endTime = DateUtils.formatTime(mContext, range.getStart());

            String result = startDate + ", " + startTime;
            if (!TextUtils.equals(startDate, endDate)) {
                result += " – " + endDate + ", " + endTime;
            } else {
                if (!TextUtils.equals(startTime, endTime)) {
                    result += " – " + endTime;
                }
            }

            ((TextView) view.findViewById(android.R.id.text1)).setText(result);
        }

        @Override
        public int getCount() {
            return mTimes.size();
        }

        @Override
        public MultidayDateRange getItem(final int position) {
            return mTimes.get(position);
        }

        @Override
        public int getListItemLayoutId() {
            return R.layout.list_item_event_time;
        }
    }

}
