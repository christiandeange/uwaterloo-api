package com.deange.uwaterlooapi.sample.ui.modules;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.deange.uwaterlooapi.annotations.ModuleInfo;
import com.deange.uwaterlooapi.annotations.ModuleMap;
import com.deange.uwaterlooapi.api.BuildingsApi;
import com.deange.uwaterlooapi.api.CoursesApi;
import com.deange.uwaterlooapi.api.EventsApi;
import com.deange.uwaterlooapi.api.FoodServicesApi;
import com.deange.uwaterlooapi.api.NewsApi;
import com.deange.uwaterlooapi.api.ResourcesApi;
import com.deange.uwaterlooapi.api.TermsApi;
import com.deange.uwaterlooapi.api.WeatherApi;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.MainActivity;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;

import retrofit.http.GET;

public class ApiMethodsFragment extends ListFragment
        implements
        ModuleListItemListener {

    private static final String ARG_METHODS = "methods";
    private static final String ARG_POSITION = "position";
    private static final Class[] API_CLASSES = new Class[] {
            FoodServicesApi.class,
            CoursesApi.class,
            EventsApi.class,
            NewsApi.class,
            WeatherApi.class,
            TermsApi.class,
            ResourcesApi.class,
            BuildingsApi.class,
    };

    public static ApiMethodsFragment newInstance(final int position) {

        ApiMethodsFragment fragment = new ApiMethodsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);

        // Retrieve all the paths for the given API interface class
        Set<String> apiPaths = new TreeSet<>();
        final Method[] methods = API_CLASSES[position].getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(GET.class)) {
                String path = method.getAnnotation(GET.class).value();
                path = path.replace(".{format}", "");

                // Filter out non-base endpoints
//                final ModuleResolver.ModuleInfo info = ModuleResolver.getFragmentInfo(path);
//                if (info == null || !info.isBase) {
//                    continue;
//                }
                apiPaths.add(path);
            }
        }

        args.putStringArray(ARG_METHODS, apiPaths.toArray(new String[apiPaths.size()]));
        fragment.setArguments(args);
        return fragment;
    }

    public ApiMethodsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String[] methods = getArguments().getStringArray(ARG_METHODS);
        setListAdapter(new ApiMethodsAdapter(getActivity(), methods, this));

        getListView().setDivider(null);
        getListView().setDividerHeight(0);

        final int padding = (int) (getResources().getDisplayMetrics().density * 4);
        getListView().setPadding(0, padding, 0, padding);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        final MainActivity parent = (MainActivity) activity;
        parent.onSectionAttached(getArguments().getInt(ARG_POSITION));
    }

    @Override
    public void onItemClicked(final int position) {
        final String endpoint = String.valueOf(getListAdapter().getItem(position));
        final ModuleInfo fragmentInfo = ModuleMap.getFragmentInfo(endpoint);
        if (fragmentInfo == null) {
            Toast.makeText(getActivity(), "No fragment for " + endpoint, Toast.LENGTH_SHORT).show();

        } else if (!fragmentInfo.isBase) {
            Toast.makeText(getActivity(),
                    fragmentInfo.fragment.getSimpleName() + " is not a base fragment",
                    Toast.LENGTH_SHORT).show();

        } else {
            startActivity(ModuleHostActivity.getStartIntent(getActivity(), fragmentInfo.fragment));
        }
    }
}
