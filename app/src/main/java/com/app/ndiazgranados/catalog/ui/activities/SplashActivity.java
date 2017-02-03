package com.app.ndiazgranados.catalog.ui.activities;

import android.os.Bundle;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchCatalogInteractor = applicationComponent.getCatalogInteractor();
        catalogLocalCache = applicationComponent.getCatalogLocalCache();
        networkManager = applicationComponent.getNetworkManager();
        settings = applicationComponent.getSettings();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!networkManager.isOnline()) {
            startActivity(TopAppsActivity.createIntent(currentContext, null));
            finish();
        } else if (!isFinishing() && catalog == null) {
            fetchCatalogInteractor.setLimitNumberOftopApps(settings.getDefaultCathegoryAppsLimit());
            fetchCatalogInteractor.execute();
        }
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
