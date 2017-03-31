package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.UWaterlooApi;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Responses;
import com.deange.uwaterlooapi.model.foodservices.WatcardVendor;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import com.deange.uwaterlooapi.sample.utils.PlatformUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

//@ModuleFragment(
//        path = "/foodservices/watcard",
//        layout = R.layout.module_foodservices_watcards
//)
public class WatcardFragment
        extends BaseListModuleFragment<Responses.Watcards, WatcardVendor> {

    private final List<WatcardVendor> mResponse = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_simple_listview;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_foodservices_watcard);
    }

    @Override
    public ModuleAdapter getAdapter() {
        return new WatcardVendorAdapter(getActivity());
    }

    @Override
    public Call<Responses.Watcards> onLoadData(final UWaterlooApi api) {
        return api.FoodServices.getWatcardVendors();
    }

    @Override
    public void onBindData(final Metadata metadata, final List<WatcardVendor> data) {
        mResponse.clear();
        mResponse.addAll(data);

        Collections.sort(mResponse, (lhs, rhs) -> lhs.getName().compareTo(rhs.getName()));

        notifyDataSetChanged();
    }

    @Override
    public String getContentType() {
        return ModuleType.WATCARD_VENDORS;
    }

    private class WatcardVendorAdapter
            extends ModuleAdapter
            implements
            View.OnLongClickListener {

        public WatcardVendorAdapter(final Context context) {
            super(context);
        }

        @Override
        public View newView(final Context context, final int position, final ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.simple_one_line_card_item, parent, false);
        }

        @Override
        public void bindView(final Context context, final int position, final View view) {
            final WatcardVendor vendor = getItem(position);
            ((TextView) view.findViewById(android.R.id.text1)).setText(vendor.getName());

            final View selectableView = view.findViewById(R.id.selectable);
            selectableView.setTag(position);
            selectableView.setLongClickable(true);
            selectableView.setOnLongClickListener(this);
        }

        @Override
        public int getCount() {
            return mResponse.size();
        }

        @Override
        public WatcardVendor getItem(final int position) {
            return mResponse.get(position);
        }

        @Override
        public boolean onLongClick(final View v) {
            final int position = (int) v.getTag();
            PlatformUtils.copyToClipboard(getActivity(), getItem(position).getName());
            return true;
        }
    }

}
