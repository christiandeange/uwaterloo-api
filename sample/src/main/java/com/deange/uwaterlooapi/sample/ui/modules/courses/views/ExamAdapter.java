package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deange.uwaterlooapi.model.courses.ExamSection;
import com.deange.uwaterlooapi.model.foodservices.Location;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.Joiner;
import com.deange.uwaterlooapi.sample.utils.PlatformUtils;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;
import com.deange.uwaterlooapi.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamAdapter
        extends ArrayAdapter<ExamSection>
        implements
        View.OnLongClickListener {

    public ExamAdapter(final Context context, final List<ExamSection> objects) {
        super(context, 0, objects);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(final int position) {
        return false;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View view;
        final ViewHolder holder;

        if (convertView != null) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_exam_section, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        view.setOnLongClickListener(this);

        final ExamSection section = getItem(position);

        // Title
        final String sectionName = getContext().getString(R.string.exam_section, getItem(position).getSection());
        holder.section.setText(sectionName);

        // Date & time
        final Date date = section.getDate();
        if (date != null) {
            final String formattedDay = DateUtils.formatDate(getContext(), section.getDate(), DateUtils.DATE_LENGTH_LONG);
            final String fullDate = section.getDay() + ", " + formattedDay;
            holder.date.setText(fullDate);
        } else {
            holder.date.setText(null);
        }

        String start = section.getStartTime();
        String end = section.getEndTime();

        String time = null;
        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            if (DateFormat.is24HourFormat(getContext())) {
                start = Location.convert12To24(start);
                end = Location.convert12To24(end);
            }

            start = Location.sanitize(start);
            end = Location.sanitize(end);

            time = start + " – " + end;
        }

        ViewUtils.setText(holder.time, time);

        // Room & building
        final String location = section.getLocation();
        final Matcher numericRooms = Pattern.compile(" (\\d,?)+").matcher(location);
        final Matcher anyRooms = Pattern.compile(" (\\w-\\w)+").matcher(location);

        final String title;
        final String subtitle;

        if (location.contains(",")) {
            title = null;
            subtitle = Joiner.on('\n').join(location.split(",\\s*"));

        } else if (numericRooms.find()) {
            final String rooms = numericRooms.group().trim();
            title = location.replace(rooms, "").trim();
            subtitle = Joiner.on(',').join(getRanges(rooms));

        } else if (anyRooms.find()) {
            final String rooms = anyRooms.group().trim();
            title = location.replace(rooms, "").trim();
            subtitle = rooms;

        } else {
            title = null;
            subtitle = location;
        }

        ViewUtils.setText(holder.building, title);
        ViewUtils.setText(holder.rooms, subtitle);

        ViewUtils.setText(holder.notes, section.getNotes());

        return view;
    }

    private static List<String> getRanges(final String input) {
        // Adapted from http://stackoverflow.com/a/2270987
        final List<String> result = new ArrayList<>();
        final String[] strArray = input.split(",");
        final int[] array = new int[strArray.length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = Integer.parseInt(strArray[i]);
        }

        int start, end;
        for (int i = 0; i < array.length; i++) {
            start = array[i];
            end = start;
            while ((i < array.length - 1) && (array[i + 1] - array[i] == 1)) {
                end = array[i + 1]; // increment the index if the numbers are sequential
                i++;
            }
            result.add(start == end ? String.valueOf(start) : start + "–" + end);
        }

        return result;
    }

    @Override
    public boolean onLongClick(final View v) {

        View parent = v;
        while (!(parent.getTag() instanceof ViewHolder)) {
            parent = (View) parent.getParent();
            if (parent == null) {
                // Well something went wrong here...
                return false;
            }
        }

        final ViewHolder holder = (ViewHolder) parent.getTag();
        final String info =
                holder.section.getText() + " – "
                + holder.date.getText() + " (" + holder.time.getText() + ") @ "
                + holder.building.getText() + " " + holder.rooms.getText().toString().replace("\n", ", ");

        PlatformUtils.copyToClipboard(getContext(), info);

        return true;
    }

    static final class ViewHolder {
        @BindView(R.id.selectable) View selectable;
        @BindView(R.id.exam_section) TextView section;
        @BindView(R.id.exam_date) TextView date;
        @BindView(R.id.exam_time) TextView time;
        @BindView(R.id.exam_building) TextView building;
        @BindView(R.id.exam_rooms) TextView rooms;
        @BindView(R.id.exam_notes) TextView notes;

        private ViewHolder(final View view) {
            ButterKnife.bind(this, view);
        }
    }
}
