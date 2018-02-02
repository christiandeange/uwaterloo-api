package com.deange.uwaterlooapi.sample.dagger;

import com.deange.uwaterlooapi.sample.ui.AboutActivity;
import com.deange.uwaterlooapi.sample.ui.ExtrasActivity;
import com.deange.uwaterlooapi.sample.ui.flow.FlowHostActivity;
import com.deange.uwaterlooapi.sample.ui.modules.ApiMethodsFragment;
import com.deange.uwaterlooapi.sample.ui.modules.ModuleHostActivity;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;
import com.deange.uwaterlooapi.sample.ui.modules.home.NearbyLocationsFragment;
import com.deange.uwaterlooapi.sample.ui.modules.watcard.WatcardBalanceFragment;
import com.deange.uwaterlooapi.sample.ui.view.CardView;

public interface AppComponentInjections {

  void inject(ModuleHostActivity target);

  void inject(AboutActivity target);

  void inject(ExtrasActivity target);

  void inject(ApiMethodsFragment target);

  void inject(CardView target);

  void inject(NearbyLocationsFragment target);

  void inject(BaseModuleFragment.Injections target);

  void inject(WatcardBalanceFragment target);
}
