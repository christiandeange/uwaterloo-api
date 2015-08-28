package com.deange.uwaterlooapi.sample.common;

import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class ContainsFilter
        extends Filter {

    private final ArrayAdapter mAdapter;
    private List<String> mOriginalValues;

    public ContainsFilter(final ArrayAdapter adapter, final List<String> values) {
        mAdapter = adapter;
        mOriginalValues = values;
    }

    @Override
    protected FilterResults performFiltering(final CharSequence prefix) {

        final FilterResults results = new FilterResults();

        if (prefix == null || prefix.length() == 0) {
            // Nothing typed, return all elements
            final ArrayList<String> list = new ArrayList<>(mOriginalValues);
            results.values = list;
            results.count = list.size();

        } else {

            final String prefixString = prefix.toString().toLowerCase();

            final ArrayList<String> startsWithValues = new ArrayList<>();
            final ArrayList<String> containsValues = new ArrayList<>();

            for (final String value : mOriginalValues) {
                final String valueText = value.toLowerCase();

                if (valueText.startsWith(prefixString)) {
                    // Add items that start with the text entered.
                    // This takes precedent over items that simply contain the string
                    startsWithValues.add(value);

                } else if (valueText.contains(prefixString)) {
                    // Add items that contain the text entered
                    containsValues.add(value);
                }
            }

            startsWithValues.addAll(containsValues);

            results.values = startsWithValues;
            results.count = startsWithValues.size();
        }

        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(
            final CharSequence constraint,
            final FilterResults results) {

        final List<String> list = (List<String>) results.values;
        mAdapter.clear();
        mAdapter.addAll(list);

        if (results.count > 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyDataSetInvalidated();
        }
    }

}
