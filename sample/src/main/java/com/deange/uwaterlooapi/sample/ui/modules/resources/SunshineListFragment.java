package com.deange.uwaterlooapi.sample.ui.modules.resources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.deange.uwaterlooapi.UWaterlooApi;
import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Responses;
import com.deange.uwaterlooapi.model.resources.Sunshiner;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import retrofit2.Call;

@ModuleFragment(
    path = "/resources/sunshinelist",
    layout = R.layout.module_resources_sunshine
)
public class SunshineListFragment
    extends BaseListModuleFragment<Responses.Sunshine, Sunshiner>
    implements
    AdapterView.OnItemSelectedListener {

  // Correspond to @array/sunshine_list_sort
  private static final int SORT_SALARY_HIGH_TO_LOW = 0;
  private static final int SORT_SALARY_LOW_TO_HIGH = 1;
  private static final int SORT_FIRST_NAME = 2;
  private static final int SORT_LAST_NAME = 3;

  @BindView(R.id.fragment_sort_spinner) Spinner mSortSpinner;

  private final List<Sunshiner> mResponse = new ArrayList<>();
  private int mHighestSalary;

  private final Comparator<Sunshiner> mComparator = (o1, o2) -> {
    switch (mSortSpinner.getSelectedItemPosition()) {
      default:
      case SORT_SALARY_HIGH_TO_LOW:
        return -o1.getSalary().compareTo(o2.getSalary());
      case SORT_SALARY_LOW_TO_HIGH:
        return o1.getSalary().compareTo(o2.getSalary());
      case SORT_FIRST_NAME:
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getGivenName(), o2.getGivenName());
      case SORT_LAST_NAME:
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getSurname(), o2.getSurname());
    }
  };

  @Override
  public String getToolbarTitle() {
    return getString(R.string.title_resources_sunshine);
  }

  @Override
  protected View getContentView(final LayoutInflater inflater, final ViewGroup parent) {
    final View view = super.getContentView(inflater, parent);
    ButterKnife.bind(this, view);

    mSortSpinner.setOnItemSelectedListener(this);

    return view;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_resources_sunshinelist;
  }

  @Override
  public float getToolbarElevationPx() {
    return 0;
  }

  @Override
  public ModuleAdapter getAdapter() {
    return new SunshineAdapter(getActivity());
  }

  @Override
  public Call<Responses.Sunshine> onLoadData(final UWaterlooApi api) {
    return api.resources().getSunshineList();
  }

  @Override
  public void onBindData(final Metadata metadata, final List<Sunshiner> data) {
    mResponse.clear();
    mResponse.addAll(data);

    Collections.sort(mResponse, mComparator);

    final Sunshiner highestSalary = Collections.max(mResponse);
    if (highestSalary != null) {
      mHighestSalary = highestSalary.getSalary().intValue();
    }

    notifyDataSetChanged();
  }

  @Override
  public String getContentType() {
    return ModuleType.SUNSHINE;
  }

  @Override
  public void onItemSelected(
      final AdapterView<?> parent,
      final View view,
      final int position,
      final long id) {
    Collections.sort(mResponse, mComparator);
    notifyDataSetChanged();
  }

  @Override
  public void onNothingSelected(final AdapterView<?> parent) {
    // Nothing to do here
  }

  private class SunshineAdapter
      extends ModuleAdapter {

    public SunshineAdapter(final Context context) {
      super(context);
    }

    @Override
    public View newView(final Context context, final int position, final ViewGroup parent) {
      return LayoutInflater.from(context).inflate(R.layout.list_item_resources_sunshiner, parent,
                                                  false);
    }

    @Override
    public void bindView(final Context context, final int position, final View view) {
      final Sunshiner sunshiner = getItem(position);

      final String name = sunshiner.getGivenName() + " " + sunshiner.getSurname();
      final BigDecimal salary = sunshiner.getSalary();
      final String salaryFormatted = NumberFormat.getCurrencyInstance().format(salary);

      final int max = mHighestSalary;
      final int progress = salary.intValue();
      final float fraction = progress / (float) max;

      final TextView nameView = (TextView) view.findViewById(R.id.sunshiner_name);
      final TextView salaryView = (TextView) view.findViewById(R.id.sunshiner_salary);
      final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.sunshiner_progress_bar);

      nameView.setText(name);
      progressBar.setMax(max);
      progressBar.setProgress(progress);

      progressBar.post(() -> {
        final int fullWidth = progressBar.getMeasuredWidth();
        final float textWidth = salaryView.getPaint().measureText(salaryFormatted);
        final float leftPadding = Math.max(Math.min(fullWidth * fraction, fullWidth) - textWidth,
                                           0);

        salaryView.setText(salaryFormatted);
        salaryView.setTranslationX(leftPadding);
      });
    }

    @Override
    public int getCount() {
      return mResponse.size();
    }

    @Override
    public Sunshiner getItem(final int position) {
      return mResponse.get(position);
    }
  }

}
