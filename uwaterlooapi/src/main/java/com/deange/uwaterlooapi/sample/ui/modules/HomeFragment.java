package com.deange.uwaterlooapi.sample.ui.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.PlatformUtils;

public class HomeFragment
        extends Fragment {

    private float mElevation;
    private Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(null);
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        if (PlatformUtils.hasLollipop()) {
            mElevation = mToolbar.getElevation();
            mToolbar.setElevation(0f);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        if (PlatformUtils.hasLollipop()) {
            mToolbar.setElevation(mElevation);
        }

        super.onDestroyView();
    }
}
