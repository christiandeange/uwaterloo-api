package com.deange.uwaterlooapi.sample.ui.modules.news;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.news.NewsDetails;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import com.deange.uwaterlooapi.sample.utils.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ModuleFragment(
        path = "/news",
        layout = R.layout.module_news
)
public class NewsListFragment
        extends BaseListModuleFragment<Response.News, NewsDetails>
        implements
        ModuleListItemListener {

    private final List<NewsDetails> mResponse = new ArrayList<>();

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final View view = super.getContentView(inflater, savedInstanceState);
        getListView().setStackFromBottom(true);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_simple_listview;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_news);
    }

    @Override
    public ModuleAdapter getAdapter() {
        return new NewsAdapter(getActivity(), this);
    }

    @Override
    public Response.News onLoadData(final UWaterlooApi api) {
        return api.News.getNews();
    }

    @Override
    public void onBindData(final Metadata metadata, final List<NewsDetails> data) {
        mResponse.clear();
        mResponse.addAll(data);

        Collections.sort(mResponse);

        notifyDataSetChanged();
    }

    @Override
    public String getContentType() {
        return ModuleType.NEWS_LIST;
    }

    @Override
    public void onItemClicked(final int position) {
        final NewsDetails news = mResponse.get(position);

        showModule(NewsFragment.class, NewsFragment.newBundle(news));
    }

    private class NewsAdapter
            extends ModuleAdapter {

        public NewsAdapter(final Context context, final ModuleListItemListener listener) {
            super(context, listener);
        }

        @Override
        public View newView(final Context context, final int position, final ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.simple_two_line_card_item, parent, false);
        }

        @Override
        public void bindView(final Context context, final int position, final View view) {
            final NewsDetails news = getItem(position);

            final String title = Html.fromHtml(news.getTitle()).toString();
            final String published = DateUtils.formatDate(getContext(), news.getPublishedDate());

            ((TextView) view.findViewById(android.R.id.text1)).setText(title);
            ((TextView) view.findViewById(android.R.id.text2)).setText(published);
        }

        @Override
        public int getCount() {
            return mResponse.size();
        }

        @Override
        public NewsDetails getItem(final int position) {
            return mResponse.get(position);
        }
    }

}
