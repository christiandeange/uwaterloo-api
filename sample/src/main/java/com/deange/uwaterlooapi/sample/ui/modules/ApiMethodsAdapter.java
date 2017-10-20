package com.deange.uwaterlooapi.sample.ui.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.deange.uwaterlooapi.annotations.ModuleInfo;
import com.deange.uwaterlooapi.annotations.ModuleMap;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;

import java.util.List;

public class ApiMethodsAdapter extends ArrayAdapter<String>
    implements View.OnClickListener {

  private final LayoutInflater mInflater;
  private final ModuleListItemListener mListener;

  public ApiMethodsAdapter(
      final Context context,
      final List<String> objects,
      final ModuleListItemListener listener) {
    super(context, 0, objects);
    mInflater = LayoutInflater.from(context);
    mListener = listener;
  }

  @NonNull
  @Override
  public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
    final String endpoint = getItem(position);
    final ModuleInfo info = ModuleMap.getFragmentInfo(endpoint);

    if (info == null || !info.isBase || info.layout == 0) {
      throw new RuntimeException(endpoint + " is not a base module!");
    }

    final View view;
    if (convertView == null) {
      view = mInflater.inflate(info.layout, parent, false);
    } else {
      view = convertView;
    }

    final View selectable = view.findViewById(R.id.selectable);
    if (selectable != null) {
      selectable.setTag(position);
      selectable.setOnClickListener(this);
    }

    return view;
  }

  @Override
  public int getItemViewType(final int position) {
    return position;
  }

  @Override
  public int getViewTypeCount() {
    return getCount();
  }

  @Override
  public boolean isEnabled(final int position) {
    return false;
  }

  @Override
  public boolean areAllItemsEnabled() {
    return false;
  }

  @Override
  public void onClick(final View v) {
    final int position = (int) v.getTag();
    if (mListener != null) {
      mListener.onItemClicked(position);
    }
  }

}
