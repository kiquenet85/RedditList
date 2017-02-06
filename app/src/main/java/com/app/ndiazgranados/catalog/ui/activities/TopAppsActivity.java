package com.app.ndiazgranados.catalog.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.app.ndiazgranados.catalog.R;
import com.app.ndiazgranados.catalog.network.WifiReceiver;
import com.app.ndiazgranados.catalog.ui.fragment.CategoryFragment;
import com.app.ndiazgranados.catalog.ui.fragment.DetailAppFragment;
import com.app.ndiazgranados.catalog.ui.fragment.TopAppsFragment;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

/**
 * @author n.diazgranados
 */
public class TopAppsActivity extends BaseActivity implements CategoryFragment.CategoryFragmentInteractionListener,
        TopAppsFragment.TopAppsFragmentInteractionListener {

    public static String ACTIVITY_KEY_CATALOG = "ACTIVITY_KEY_CATALOG";
    private Gson gson;

    private CollapsingToolbarLayout collapsingToolbar;

    public static Intent createIntent(Context context, String catalogJSON) {
        Intent intent = new Intent(context, TopAppsActivity.class);
        Bundle args = new Bundle();

        args.putString(ACTIVITY_KEY_CATALOG, catalogJSON);
        intent.putExtras(args);
        return intent;
    }

    //region lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
        gson = applicationComponent.getDefaultGson();

        if (savedInstanceState == null) {
            Bundle arguments = arguments = getIntent().getExtras();

            CategoryFragment categoryFragment = CategoryFragment.newInstance();
            if (arguments != null) {
                categoryFragment.setArguments(arguments);
            }
            addFragment(categoryFragment);
        }
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.toolbar_header_title));

        ImageView header = (ImageView) findViewById(R.id.header);
        Picasso.with(currentContext).load(R.drawable.top_apps_header).into(header);

        collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.colorAccent));
    }
    //endregion

    //region broadcast-events
    @Subscribe
    public void updateConnectionStatus(WifiReceiver.NetworkConnectionChange networkConnectionChange) {
        updateActiveConnectionIcon(networkConnectionChange.isOnline());
        CategoryFragment categoryFragment = (CategoryFragment) getFragmentFromActivity(CategoryFragment.class.getName());
        if (categoryFragment != null) {
            categoryFragment.updateConnectionStatus(networkConnectionChange.isOnline());
        }
    }

    @Override
    public void showSelectedCategory(String nameSelectedCategory) {
        pushFragment(R.id.fragment_container, TopAppsFragment.newInstance(nameSelectedCategory), TopAppsFragment.class.getName(),
                true, new CustomAnimations(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right));
    }

    @Override
    public void showSelectedApp(String nameSelectedApp) {
        pushFragment(R.id.fragment_container, DetailAppFragment.newInstance(nameSelectedApp), TopAppsFragment.class.getName(),
                true, new CustomAnimations(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right));
    }
    //endregion
}
