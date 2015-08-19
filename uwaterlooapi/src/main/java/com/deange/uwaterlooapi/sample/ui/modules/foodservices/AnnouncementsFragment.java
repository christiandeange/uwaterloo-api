package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.Context;
import android.os.Bundle;
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
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.DateSelectorView;
import com.deange.uwaterlooapi.sample.utils.DateUtils;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@ModuleFragment(
        path = "/foodservices/announcements",
        layout = R.layout.module_foodservices_announcements
)
public class AnnouncementsFragment extends BaseListModuleFragment<Response.Announcements, Announcement>
        implements DateSelectorView.OnDateChangedListener {

    private final List<Announcement> mResponse = new ArrayList<>();
    private DateSelectorView mDateSelectorView;
    private View mEmptyView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_foodservices_announcements;
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View root = super.getContentView(inflater, savedInstanceState);
        mEmptyView = root.findViewById(R.id.fragment_empty_view);

        mDateSelectorView = (DateSelectorView) root.findViewById(R.id.fragment_date_selector);
        mDateSelectorView.setOnDateSetListener(this);

        getListView().setDivider(null);
        getListView().setDividerHeight(0);

        return root;
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
    public void onDateSet(final int year, final int monthOfYear, final int dayOfMonth) {
        onRefreshRequested();
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
