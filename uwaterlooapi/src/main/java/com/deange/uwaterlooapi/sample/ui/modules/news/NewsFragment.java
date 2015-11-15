package com.deange.uwaterlooapi.sample.ui.modules.news;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Image;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.news.NewsArticle;
import com.deange.uwaterlooapi.model.news.NewsDetails;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.utils.DateUtils;
import com.deange.uwaterlooapi.sample.utils.IntentUtils;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ModuleFragment(path = "/news/*/*")
public class NewsFragment
        extends BaseModuleFragment<Response.NewsEntity, NewsArticle> {

    private NewsArticle mNewsArticle;

    @Bind(R.id.news_title) TextView mTitleView;
    @Bind(R.id.news_image) ImageView mImageBanner;
    @Bind(R.id.news_audience) TextView mAudienceView;

    @Bind(R.id.news_banner_root) View mBannerRoot;
    @Bind(R.id.news_spacer) View mSpacer;
    @Bind(R.id.news_published) TextView mPublishedView;
    @Bind(R.id.news_description) TextView mDescriptionView;
    @Bind(R.id.news_open_in_browser_root) View mBrowserRoot;

    @Override
    protected View getContentView(
            final LayoutInflater inflater,
            final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_news, null);

        ButterKnife.bind(this, root);

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

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    @OnClick(R.id.news_open_in_browser)
    public void onOpenInBrowserClicked() {
        IntentUtils.openBrowser(getActivity(), mNewsArticle.getLink());
    }

    @Override
    public Response.NewsEntity onLoadData(final UWaterlooApi api) {
        final NewsDetails news = getModel();

        return api.News.getNews(news.getSite(), news.getId());
    }

    @Override
    public void onBindData(final Metadata metadata, final NewsArticle data) {
        mNewsArticle = data;

        mTitleView.setText(Html.fromHtml(mNewsArticle.getTitle()).toString());
        mDescriptionView.setText(Html.fromHtml(mNewsArticle.getDescription()).toString());

        final String publishedDate = getString(R.string.news_published,
                DateUtils.formatDate(getContext(), mNewsArticle.getPublishedDate()));
        mPublishedView.setText(publishedDate);

        final String audience = !mNewsArticle.getAudience().isEmpty()
                ? getString(R.string.event_news_audience, Joiner.on(", ").join(data.getAudience()))
                : null;

        ViewUtils.setText(mAudienceView, audience);

        final Image image = mNewsArticle.getImage();
        final String url = (image != null) ? image.getUrl() : null;

        if (url == null) {
            mImageBanner.setVisibility(View.GONE);

        } else {
            mImageBanner.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(url).into(mImageBanner);
        }

        mBrowserRoot.setVisibility((mNewsArticle != null && !TextUtils.isEmpty(mNewsArticle.getLink()))
                        ? View.VISIBLE
                        : View.GONE
        );
    }

}
