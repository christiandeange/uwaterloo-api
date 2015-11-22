package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.courses.Class;
import com.deange.uwaterlooapi.model.courses.ClassDate;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClassAdapter extends ArrayAdapter<Class> {

    public ClassAdapter(final Context context, final List<Class> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View view;
        final ViewHolder holder;

        if (convertView != null) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_section_class, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        final Class courseClass = getItem(position);

        // Location
        String location = "";
        if (!TextUtils.isEmpty(courseClass.getBuilding())) {
            location += courseClass.getBuilding() + " ";
        }
        if (!TextUtils.isEmpty(courseClass.getRoom())) {
            location += courseClass.getRoom() + " ";
        }
        location = location.trim();
        ViewUtils.setText(holder.location, location);

        // List of instructors
        final List<String> instructorsList = new ArrayList<>();
        for (final String instructor : courseClass.getInstructors()) {
            instructorsList.add(sanitizeInstructorName(instructor));
        }
        final String and = getContext().getString(R.string.and);
        final String instructors = Joiner.on('\n').conjunct(and).join(instructorsList);
        holder.instructors.setText(instructors);

        // Date & Time
        final ClassDate classDate = courseClass.getDate();
        ViewUtils.setText(holder.date, classDate.getWeekdays());

        String start = classDate.getStartTime();
        String end = classDate.getEndTime();

        String time = null;
        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            if (!DateFormat.is24HourFormat(getContext())) {
                start = Location.convert24To12(start);
                end = Location.convert24To12(end);
            }

            start = Location.sanitize(start);
            end = Location.sanitize(end);

            time = start + " – " + end;
        }

        ViewUtils.setText(holder.time, time);

        return view;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(final int position) {
        return false;
    }

    private String sanitizeInstructorName(final String input) {
        final String output;

        final char delimiter = ',';
        final int firstIndex = input.indexOf(delimiter);
        if (firstIndex >= 0 && firstIndex == input.lastIndexOf(delimiter)) {
            // Only one comma in the entire string
            final String[] parts = input.split(String.valueOf(delimiter));
            output = (parts[1] + " " + parts[0]).trim();

        } else {
            output = input.trim();
        }

        return output;
    }

    static class ViewHolder {
        @Bind(R.id.class_location) TextView location;
        @Bind(R.id.class_instructors) TextView instructors;
        @Bind(R.id.class_date) TextView date;
        @Bind(R.id.class_time) TextView time;

        public ViewHolder(final View view) {
            ButterKnife.bind(this, view);
        }
    }
}
