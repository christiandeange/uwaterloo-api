package com.deange.uwaterlooapi.sample.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.deange.uwaterlooapi.sample.R;

public abstract class ModuleAdapter
    extends BaseAdapter
    implements
    View.OnClickListener {

  protected final Context mContext;
  protected final ModuleListItemListener mListener;

  public ModuleAdapter(final Context context) {
    this(context, null);
  }

  public ModuleAdapter(final Context context, final ModuleListItemListener listener) {
    mContext = context;
    mListener = listener;
  }

  @Override
  public View getView(final int position, final View convertView, final ViewGroup parent) {
    View v;
    if (convertView == null) {
      v = newView(mContext, position, parent);
    } else {
      v = convertView;
    }

    if (mListener != null) {
      View selectable = v.findViewById(R.id.selectable);
      if (selectable == null) {
        selectable = v;
      }

      if (selectable != null) {
        selectable.setTag(position);
        selectable.setOnClickListener(this);
      }
    }

    bindView(mContext, position, v);
    return v;
  }

  @Override
  public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
    View v;
    if (convertView == null) {
      v = newDropDownView(mContext, position, parent);
    } else {
      v = convertView;
    }
    bindView(mContext, position, v);
    return v;
  }

  @Override
  public long getItemId(final int position) {
    return position;
  }

  @Override
  public boolean isEnabled(final int position) {
    return false;
  }

  public View newDropDownView(final Context context, final int position, final ViewGroup parent) {
    return newView(context, position, parent);
  }

  public View newView(final Context context, final int position, final ViewGroup parent) {
    return LayoutInflater.from(context).inflate(getListItemLayoutId(), null);
  }

  @Override
  public final void onClick(final View v) {
    if (mListener != null) {
      mListener.onItemClicked((int) v.getTag());
    }
  }

  public int getListItemLayoutId() {
    return android.R.layout.simple_list_item_1;
  }

  public abstract void bindView(final Context context, final int position, final View view);

}
