package com.deange.uwaterlooapi.sample.ui.modules.events;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Image;
import com.deange.uwaterlooapi.model.common.MultidayDateRange;
import com.deange.uwaterlooapi.model.common.Responses;
import com.deange.uwaterlooapi.model.events.Event;
import com.deange.uwaterlooapi.model.events.EventInfo;
import com.deange.uwaterlooapi.model.events.EventLocation;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.utils.DateUtils;
import com.deange.uwaterlooapi.sample.utils.IntentUtils;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

@ModuleFragment(path = "/events/*/*")
public class EventFragment
        extends BaseModuleFragment<Responses.EventDetails, EventInfo> {

    private EventInfo mEventInfo;
    private View mRoot;

    @BindView(R.id.event_title) TextView mTitleView;
    @BindView(R.id.event_image) ImageView mImageBanner;
    @BindView(R.id.event_audience) TextView mAudienceView;
    @BindView(R.id.event_cost) TextView mCostView;
    @BindView(R.id.event_location) TextView mLocationView;

    @BindView(R.id.event_banner_root) View mBannerRoot;
    @BindView(R.id.event_spacer) View mSpacer;
    @BindView(R.id.event_times) ListView mTimesListView;
    @BindView(R.id.event_description) TextView mDescriptionView;
    @BindView(R.id.event_open_in_browser_root) View mBrowserRoot;

    @Override
    protected View getContentView(
            final LayoutInflater inflater,
            final ViewGroup parent) {
        mRoot = inflater.inflate(R.layout.fragment_event, parent, false);

        ButterKnife.bind(this, mRoot);

        mBannerRoot.addOnLayoutChangeListener(
                (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) ->
                        mSpacer.post(() -> {
                            mSpacer.getLayoutParams().height = v.getMeasuredHeight();
                            mSpacer.requestLayout();
                        }));

        return mRoot;
    }

    @Override
    public float getToolbarElevationPx() {
        return 0;
    }

    @OnClick(R.id.event_open_in_browser)
    public void onOpenInBrowserClicked() {
        IntentUtils.openBrowser(getActivity(), mEventInfo.getLink());
    }

    @Override
    public Call<Responses.EventDetails> onLoadData(final UWaterlooApi api) {
        final Event event = getModel();

        return api.Events.getEvent(event.getSite(), event.getId());
    }

    @Override
    public void onBindData(final Metadata metadata, final EventInfo data) {
        mEventInfo = data;

        final String title = mEventInfo.getTitle();
        if (!TextUtils.isEmpty(title)) {
            mTitleView.setText(Html.fromHtml(title).toString());
        }

        final String description = mEventInfo.getDescription();
        if (!TextUtils.isEmpty(description)) {
            mDescriptionView.setText(Html.fromHtml(description).toString());
        }

        final String audience = !mEventInfo.getAudience().isEmpty()
                ? getString(R.string.event_news_audience, Joiner.on(", ").join(data.getAudience()))
                : null;

        final String cost = mEventInfo.getCost();

        SpannableString locationText = null;
        final EventLocation location = mEventInfo.getLocation();
        if (location != null && !TextUtils.isEmpty(location.getName())) {
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

        final Image image = mEventInfo.getImage();
        final String url = (image != null) ? image.getUrl() : null;

        if (url == null) {
            mImageBanner.setVisibility(View.GONE);

        } else {
            mImageBanner.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(url).into(mImageBanner);
        }

        mTimesListView.setAdapter(new TimesAdapter(getContext(), mEventInfo.getTimes()));

        mBrowserRoot.setVisibility((mEventInfo != null && !TextUtils.isEmpty(mEventInfo.getLink()))
                ? View.VISIBLE
                : View.GONE
        );
    }

    @Override
    public String getContentType() {
        return ModuleType.EVENT;
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
