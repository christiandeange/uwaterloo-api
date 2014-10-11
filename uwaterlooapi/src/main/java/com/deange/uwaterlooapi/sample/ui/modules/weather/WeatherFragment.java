package com.deange.uwaterlooapi.sample.ui.modules.weather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.weather.WeatherReading;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.SliceView;
import com.deange.uwaterlooapi.sample.utils.DateUtils;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import com.squareup.picasso.Picasso;

public class WeatherFragment extends BaseModuleFragment<Response.Weather, WeatherReading> {

    SliceView mSliceView;
    TextView mTemperatureView;
    TextView mLastUpdated;
    ImageView mBackground;
    ParallaxScrollView mScrollView;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_weather, null);

        mScrollView = (ParallaxScrollView) root.findViewById(R.id.weather_scrollview);
        mSliceView = (SliceView) root.findViewById(R.id.weather_slider);
        mTemperatureView = (TextView) root.findViewById(R.id.weather_temperature);
        mLastUpdated = (TextView) root.findViewById(R.id.weather_last_updated);
        mBackground = (ImageView) root.findViewById(R.id.weather_background);

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
        mTemperatureView.setText(Math.round(data.getTemperature()) + "˚C");
        mLastUpdated.setText(
                getString(R.string.weather_last_updated,
                        DateUtils.formatDate(data.getObservationTime())));

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

}
