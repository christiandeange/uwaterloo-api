package com.deange.uwaterlooapi.sample.ui.modules;


import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.deange.uwaterlooapi.annotations.ModuleInfo;
import com.deange.uwaterlooapi.annotations.ModuleMap;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.ui.modules.baseflow.Screen;

import java.util.List;

import butterknife.BindView;

public class ApiMethodsFragment
    extends Screen<ApiMethodsKey>
    implements
    ModuleListItemListener {

  @BindView(android.R.id.list) ListView mListView;

  @SuppressWarnings("unchecked")
  public static void openModule(final Context context, final String endpoint) {
    final ModuleInfo fragmentInfo = ModuleMap.getFragmentInfo(endpoint);
    context.startActivity(ModuleHostActivity.getStartIntent(context, fragmentInfo.fragment));
  }

  @Override
  protected void onViewAttached(final View view) {
    final List<String> methods = key().endpoints();
    if (methods != null) {
      mListView.setAdapter(new ApiMethodsAdapter(getActivity(), methods, this));
    } else {
      mListView.setAdapter(null);
    }
  }

  @Override
  public void onItemClicked(final int position) {
    openModule(getActivity(), key().endpoints().get(position));
  }

  @Override
  public String getContentType() {
    return null;
  }
}
