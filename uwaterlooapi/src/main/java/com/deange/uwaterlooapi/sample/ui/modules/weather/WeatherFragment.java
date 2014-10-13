package com.deange.uwaterlooapi.sample.ui.modules.weather;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.weather.WeatherReading;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.RangeView;
import com.deange.uwaterlooapi.sample.ui.view.SliceView;
import com.deange.uwaterlooapi.sample.utils.DateUtils;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class WeatherFragment extends BaseModuleFragment<Response.Weather, WeatherReading>
        implements ViewTreeObserver.OnScrollChangedListener {

    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());

    private final Set<View> mViewsSeen = new HashSet<>();
    private final Rect mRect = new Rect();

    SliceView mSliceView;
    ImageView mBackground;
    ParallaxScrollView mScrollView;

    TextView mTemperatureView;
    TextView mMinTempView;
    TextView mMaxTempView;
    RangeView mRangeView;
    TextView mLastUpdated;

    private WeatherReading mLastReading;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_weather, null);

        mScrollView = (ParallaxScrollView) root.findViewById(R.id.weather_scrollview);
        mSliceView = (SliceView) root.findViewById(R.id.weather_slider);
        mBackground = (ImageView) root.findViewById(R.id.weather_background);

        mTemperatureView = (TextView) root.findViewById(R.id.weather_temperature);
        mRangeView = (RangeView) root.findViewById(R.id.weather_temperature_range);
        mMinTempView = (TextView) root.findViewById(R.id.weather_min_temp);
        mMaxTempView = (TextView) root.findViewById(R.id.weather_max_temp);
        mLastUpdated = (TextView) root.findViewById(R.id.weather_last_updated);

        final int thumbDiameter = mRangeView.getThumbRadius() * 2;
        mMinTempView.setTextSize(TypedValue.COMPLEX_UNIT_PX, thumbDiameter);
        mMaxTempView.setTextSize(TypedValue.COMPLEX_UNIT_PX, thumbDiameter);

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(this);

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mBackground.setLayoutParams(
                new FrameLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels));

        Picasso.with(getActivity())
                .load("https://farm4.staticflickr.com/3807/10395423826_71a309c66b_c.jpg")
                .centerCrop()
                .resize(metrics.widthPixels, metrics.heightPixels)
                .into(mBackground);

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                resizeTemperatureView();
            }
        });

        return root;
    }

    @Override
    public Response.Weather onLoadData(final UWaterlooApi api) {
        return api.Weather.getWeather();
    }

    @Override
    public void onBindData(final Metadata metadata, final WeatherReading data) {
        mLastReading = data;

        mTemperatureView.setText(formatTemperature(data.getTemperature(), 0) + "˚");

        mRangeView.setMin(data.getTemperature24hMin());
        mRangeView.setMax(data.getTemperature24hMax());
        mRangeView.setThumbShown(false);    // So that we can animate it once shown

        mMinTempView.setText(formatTemperature(data.getTemperature24hMin(), 1) + "˚");
        mMaxTempView.setText(formatTemperature(data.getTemperature24hMax(), 1) + "˚");

        mLastUpdated.setText(
                getString(R.string.weather_last_updated,
                        DateUtils.formatDateTime(data.getObservationTime())));

    }

    @Override
    protected void onContentShown() {
        mViewsSeen.clear();
        onScrollChanged();
    }

    private String formatTemperature(final float temp, final int decimals) {
        return String.format("%." + decimals + "f", temp);
    }

    private void animateTemperatureRange() {
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

        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.start();
    }

    private void resizeTemperatureView() {
        // Places the temperature view programatically
        final ViewGroup sliceParent = ((ViewGroup) mSliceView.getParent());
        final RelativeLayout.LayoutParams temperatureParams =
                (RelativeLayout.LayoutParams) mTemperatureView.getLayoutParams();
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

    private int getNavBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
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
}
