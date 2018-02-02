package com.deange.uwaterlooapi.sample.ui.modules;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import com.deange.uwaterlooapi.annotations.ModuleInfo;
import com.deange.uwaterlooapi.annotations.ModuleMap;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.utils.Px;

import javax.inject.Inject;

import static com.deange.uwaterlooapi.sample.dagger.Components.component;

public class ApiMethodsFragment extends ListFragment
    implements
    ModuleListItemListener {

  private static final String ARG_METHODS = "methods";

  @Inject Px mPx;

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
  public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    component(this).inject(this);
  }

  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    final int padding = mPx.fromDp(4);
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
