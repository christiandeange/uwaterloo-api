package com.deange.uwaterlooapi.sample.ui.modules;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.MainActivity;
import com.deange.uwaterlooapi.sample.ui.modules.buildings.ListBuildingsFragment;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;

import retrofit.http.GET;

public class SampleHostFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_METHODS = "methods";
    private static final String ARG_POSITION = "position";
    private Spinner mMethodSpinner;
    private UWaterlooApi mApi;

    public static SampleHostFragment newInstance(final int position) {

        SampleHostFragment fragment = new SampleHostFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);

        // Retrieve all the paths for the given API interface class
        Set<String> apiPaths = new TreeSet<>();
        final Method[] methods = MainActivity.getApiForIndex(position).getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(GET.class)) {
                String path = method.getAnnotation(GET.class).value();
                path = path.replace(".{format}", "");
                apiPaths.add(path);
            }
        }
        args.putStringArray(ARG_METHODS, apiPaths.toArray(new String[apiPaths.size()]));
        fragment.setArguments(args);
        return fragment;
    }

    public SampleHostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sample_host, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View root = getView();

        final String[] methods = getArguments().getStringArray(ARG_METHODS);

        mMethodSpinner = (Spinner) root.findViewById(R.id.method_spinner);
        mMethodSpinner.setOnItemSelectedListener(this);
        mMethodSpinner.setAdapter(new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, methods));
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        final MainActivity parent = (MainActivity) activity;
        parent.onSectionAttached(getArguments().getInt(ARG_POSITION));
        mApi = parent.getApi();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mApi = null;
    }

    @Override
    public void onItemSelected(final AdapterView<?> adapterView, final View view,
                               final int position, final long id) {
        Toast.makeText(getActivity(), String.valueOf(mMethodSpinner.getAdapter().getItem(position)),
                Toast.LENGTH_SHORT).show();

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.container_method_view, new ListBuildingsFragment())
                .commit();
    }

    @Override
    public void onNothingSelected(final AdapterView<?> adapterView) {
        // Nothing to do here
    }

    /* package */ UWaterlooApi getApi() {
        return mApi;
    }
}
