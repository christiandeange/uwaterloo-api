package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.foodservices.Announcement;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.DateSelectorView;
import com.deange.uwaterlooapi.sample.utils.DateUtils;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@ModuleFragment(
        path = "/foodservices/announcements",
        layout = R.layout.module_foodservices_announcements
)
public class AnnouncementsFragment
        extends BaseListModuleFragment<Response.Announcements, Announcement>
        implements
        DateSelectorView.OnDateChangedListener {

    @Bind(R.id.fragment_empty_view) View mEmptyView;
    @Bind(R.id.fragment_date_selector) DateSelectorView mDateSelectorView;

    private final List<Announcement> mResponse = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_foodservices_announcements;
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
        final View view = super.getContentView(inflater, parent);
        ButterKnife.bind(this, view);

        mDateSelectorView.setOnDateSetListener(this);

        return view;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_foodservices_announcements);
    }

    @Override
    public ModuleAdapter getAdapter() {
        return new AnnouncementAdapter(getActivity());
    }

    @Override
    public Response.Announcements onLoadData(final UWaterlooApi api) {
        final LocalDate date = mDateSelectorView.getDate();
        final int year = date.getYear();
        final int week = date.getWeekOfWeekyear();

        return api.FoodServices.getAnnouncements(year, week);
    }

    @Override
    public void onBindData(final Metadata metadata, final List<Announcement> data) {
        mResponse.clear();
        for (final Announcement announcement : data) {
            if (!TextUtils.isEmpty(announcement.getText())) {
                // The api frequently sends announcements with no text in them
                mResponse.add(announcement);
            }
        }

        mEmptyView.setVisibility(mResponse.isEmpty() ? View.VISIBLE : View.GONE);
        notifyDataSetChanged();
    }

    @Override
    public String getContentType() {
        return ModuleType.ANNOUNCEMENTS;
    }

    @Override
    public void onDateSet(final int year, final int monthOfYear, final int dayOfMonth) {
        doRefresh();
    }

    private class AnnouncementAdapter extends ModuleAdapter {

        public AnnouncementAdapter(final Context context) {
            super(context);
        }

        @Override
        public View newView(final Context context, final int position, final ViewGroup parent) {
            return LayoutInflater.from(context)
                                 .inflate(R.layout.list_item_foodservices_announcement, parent, false);
        }

        @Override
        public void bindView(final Context context, final int position, final View view) {
            final Announcement announcement = getItem(position);
            ((TextView) view.findViewById(android.R.id.text1)).setText(announcement.getText());
            ((TextView) view.findViewById(android.R.id.text2))
                    .setText(DateUtils.formatDate(announcement.getDate()));
        }

        @Override
        public int getCount() {
            return mResponse == null ? 0 : mResponse.size();
        }

        @Override
        public Announcement getItem(final int position) {
            return mResponse == null ? null : mResponse.get(position);
        }
    }

}
