package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.deange.uwaterlooapi.UWaterlooApi;
import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Responses;
import com.deange.uwaterlooapi.model.foodservices.Note;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import com.deange.uwaterlooapi.sample.ui.view.DateSelectorView;
import com.deange.uwaterlooapi.sample.utils.DateUtils;
import com.deange.uwaterlooapi.sample.utils.PlatformUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.LocalDate;
import retrofit2.Call;

@ModuleFragment(
    path = "/foodservices/notes",
    layout = R.layout.module_foodservices_notes
)
public class NotesFragment
    extends BaseListModuleFragment<Responses.Notes, Note>
    implements
    DateSelectorView.OnDateChangedListener {

  @BindView(R.id.fragment_date_selector) DateSelectorView mDateSelectorView;
  @BindView(R.id.fragment_empty_view) View mEmptyView;

  private final List<Note> mResponse = new ArrayList<>();

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_foodservices_notes;
  }

  @Override
  protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
    final View view = super.getContentView(inflater, parent);
    ButterKnife.bind(this, view);

    mDateSelectorView.setOnDateSetListener(this);

    return view;
  }

  @Override
  public String getToolbarTitle() {
    return getString(R.string.title_foodservices_notes);
  }

  @Override
  public float getToolbarElevationPx() {
    return 0;
  }

  @Override
  public ModuleAdapter getAdapter() {
    return new NotesAdapter(getActivity());
  }

  @Override
  public Call<Responses.Notes> onLoadData(final UWaterlooApi api) {
    final LocalDate date = mDateSelectorView.getDate();
    final int year = date.getYear();
    final int week = date.getWeekOfWeekyear();

    return api.foodServices().getNotes(year, week);
  }

  @Override
  protected void onNoDataReturned() {
    mEmptyView.setVisibility(View.VISIBLE);
  }

  @Override
  public void onBindData(final Metadata metadata, final List<Note> data) {
    mResponse.clear();
    mResponse.addAll(data);

    Collections.sort(mResponse, (lhs, rhs) -> lhs.getDate().compareTo(rhs.getDate()));

    mEmptyView.setVisibility(mResponse.isEmpty() ? View.VISIBLE : View.GONE);
    notifyDataSetChanged();
  }

  @Override
  public String getContentType() {
    return ModuleType.NOTES;
  }

  @Override
  public void onDateSet(final int year, final int monthOfYear, final int dayOfMonth) {
    doRefresh();
  }

  private class NotesAdapter
      extends ModuleAdapter
      implements View.OnLongClickListener {

    public NotesAdapter(final Context context) {
      super(context);
    }

    @Override
    public View newView(final Context context, final int position, final ViewGroup parent) {
      return LayoutInflater.from(context)
                           .inflate(R.layout.list_item_foodservices_note, parent, false);
    }

    @Override
    public void bindView(final Context context, final int position, final View view) {
      final Note note = getItem(position);
      ((TextView) view.findViewById(R.id.note_outlet)).setText(note.getOutletName());
      ((TextView) view.findViewById(R.id.note_content)).setText(note.getNote());
      ((TextView) view.findViewById(R.id.note_date))
          .setText(DateUtils.formatDate(note.getDate()));

      final View selectableView = view.findViewById(R.id.selectable);
      selectableView.setTag(position);
      selectableView.setLongClickable(true);
      selectableView.setOnLongClickListener(this);
    }

    @Override
    public int getCount() {
      return mResponse == null ? 0 : mResponse.size();
    }

    @Override
    public Note getItem(final int position) {
      return mResponse == null ? null : mResponse.get(position);
    }

    @Override
    public boolean onLongClick(final View v) {
      final int position = (int) v.getTag();
      PlatformUtils.copyToClipboard(getActivity(), getItem(position).getNote());
      return true;
    }
  }

}
