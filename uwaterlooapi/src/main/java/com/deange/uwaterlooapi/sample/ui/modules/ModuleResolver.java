package com.deange.uwaterlooapi.sample.ui.modules;

import android.content.Context;
import android.util.Log;

import com.deange.uwaterlooapi.api.BuildingsApi;
import com.deange.uwaterlooapi.api.CoursesApi;
import com.deange.uwaterlooapi.api.EventsApi;
import com.deange.uwaterlooapi.api.FoodServicesApi;
import com.deange.uwaterlooapi.api.NewsApi;
import com.deange.uwaterlooapi.api.ResourcesApi;
import com.deange.uwaterlooapi.api.TermsApi;
import com.deange.uwaterlooapi.api.WeatherApi;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.modules.buildings.BuildingFragment;
import com.deange.uwaterlooapi.sample.ui.modules.buildings.ListBuildingsFragment;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexFile;

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


    public static void initMappings(final Context context) {

        if (!FRAGMENTS.isEmpty()) {
            // Already initialized our mappings
            return;
        }

        final String classPrefix = context.getPackageName() + ".ui.modules.";
        final String classSuffix = "Fragment";

        final DexFile dexFile;
        try {
            dexFile = new DexFile(context.getPackageCodePath());
        } catch (final IOException e) {
            Log.e(TAG, "Could not load Dex File", e);
            return;
        }

        for (final Enumeration<String> entries = dexFile.entries(); entries.hasMoreElements(); ) {
            final String clazzName = entries.nextElement();
            try {
                if (clazzName.startsWith(classPrefix) && clazzName.endsWith(classSuffix)) {
                    final Class<?> clazz = Class.forName(clazzName);
                    if (BaseModuleFragment.class.isAssignableFrom(clazz)) {
                        final Class<? extends BaseModuleFragment> moduleClazz =
                                clazz.asSubclass(BaseModuleFragment.class);
                        if (moduleClazz.isAnnotationPresent(ApiFragment.class)) {
                            final ApiFragment info = moduleClazz.getAnnotation(ApiFragment.class);
                            final String endpointName = info.value();

                            // Huzzah!
                            FRAGMENTS.put(endpointName,
                                    new ModuleInfo(moduleClazz, info.isBare()));
                        }
                    }
                }
            } catch (final ClassNotFoundException e) {
                Log.w(TAG, "Class not found?", e);
            }
        }

    }

    public static void initMappingsStatic() {

        // Sometimes, dynamic isn't the best answer
        FRAGMENTS.put("/buildings/list", new ModuleInfo(ListBuildingsFragment.class, true));
        FRAGMENTS.put("/buildings/*", new ModuleInfo(BuildingFragment.class, false));
    }

    public static class ModuleInfo {
        public final Class<? extends BaseModuleFragment> fragment;
        public final boolean isBase;


        public ModuleInfo(final Class<? extends BaseModuleFragment> fragment,
                          final boolean isBase) {
            this.fragment = fragment;
            this.isBase = isBase;
        }
    }
}
