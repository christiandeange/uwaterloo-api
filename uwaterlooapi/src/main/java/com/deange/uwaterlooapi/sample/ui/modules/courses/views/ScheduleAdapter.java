package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.model.courses.Reserve;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScheduleAdapter extends ArrayAdapter<CourseSchedule> {

    public ScheduleAdapter(final Context context, final List<CourseSchedule> schedules) {
        super(context, 0, schedules);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View view;
        final ViewHolder holder;

        if (convertView != null) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_section_schedule, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        final CourseSchedule schedule = getItem(position);
        holder.section.setText(schedule.getSection());
        holder.campus.setText(schedule.getCampus());

        final String capacity = getContext().getString(
                R.string.course_capacity, schedule.getEnrollmentTotal(), schedule.getEnrollmentCapacity());
        holder.capacity.setText(capacity);

        final List<Reserve> reserves = schedule.getReserves();
        final List<String> reserveStrings = new ArrayList<>();
        for (final Reserve reserve : reserves) {
            final String reserveString = getContext().getString(
                    R.string.course_reserve,
                    reserve.getEnrollmentTotal(),
                    reserve.getEnrollmentCapacity(),
                    reserve.getReserveGroup().trim());

            reserveStrings.add(reserveString);
        }

        ViewUtils.setText(holder.reserves, Joiner.on('\n').join(reserveStrings));

        final List<String> heldWith = schedule.getHeldWith();
        if (heldWith != null && !heldWith.isEmpty()) {
            final String and = getContext().getString(R.string.and);
            final String heldClasses = getContext().getString(
                    R.string.course_held_with, Joiner.on('\n').conjunct(and).join(heldWith));

            holder.heldWith.setVisibility(View.VISIBLE);
            holder.heldWith.setText(heldClasses);
        } else {
            holder.heldWith.setVisibility(View.GONE);
        }

        holder.classesListView.setAdapter(new ClassAdapter(getContext(), schedule.getClasses()));

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

    static class ViewHolder {
        @Bind(R.id.schedule_section) TextView section;
        @Bind(R.id.schedule_campus) TextView campus;
        @Bind(R.id.schedule_capacity) TextView capacity;
        @Bind(R.id.schedule_reserves) TextView reserves;
        @Bind(R.id.schedule_held_with) TextView heldWith;
        @Bind(R.id.schedule_classes) ListView classesListView;

        public ViewHolder(final View view) {
            ButterKnife.bind(this, view);
        }
    }
}
