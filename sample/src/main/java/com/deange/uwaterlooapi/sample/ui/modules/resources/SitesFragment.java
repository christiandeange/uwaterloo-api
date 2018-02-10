package com.deange.uwaterlooapi.sample.ui.modules.resources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.deange.uwaterlooapi.UWaterlooApi;
import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.model.Metadata;
import com.deange.uwaterlooapi.model.common.Responses;
import com.deange.uwaterlooapi.model.resources.Site;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.ModuleAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleIndexedAdapter;
import com.deange.uwaterlooapi.sample.ui.ModuleListItemListener;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleType;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseListModuleFragment;
import com.deange.uwaterlooapi.sample.utils.IntentUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import retrofit2.Call;

@ModuleFragment(
    path = "/resources/sites",
    layout = R.layout.module_resources_sites
)
public class SitesFragment
    extends BaseListModuleFragment<Responses.Sites, Site>
    implements
    ModuleListItemListener {

  private final List<Site> mResponse = new ArrayList<>();
  private final Set<String> mSections = new TreeSet<>();
  private String[] mSectionsArray;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_simple_listview;
  }

  @Override
  public String getToolbarTitle() {
    return getString(R.string.title_resources_sites);
  }

  @Override
  public ModuleAdapter getAdapter() {
    return new SiteAdapter(getActivity(), this);
  }

  @Override
  public Call<Responses.Sites> onLoadData(final UWaterlooApi api) {
    return api.resources().getSites();
  }

  @Override
  public void onBindData(final Metadata metadata, final List<Site> data) {
    mResponse.clear();
    mResponse.addAll(data);

    Collections.sort(mResponse, (lhs, rhs) -> lhs.getName().compareTo(rhs.getName()));

    mSections.clear();
    for (int i = 0; i < mResponse.size(); i++) {
      mSections.add(getFirstCharOf(i));
    }
    mSectionsArray = mSections.toArray(new String[mSections.size()]);

    getListView().setFastScrollEnabled(true);
    getListView().setFastScrollAlwaysVisible(true);

    notifyDataSetChanged();
  }

  @Override
  public String getContentType() {
    return ModuleType.SITES;
  }

  @Override
  public final void onItemClicked(final int position) {
    onSiteClicked(mResponse.get(position));
  }

  public void onSiteClicked(final Site site) {
    IntentUtils.openBrowser(getActivity(), site.getUrl());
  }

  private String getFirstCharOf(final int position) {
    return String.valueOf(mResponse.get(position).getName().charAt(0));
  }

  private class SiteAdapter
      extends ModuleIndexedAdapter<String> {

    public SiteAdapter(final Context context, final ModuleListItemListener listener) {
      super(context, listener);
    }

    @Override
    public View newView(final Context context, final int position, final ViewGroup parent) {
      return LayoutInflater.from(context).inflate(R.layout.simple_two_line_card_item, parent,
                                                  false);
    }

    @Override
    public void bindView(final Context context, final int position, final View view) {
      final Site site = getItem(position);
      ((TextView) view.findViewById(android.R.id.text1)).setText(site.getName());
      ((TextView) view.findViewById(android.R.id.text2)).setText(site.getUnitShortName());
    }

    @Override
    public int getCount() {
      return mResponse.size();
    }

    @Override
    public Site getItem(final int position) {
      return mResponse.get(position);
    }

    @Override
    public String[] getSections() {
      return mSectionsArray;
    }

    @Override
    public String getFirstCharOf(final int position) {
      return SitesFragment.this.getFirstCharOf(position);
    }

  }
}
