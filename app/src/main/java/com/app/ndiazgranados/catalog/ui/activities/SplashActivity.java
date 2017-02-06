package com.app.ndiazgranados.catalog.ui.activities;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.app.ndiazgranados.catalog.R;
import com.app.ndiazgranados.catalog.data.local.cache.CatalogLocalCache;
import com.app.ndiazgranados.catalog.model.web.Catalog;
import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.settings.Settings;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractor;
import com.squareup.otto.Subscribe;

/**
 * @author n.diazgranados
 */
public class SplashActivity extends BaseActivity {

    private FetchCatalogInteractor fetchCatalogInteractor;
    private Settings settings;
    private CatalogLocalCache catalogLocalCache;
    private NetworkManager networkManager;
    private Catalog catalog;
    private ImageView imageView;
    private Animation animation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchCatalogInteractor = applicationComponent.getCatalogInteractor();
        catalogLocalCache = applicationComponent.getCatalogLocalCache();
        networkManager = applicationComponent.getNetworkManager();
        settings = applicationComponent.getSettings();

        imageView = (ImageView) findViewById(R.id.splash_image);
        animation = AnimationUtils.loadAnimation(currentContext, R.anim.pull_up_from_bottom);
    }

    @Override
    public void onResume() {
        super.onResume();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (networkManager.isOnline()) {
                    fetchCatalogInteractor.setLimitNumberOftopApps(settings.getDefaultCathegoryAppsLimit());
                    fetchCatalogInteractor.execute();
                } else {
                    startActivity(TopAppsActivity.createIntent(currentContext, null));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animation);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (catalog != null) {
            catalogLocalCache.saveToCache(catalog);
        }
    }

    @Subscribe
    public void onLoadCatalog(FetchCatalogInteractor.FetchCatalogEvent event) {
        if (event.isSuccessful()) {
            catalog = event.getResponse().body();
            catalogLocalCache.saveToCache(catalog);
        }
        startActivity(TopAppsActivity.createIntent(currentContext, (event.isSuccessful()) ? event.getResponse().raw().toString() : null));
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.splash_activity;
    }
}
