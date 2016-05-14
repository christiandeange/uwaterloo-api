package com.deange.uwaterlooapi.sample.ui.modules.events;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.events.Event;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import com.deange.uwaterlooapi.sample.utils.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

@ModuleFragment(
        path = "/events",
        layout = R.layout.module_events
)
public class EventsFragment
        extends BaseListModuleFragment<Response.Events, Event>
        implements
        ModuleListItemListener {

    private final List<Event> mResponse = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_simple_listview;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_events);
    }

    @Override
    public ModuleAdapter getAdapter() {
        return new EventAdapter(getActivity(), this);
    }

    @Override
    public Call<Response.Events> onLoadData(final UWaterlooApi api) {
        return api.Events.getEvents();
    }

    @Override
    public void onBindData(final Metadata metadata, final List<Event> data) {
        mResponse.clear();
        mResponse.addAll(data);

        Collections.sort(mResponse);

        notifyDataSetChanged();

        final Date now = new Date();
        final EventAdapter adapter = (EventAdapter) getListView().getAdapter();

        if (adapter != null) {
            // Position the next up event in the middle of the screen,
            // and then round towards the next item visible
            final int offset = getListView().getMeasuredHeight() / 2;
            for (int i = 0; i < adapter.getCount(); ++i) {
                if (Event.getNext(adapter.getItem(i).getTimes(), now).after(now)) {

                    getListView().setSelectionFromTop(i, offset);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            final int currentFirst = getListView().getFirstVisiblePosition();
                            final int nextFirst = Math.min(adapter.getCount() - 1, currentFirst + 1);
                            getListView().setSelectionFromTop(nextFirst, 0);
                        }
                    });

                    break;
                }
            }
        }
    }

    @Override
    public String getContentType() {
        return ModuleType.EVENTS;
    }

    @Override
    public void onItemClicked(final int position) {
        final Event event = mResponse.get(position);

        showModule(EventFragment.class, EventFragment.newBundle(event));
    }

    private class EventAdapter
            extends ModuleAdapter {

        private final Date mNow = new Date();

        public EventAdapter(final Context context, final ModuleListItemListener listener) {
            super(context, listener);
        }

        @Override
        public View newView(final Context context, final int position, final ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_item_event, parent, false);
        }

        @Override
        public void bindView(final Context context, final int position, final View view) {
            final Event event = getItem(position);

            final String title = Html.fromHtml(event.getTitle()).toString();
            final String siteName = Html.fromHtml(event.getSiteName()).toString();
            final String date = DateUtils.getTimeDifference(getResources(), Event.getNext(event.getTimes()).getTime());

            ((TextView) view.findViewById(android.R.id.text1)).setText(title);
            ((TextView) view.findViewById(android.R.id.text2)).setText(siteName);
            ((TextView) view.findViewById(android.R.id.summary)).setText(date);

            view.setAlpha((Event.getNext(event.getTimes(), mNow).before(mNow)) ? 0.5f : 1.0f);
        }

        @Override
        public int getCount() {
            return mResponse.size();
        }

        @Override
        public Event getItem(final int position) {
            return mResponse.get(position);
        }
    }

}
