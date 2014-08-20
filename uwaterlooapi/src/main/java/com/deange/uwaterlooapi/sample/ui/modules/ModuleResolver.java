package com.deange.uwaterlooapi.sample.ui.modules;

import com.deange.uwaterlooapi.api.BuildingsApi;
import com.deange.uwaterlooapi.api.CoursesApi;
import com.deange.uwaterlooapi.api.EventsApi;
import com.deange.uwaterlooapi.api.FoodServicesApi;
import com.deange.uwaterlooapi.api.NewsApi;
import com.deange.uwaterlooapi.api.ResourcesApi;
import com.deange.uwaterlooapi.api.TermsApi;
import com.deange.uwaterlooapi.api.WeatherApi;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.buildings.BuildingFragment;
import com.deange.uwaterlooapi.sample.ui.modules.buildings.ListBuildingsFragment;

import java.util.HashMap;
import java.util.Map;

public class ModuleResolver {

    private static final String TAG = ModuleResolver.class.getSimpleName();
    private static final Map<String, ModuleInfo> FRAGMENTS = new HashMap<>();

    private static final Class[] API_CLASSES = new Class[] {
        FoodServicesApi.class, CoursesApi.class, EventsApi.class, NewsApi.class, WeatherApi.class,
        TermsApi.class, ResourcesApi.class, BuildingsApi.class,
    };

    private ModuleResolver() {
        throw new UnsupportedOperationException();
    }

    public static Class getApiClassForIndex(final int index) {
        // These indices MUST be matched against @array/api_array
        if (index >= 0 && index < API_CLASSES.length) {
            return API_CLASSES[index];
        } else {
            throw new IllegalArgumentException("invalid index " + index);
        }
    }

    public static ModuleInfo getFragmentName(final String endpoint) {

        String path = endpoint;
        path = path.replace(".{format}", "");
        path = path.replaceAll("\\{[^\\}]*\\}", "*");

        return FRAGMENTS.get(path);
    }

    public static void initFragmentMappings() {
        // Sometimes, dynamic isn't the best answer

        FRAGMENTS.put("/buildings/list",
                ModuleInfo.newBuilder(ListBuildingsFragment.class)
                        .base(true)
                        .icon(R.drawable.ic_launcher)
                        .build());

        FRAGMENTS.put("/buildings/*",
                ModuleInfo.newBuilder(BuildingFragment.class)
                        .build());
    }

}
