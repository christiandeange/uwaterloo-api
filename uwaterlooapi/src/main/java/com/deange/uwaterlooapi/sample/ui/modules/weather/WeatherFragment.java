package com.deange.uwaterlooapi.sample.ui.modules.weather;

import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.weather.WeatherReading;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.Photo;
import com.deange.uwaterlooapi.sample.model.PhotoDetails;
import com.deange.uwaterlooapi.sample.model.PhotoSize;
import com.deange.uwaterlooapi.sample.model.PhotoUrl;
import com.deange.uwaterlooapi.sample.ui.CoverPhotoPresenter;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.RangeView;
import com.deange.uwaterlooapi.sample.ui.view.SliceView;
import com.deange.uwaterlooapi.sample.utils.DateUtils;
import com.deange.uwaterlooapi.sample.utils.IntentUtils;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ModuleFragment(
        path = "/weather/current",
        layout = R.layout.module_weather
)
public class WeatherFragment extends BaseModuleFragment<Response.Weather, WeatherReading>
        implements ViewTreeObserver.OnScrollChangedListener {

    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());
    private static final float MAX_BEARING_TOLERANCE = 25f;
    private static final String[] DIRECTIONS = {
            "N", "NE", "E", "SE", "S", "SW", "W", "NW", "N",
    };

    private final Set<View> mViewsSeen = new HashSet<>();
    private final Rect mRect = new Rect();

    @Bind(R.id.weather_scrollview) ScrollView mScrollView;
    @Bind(R.id.weather_slider) SliceView mSliceView;
    @Bind(R.id.weather_background) ImageView mBackground;
    @Bind(R.id.weather_temperature) TextView mTemperatureView;
    @Bind(R.id.weather_temperature_range) RangeView mRangeView;
    @Bind(R.id.weather_min_temp) TextView mMinTempView;
    @Bind(R.id.weather_max_temp) TextView mMaxTempView;

    @Bind(R.id.weather_wind_direction_root) View mWindDirectionRoot;
    @Bind(R.id.weather_wind_direction) View mWindDirectionView;
    @Bind(R.id.weather_wind_speed) TextView mWindSpeedView;

    @Bind(R.id.weather_last_updated) TextView mLastUpdated;
    @Bind(R.id.weather_spacer) View mSpacer;
    @Bind(R.id.weather_author_attribution) TextView mAuthor;

    private WeatherReading mLastReading;
    private ValueAnimator mWindTextAnimation;
    private ValueAnimator mWindSpeedAnimation;
    private Photo mPhoto;
    private PhotoSize mPhotoSize;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_weather, null);

        ButterKnife.bind(this, root);

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(this);

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mBackground.setLayoutParams(
                new FrameLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels));

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                resizeTemperatureView();
            }
        });

        mBackground.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
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
                mSpacer.getLayoutParams().height =
                        v.getMeasuredHeight()
                                - getHostActivity().getToolbar().getMeasuredHeight()
                                - getStatusBarHeight();
                mSpacer.requestLayout();
            }
        });

        return root;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onScrollChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mWindTextAnimation != null) {
            mWindTextAnimation.cancel();
        }
        if (mWindSpeedAnimation != null) {
            mWindSpeedAnimation.cancel();
        }

        ButterKnife.unbind(this);
    }

    @Override
    public Response.Weather onLoadData(final UWaterlooApi api) {
        return api.Weather.getWeather();
    }

    @Override
    public void onBindData(final Metadata metadata, final WeatherReading data) {
        mLastReading = data;

        mTemperatureView.setText(formatTemperature(data.getTemperature(), 0));

        mRangeView.setMin(data.getTemperature24hMin());
        mRangeView.setMax(data.getTemperature24hMax());

        mMinTempView.setText(formatTemperature(data.getTemperature24hMin(), 1));
        mMaxTempView.setText(formatTemperature(data.getTemperature24hMax(), 1));

        mLastUpdated.setText(
                getString(R.string.weather_last_updated,
                        DateUtils.formatTime(getActivity(), data.getObservationTime())));

        final String direction = formatBearing(data.getWindDirection());
        final String windSpeed = "  " + data.getWindSpeed() + " kph " + direction + "  ";
        mWindSpeedView.setText(windSpeed);

        final float windSpeedMax = -(Math.min(data.getWindSpeed(), 50f) / 100);
        final float windSpeedMin = windSpeedMax / 5f;

        if (mWindTextAnimation != null) {
            mWindTextAnimation.cancel();
        }
        mWindTextAnimation = ValueAnimator.ofFloat(windSpeedMin, windSpeedMax);
        mWindTextAnimation.setDuration(1000);
        mWindTextAnimation.setInterpolator(new BounceInterpolator());
        mWindTextAnimation.setRepeatMode(ValueAnimator.REVERSE);
        mWindTextAnimation.setRepeatCount(ValueAnimator.INFINITE);
        mWindTextAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                final float value = (float) animation.getAnimatedValue();
                if (mWindSpeedView != null) {
                    mWindSpeedView.getPaint().setTextSkewX(value);
                    mWindSpeedView.getPaint().setTextScaleX(1 + Math.abs(value));
                    mWindSpeedView.invalidate();
                }
            }
        });
        mWindTextAnimation.start();

        final float cappedTolerance = Math.min(data.getWindSpeed(), MAX_BEARING_TOLERANCE);
        final float minDegrees = data.getWindDirection() - cappedTolerance;
        final float maxDegrees = data.getWindDirection() + cappedTolerance;

        if (mWindSpeedAnimation != null) {
            mWindSpeedAnimation.cancel();
        }
        mWindSpeedAnimation = ValueAnimator.ofFloat(minDegrees, maxDegrees);
        mWindSpeedAnimation.setDuration(1000);
        mWindSpeedAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mWindSpeedAnimation.setRepeatMode(ValueAnimator.REVERSE);
        mWindSpeedAnimation.setRepeatCount(ValueAnimator.INFINITE);
        mWindSpeedAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                final float value = (float) animation.getAnimatedValue();
                if (mWindDirectionView != null) {
                    mWindDirectionView.setPivotX(mWindDirectionView.getMeasuredWidth() / 2f);
                    mWindDirectionView.setPivotY(mWindDirectionView.getMeasuredHeight() / 2f);
                    mWindDirectionView.setRotation(value);
                }
            }
        });
        mWindSpeedAnimation.start();
    }

    @Override
    public String getContentType() {
        return ModuleType.WEATHER;
    }

    @Override
    protected void onRefreshRequested() {
        PhotoFetcher.fetchRandom(this);
    }

    @Override
    protected void onContentShown() {
        mViewsSeen.clear();
        onScrollChanged();
    }

    @OnClick(R.id.weather_author_source_link)
    public void onOpenFlickrPhotoClicked() {
        if (mPhoto != null) {
            final List<PhotoUrl> urls = mPhoto.getDetails().getUrls();
            IntentUtils.openBrowser(getContext(),
                    (!urls.isEmpty())
                            ? urls.get(0).getUrl()
                            : getPhotoSize().getUrl());
        }
    }

    private void loadPicture(final Photo photo) {
        mPhoto = photo;
        mPhotoSize = null;

        final PhotoSize size = getPhotoSize();
        Picasso.with(getActivity())
               .load(size.getSource())
               .centerCrop()
               .resize(mBackground.getMeasuredWidth(), mBackground.getMeasuredHeight())
               .into(mBackground);

        final PhotoDetails details = mPhoto.getDetails();
        final String text = getString(
                R.string.weather_author_attribution, details.getTitle(), details.getAuthor().getPreferredName());

        final SpannableString spannable = new SpannableString(text);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, details.getTitle().length(), 0);
        mAuthor.setText(spannable);
    }

    private PhotoSize getPhotoSize() {
        if (mPhotoSize != null) {
            return mPhotoSize;
        }

        final int w = mBackground.getMeasuredWidth();
        final int h = mBackground.getMeasuredHeight();
        final boolean isHeightLarger = (h >= w);

        final List<PhotoSize> photos = new ArrayList<>(mPhoto.getSizes());
        Collections.sort(photos, new Comparator<PhotoSize>() {
            @Override
            public int compare(final PhotoSize lhs, final PhotoSize rhs) {
                return Double.compare(
                        isHeightLarger ? lhs.getHeight() : lhs.getWidth(),
                        isHeightLarger ? rhs.getHeight() : rhs.getWidth()
                );
            }
        });

        // Try to find the smallest image larger than the bounds of the ImageView
        for (final PhotoSize photoSize : photos) {
            if (isHeightLarger && photoSize.getHeight() >= h) {
                mPhotoSize = photoSize;
                break;
            } else if (!isHeightLarger && photoSize.getWidth() >= w) {
                mPhotoSize = photoSize;
                break;
            }
        }

        final String ignoreLabel = "Original";

        if (mPhotoSize == null || ignoreLabel.equals(mPhotoSize.getLabel())) {
            // They are all smaller than the screen, need to scale up
            // Pick the largest one that isn't original (usually it's too big)
            for (int i = photos.size() - 1; i >= 0; --i) {
                final PhotoSize photoSize = photos.get(i);
                if (!ignoreLabel.equals(photoSize.getLabel())) {
                    mPhotoSize = photoSize;
                    break;
                }
            }
        }

        return mPhotoSize;
    }

    private String formatTemperature(final float temp, final int decimals) {
        return String.format("%." + decimals + "f˚", temp);
    }

    private String formatBearing(final float bearing) {
        final float normalizedBearing = (bearing % 360f) + 360f;
        return DIRECTIONS[(int) Math.floor(((normalizedBearing + 22.5f) % 360) / 45)];
    }

    private void animateTemperatureRange() {
        if (mLastReading == null) return;

        final ValueAnimator animator = ValueAnimator.ofFloat(
                mLastReading.getTemperature24hMin(), mLastReading.getTemperature());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                sMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mRangeView.setValue((Float) animation.getAnimatedValue());
                    }
                });
            }
        });

        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.setDuration(1000);
        animator.start();
    }

    private void resizeTemperatureView() {
        // Places the temperature view programmatically
        final ViewGroup sliceParent = ((ViewGroup) mSliceView.getParent());
        final ViewGroup.MarginLayoutParams temperatureParams =
                (ViewGroup.MarginLayoutParams) mTemperatureView.getLayoutParams();
        final int height = mTemperatureView.getMeasuredHeight();

        final DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        final float offset =
                metrics.heightPixels
                        - temperatureParams.bottomMargin
                        - height
                        - temperatureParams.topMargin
                        - mSliceView.getPaddingTop()
                        - getNavBarHeight();

        sliceParent.setPadding(
                mSliceView.getPaddingLeft(),
                (int) offset,
                mSliceView.getPaddingRight(),
                mSliceView.getPaddingBottom()
        );
    }

    public int getStatusBarHeight() {
        final Resources res = getResources();
        final int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        return (resourceId > 0) ? getResources().getDimensionPixelSize(resourceId) : 0;
    }

    private int getNavBarHeight() {
        final Resources res = getResources();
        final int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        return (resourceId > 0) ? getResources().getDimensionPixelSize(resourceId) : 0;
    }

    @Override
    public void onScrollChanged() {
        // Scrollview has moved
        if (firstViewSinceRefresh(mRangeView)) {
            animateTemperatureRange();
        }
    }

    // Note that this is NOT IDEMPOTENT, successive calls for the same view may return false
    private boolean firstViewSinceRefresh(final View v) {
        if (!mViewsSeen.contains(v) && v.getGlobalVisibleRect(mRect)) {
            mViewsSeen.add(v);
            return true;
        }

        return false;
    }

    private static class PhotoFetcher extends AsyncTask<String, Void, Photo> {

        private final WeakReference<WeatherFragment> mFragmentRef;

        public static void fetchRandom(final WeatherFragment fragment) {
            fetch(fragment, null);
        }

        public static void fetch(final WeatherFragment fragment, final String photoId) {
            new PhotoFetcher(fragment).execute(photoId);
        }

        private PhotoFetcher(final WeatherFragment fragment) {
            mFragmentRef = new WeakReference<>(fragment);
        }

        @Override
        protected Photo doInBackground(final String... params) {
            return CoverPhotoPresenter.getPhoto(params[0]);
        }

        @Override
        protected void onPostExecute(final Photo photo) {
            final WeatherFragment fragment = mFragmentRef.get();
            if (fragment != null) {
                fragment.loadPicture(photo);
            }
        }
    }
}
