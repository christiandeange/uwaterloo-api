package com.deange.uwaterlooapi.sample.ui.modules.foodservices;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.deange.uwaterlooapi.model.foodservices.Meal;
import com.deange.uwaterlooapi.model.foodservices.Menu;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.view.WrapContentListView;
import com.deange.uwaterlooapi.sample.utils.PlatformUtils;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;
import java.util.List;
import org.joda.time.LocalDate;

public class MenuDayAdapter
    extends PagerAdapter {

  private final List<Menu> mMenus;

  public MenuDayAdapter(final List<Menu> menus) {
    mMenus = menus;
  }

  public Menu getItem(final int position) {
    return mMenus.get(position);
  }

  @Override
  public int getCount() {
    return mMenus.size();
  }

  @Override
  public CharSequence getPageTitle(final int position) {
    return getItem(position).getDayOfWeek();
  }

  @Override
  public boolean isViewFromObject(final View view, final Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(final ViewGroup container, final int position) {
    final ContentView view = new ContentView(container.getContext());

    container.addView(view);
    view.bind(getItem(position));

    return view;
  }

  @Override
  public void destroyItem(final ViewGroup container, final int position, final Object object) {
    container.removeView((View) object);
  }

  public static final class ContentView extends FrameLayout {

    @BindView(R.id.menu_title_day) TextView mTitle;
    @BindView(R.id.menu_lunch_container) ViewGroup mLunchContainer;
    @BindView(R.id.menu_dinner_container) ViewGroup mDinnerContainer;
    @BindView(R.id.menu_lunch_items) WrapContentListView mLunchView;
    @BindView(R.id.menu_dinner_items) WrapContentListView mDinnerView;

    public ContentView(final Context context) {
      super(context);
      setLayoutParams(generateDefaultLayoutParams());

      inflate(context, R.layout.view_menu_for_day, this);
      ButterKnife.bind(this);
    }

    public void bind(final Menu menu) {
      mTitle.setText(getResources().getString(
          R.string.foodservices_menu_for_day,
          LocalDate.fromDateFields(menu.getDate()).toString("MMM dd")));

      setItems(mLunchContainer, mLunchView, menu.getMeals().getLunch());
      setItems(mDinnerContainer, mDinnerView, menu.getMeals().getDinner());
    }

    private void setItems(
        final ViewGroup parent,
        final WrapContentListView listView,
        final List<Meal> meals) {

      listView.setAdapter(new MealAdapter(getContext(), meals));
      if (meals == null || meals.isEmpty()) {
        parent.setVisibility(View.GONE);
      } else {
        parent.setVisibility(View.VISIBLE);
      }
    }
  }

  private static final class MealAdapter extends ArrayAdapter<Meal>
      implements View.OnLongClickListener {

    public MealAdapter(final Context context, final List<Meal> meals) {
      super(context, 0, meals);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
      final View view;
      if (convertView != null) {
        view = convertView;
      } else {
        view = LayoutInflater.from(getContext())
                             .inflate(R.layout.simple_two_line_item, parent, false);
      }

      view.setLongClickable(true);
      view.setOnLongClickListener(this);

      final Meal meal = getItem(position);
      ((TextView) view.findViewById(android.R.id.text1)).setText(meal.getName());
      ViewUtils.setText((TextView) view.findViewById(android.R.id.text2), meal.getDietType());

      return view;
    }

    @Override
    public boolean onLongClick(final View v) {
      final TextView view = (TextView) v.findViewById(android.R.id.text1);
      final String text = view.getText().toString();

      PlatformUtils.copyToClipboard(getContext(), text);

      return true;
    }
  }

}
