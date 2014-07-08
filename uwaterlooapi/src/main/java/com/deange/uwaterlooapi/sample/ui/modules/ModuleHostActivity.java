package com.deange.uwaterlooapi.sample.ui.modules;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.deange.uwaterlooapi.api.UWaterlooApi;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.ui.modules.base.BaseModuleFragment;

public class ModuleHostActivity extends FragmentActivity {

    private static final String TAG = "module_fragment";
    private static final String ARG_FRAGMENT_CLASS = "fragment_class";

    private final UWaterlooApi mApi = new UWaterlooApi("YOUR_API_KEY_HERE");

    public static <T extends BaseModuleFragment> Intent getStartIntent(final Context context,
                                                                       final T fragment) {
        return getStartIntent(context, fragment, new Bundle());
    }

    public static <T extends BaseModuleFragment> Intent getStartIntent(final Context context,
                                        final T fragment,
                                        final Bundle args) {
        final Intent intent = new Intent(context, ModuleHostActivity.class);
        final Bundle fragmentArgs = fragment.getFragmentInfo();

        intent.putExtra(ARG_FRAGMENT_CLASS, fragment.getClass().getCanonicalName());
        if (args != null) {
            intent.putExtras(args);
        }
        if (fragmentArgs != null) {
            intent.putExtras(fragmentArgs);
        }

        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_module_host);
        setupActionBar();

        final Fragment childFragment = Fragment.instantiate(
                this, getIntent().getStringExtra(ARG_FRAGMENT_CLASS));
        childFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, childFragment, TAG)
                .commit();
    }

    private void setupActionBar() {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public UWaterlooApi getApi() {
        return mApi;
    }

}
