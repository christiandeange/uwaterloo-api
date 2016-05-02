package com.deange.uwaterlooapi.sample.ui.modules.news;

import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.news.NewsArticle;
import com.deange.uwaterlooapi.model.news.NewsDetails;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.utils.DateUtils;
import com.deange.uwaterlooapi.sample.utils.IntentUtils;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ModuleFragment(path = "/news/*/*")
public class NewsFragment
        extends BaseModuleFragment<Response.NewsEntity, NewsArticle> {

    private static final String TAG = "NewsFragment";

    private NewsArticle mNewsArticle;

    @Bind(R.id.news_title) TextView mTitleView;
    @Bind(R.id.news_audience) TextView mAudienceView;

    @Bind(R.id.news_banner_root) View mBannerRoot;
    @Bind(R.id.news_spacer) View mSpacer;
    @Bind(R.id.news_published) TextView mPublishedView;
    @Bind(R.id.news_description) TextView mDescriptionView;
    @Bind(R.id.news_open_in_browser_root) View mBrowserRoot;

    @Override
    protected View getContentView(
            final LayoutInflater inflater,
            final ViewGroup parent) {
        final View root = inflater.inflate(R.layout.fragment_news, parent, false);

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

        mDescriptionView.setMovementMethod(LinkMovementMethod.getInstance());

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

        // Remove img tags from HTML
        final String rawHtml = mNewsArticle.getHtmlDescription().replaceAll("<img([^>]+?)>", "");
        final Spanned html = Html.fromHtml(rawHtml);
        final Spannable text = Spannable.Factory.getInstance().newSpannable(html);
        for (final ImageSpan span : html.getSpans(0, html.length() - 1, ImageSpan.class)) {
            text.removeSpan(span);
        }

        mDescriptionView.setText(text);

        final String publishedDate = getString(R.string.news_published,
                DateUtils.formatDate(getContext(), mNewsArticle.getPublishedDate()));
        mPublishedView.setText(publishedDate);

        final String audience = !data.getAudience().isEmpty()
                ? Joiner.on(", ").join(data.getAudience())
                : null;

        ViewUtils.setText(mAudienceView, audience);

        mBrowserRoot.setVisibility((mNewsArticle != null && !TextUtils.isEmpty(mNewsArticle.getLink()))
                ? View.VISIBLE
                : View.GONE
        );
    }

    @Override
    public String getContentType() {
        return ModuleType.NEWS;
    }

}

