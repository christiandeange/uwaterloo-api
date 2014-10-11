package com.deange.uwaterlooapi.sample.ui.modules.weather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Response;
import com.deange.uwaterlooapi.model.weather.WeatherReading;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.SliceView;
import com.deange.uwaterlooapi.sample.utils.DateUtils;

public class WeatherFragment extends BaseModuleFragment<Response.Weather, WeatherReading> implements View.OnLayoutChangeListener {

    SliceView mSliceView;
    TextView mTemperatureView;
    TextView mLastUpdated;

    @Override
    protected View getContentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_weather, null);

        mSliceView = (SliceView) root.findViewById(R.id.weather_slider);
        mTemperatureView = (TextView) root.findViewById(R.id.weather_temperature);
        mLastUpdated = (TextView) root.findViewById(R.id.weather_last_updated);
        mTemperatureView.addOnLayoutChangeListener(this);

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

    @Override
    public void onLayoutChange(final View v, final int left, final int top, final int right,
                               final int bottom, final int oldLeft, final int oldTop,
                               final int oldRight, final int oldBottom) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // For when the temperature view size changes
                final int[] sliceParentLocation = new int[2];
                final ViewGroup sliceParent = ((ViewGroup) mSliceView.getParent());
                sliceParent.getLocationInWindow(sliceParentLocation);

                final RelativeLayout.LayoutParams temperatureParams =
                        (RelativeLayout.LayoutParams) mTemperatureView.getLayoutParams();
                final RelativeLayout.LayoutParams parentParams =
                        (RelativeLayout.LayoutParams) mSliceView.getLayoutParams();
                final int height = mTemperatureView.getMeasuredHeight();

                final DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

                final float offset =
                          metrics.heightPixels
                        - temperatureParams.bottomMargin
                        - height
                        - temperatureParams.topMargin
                        - mSliceView.getPaddingTop()
                        - sliceParentLocation[1]
                        - getNavBarHeight();

                sliceParent.setPadding(
                        mSliceView.getPaddingLeft(),
                        (int) offset,
                        mSliceView.getPaddingRight(),
                        mSliceView.getPaddingBottom()
                );
            }
        }, 500);


    }

    private int getNavBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
