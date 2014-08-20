package com.deange.uwaterlooapi.sample.ui.modules;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.deange.uwaterlooapi.sample.ui.MainActivity;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;

import retrofit.http.GET;

public class ApiMethodsFragment extends ListFragment
        implements AdapterView.OnItemClickListener {

    private static final String ARG_METHODS = "methods";
    private static final String ARG_POSITION = "position";

    public static ApiMethodsFragment newInstance(final int position) {

        ApiMethodsFragment fragment = new ApiMethodsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);

        // Retrieve all the paths for the given API interface class
        Set<String> apiPaths = new TreeSet<>();
        final Method[] methods = ModuleResolver.getApiClassForIndex(position).getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(GET.class)) {
                String path = method.getAnnotation(GET.class).value();
                path = path.replace(".{format}", "");

                // Filter out non-base endpoints
//                final ModuleResolver.ModuleInfo info = ModuleResolver.getFragmentName(path);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String[] methods = getArguments().getStringArray(ARG_METHODS);

        getListView().setOnItemClickListener(this);
        setListAdapter(new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, methods));
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        final MainActivity parent = (MainActivity) activity;
        parent.onSectionAttached(getArguments().getInt(ARG_POSITION));
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view,
                               final int position, final long id) {

        final String endpoint = String.valueOf(getListAdapter().getItem(position));
        final ModuleResolver.ModuleInfo fragmentInfo = ModuleResolver.getFragmentName(endpoint);
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
