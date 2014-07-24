package com.deange.uwaterlooapi.sample.ui.modules;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.FragmentInfo;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;


public class ModuleHostActivity extends FragmentActivity
        implements FragmentManager.OnBackStackChangedListener {

    private static final String TAG = "module_fragment";
    private static final String ARG_FRAGMENT_CLASS = "fragment_class";

    private final UWaterlooApi mApi = new UWaterlooApi("YOUR_API_KEY_HERE");
    private BaseModuleFragment mChildFragment;
    private FragmentInfo mInfo;

    public static <T extends BaseModuleFragment> Intent getStartIntent(final Context context,
                                                                       final Class<T> fragment) {
        return getStartIntent(context, fragment, new Bundle());
    }

    public static <T extends BaseModuleFragment> Intent getStartIntent(final Context context,
                                        final Class<T> fragment,
                                        final Bundle args) {
        final Intent intent = new Intent(context, ModuleHostActivity.class);

        intent.putExtra(ARG_FRAGMENT_CLASS, fragment.getCanonicalName());
        if (args != null) {
            intent.putExtras(args);
        }

        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_module_host);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        final String fragmentName = getIntent().getStringExtra(ARG_FRAGMENT_CLASS);
        showFragment((BaseModuleFragment) Fragment.instantiate(this, fragmentName), false,
                getIntent().getExtras());
    }

    public void showFragment(final BaseModuleFragment fragment, final boolean addToBackStack,
                             final Bundle arguments) {
        mChildFragment = fragment;
        mChildFragment.setArguments(arguments);
        mInfo = mChildFragment.getFragmentInfo(this);
        refreshActionBar();

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(mChildFragment.getClass().getCanonicalName());
        }
        transaction.replace(R.id.content, mChildFragment, TAG).commit();
    }

    public void refreshActionBar() {
        setupActionBar(mInfo);
    }

    private void setupActionBar(final FragmentInfo info) {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (info == null) {
            return;
        }

        actionBar.setTitle(info.getActionBarTitle());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public UWaterlooApi getApi() {
        return mApi;
    }

    @Override
    public void onBackStackChanged() {
        mChildFragment =
                (BaseModuleFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        mInfo = mChildFragment.getFragmentInfo(this);
        refreshActionBar();
    }
}
