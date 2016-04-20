package com.deange.uwaterlooapi.sample.ui.modules;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.deange.uwaterlooapi.annotations.ModuleInfo;
import com.deange.uwaterlooapi.annotations.ModuleMap;
import com.deange.uwaterlooapi.api.BuildingsApi;
import com.deange.uwaterlooapi.api.CoursesApi;
import com.deange.uwaterlooapi.api.EventsApi;
import com.deange.uwaterlooapi.api.FoodServicesApi;
import com.deange.uwaterlooapi.api.NewsApi;
import com.deange.uwaterlooapi.api.ParkingApi;
import com.deange.uwaterlooapi.api.PointsOfInterestApi;
import com.deange.uwaterlooapi.api.ResourcesApi;
import com.deange.uwaterlooapi.api.WeatherApi;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import retrofit.http.GET;

public class ApiMethodsFragment extends ListFragment
        implements
        ModuleListItemListener {

    private static final String ARG_METHODS = "methods";

    private static final Map<Integer, Class> API_CLASSES;
    static {
        final Map<Integer, Class> classes = new LinkedHashMap<>();
        classes.put(R.id.menu_item_foodservices, FoodServicesApi.class);
        classes.put(R.id.menu_item_courses, CoursesApi.class);
        classes.put(R.id.menu_item_events, EventsApi.class);
        classes.put(R.id.menu_item_news, NewsApi.class);
        classes.put(R.id.menu_item_weather, WeatherApi.class);
        classes.put(R.id.menu_item_resources, ResourcesApi.class);
        classes.put(R.id.menu_item_buildings, BuildingsApi.class);
        classes.put(R.id.menu_item_parking, ParkingApi.class);
        classes.put(R.id.menu_item_poi, PointsOfInterestApi.class);
        API_CLASSES = Collections.unmodifiableMap(classes);
    }

    public static String[] getApiEndpoints(final int itemId) {
        // Retrieve all the paths for the given API interface class
        final Set<String> apiPaths = new TreeSet<>();
        final Set<ModuleInfo> modules = new HashSet<>();
        final Method[] methods = API_CLASSES.get(itemId).getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(GET.class)) {
                String path = method.getAnnotation(GET.class).value();
                path = path.replace(".{format}", "");

                // Filter out non-base endpoints and non-existing endpoints
                final ModuleInfo info = ModuleMap.getFragmentInfo(path);
                if (info == null || !info.isBase || modules.contains(info)) {
                    continue;
                }

                modules.add(info);
                apiPaths.add(path);
            }
        }

        return apiPaths.toArray(new String[apiPaths.size()]);
    }

    public static ApiMethodsFragment newInstance(final String[] endpoints) {
        final ApiMethodsFragment fragment = new ApiMethodsFragment();

        final Bundle args = new Bundle();
        args.putStringArray(ARG_METHODS, endpoints);
        fragment.setArguments(args);

        return fragment;
    }

    public static void openModule(final Context context, final String endpoint) {
        final ModuleInfo fragmentInfo = ModuleMap.getFragmentInfo(endpoint);
        context.startActivity(ModuleHostActivity.getStartIntent(context, fragmentInfo.fragment));
    }

    public ApiMethodsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final int padding = (int) (getResources().getDisplayMetrics().density * 4);
        getListView().setPadding(0, padding, 0, padding);
        getListView().setDivider(null);
        getListView().setDividerHeight(0);

        final String[] methods = getArguments().getStringArray(ARG_METHODS);
        if (methods != null) {
            setListAdapter(new ApiMethodsAdapter(getActivity(), methods, ApiMethodsFragment.this));
        }
    }

    @Override
    public void onItemClicked(final int position) {
        final String[] methods = getArguments().getStringArray(ARG_METHODS);
        if (methods != null) {
            openModule(getActivity(), methods[position]);
        }
    }
}
