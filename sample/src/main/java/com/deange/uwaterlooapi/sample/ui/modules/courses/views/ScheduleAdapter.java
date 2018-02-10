package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.deange.uwaterlooapi.model.courses.CourseSchedule;
import com.deange.uwaterlooapi.model.courses.Reserve;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.courses.CourseSpan;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleAdapter
    extends ArrayAdapter<CourseSchedule> {

  private static final Pattern COURSE_PATTERN = Pattern.compile("([A-Z]+ [0-9]{2,}[A-Z]+)");

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
      view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_section_schedule, parent,
                                                       false);
      holder = new ViewHolder(view);
      view.setTag(holder);

      holder.heldWith.setMovementMethod(LinkMovementMethod.getInstance());
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
      final Spannable spannable = Spannable.Factory.getInstance().newSpannable(heldClasses);

      final Matcher matcher = COURSE_PATTERN.matcher(heldClasses);
      while (matcher.find()) {
        final int start = matcher.start();
        final int end = matcher.end();
        final String[] course = matcher.group().split(" ");

        spannable.setSpan(new CourseSpan(course[0], course[1]), start, end, 0);
      }

      holder.heldWith.setVisibility(View.VISIBLE);
      holder.heldWith.setText(spannable);
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
    @BindView(R.id.schedule_section) TextView section;
    @BindView(R.id.schedule_campus) TextView campus;
    @BindView(R.id.schedule_capacity) TextView capacity;
    @BindView(R.id.schedule_reserves) TextView reserves;
    @BindView(R.id.schedule_held_with) TextView heldWith;
    @BindView(R.id.schedule_classes) ListView classesListView;

    public ViewHolder(final View view) {
      ButterKnife.bind(this, view);
    }
  }
}
