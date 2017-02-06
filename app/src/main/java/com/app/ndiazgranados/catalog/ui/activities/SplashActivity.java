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
import com.squareup.picasso.Picasso;

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

        imageView = (ImageView) findViewById(R.id.ssplash_image);
        Picasso.with(currentContext).load(R.mipmap.ic_launcher).into(imageView);

        animation = AnimationUtils.loadAnimation(currentContext, R.anim.slide_in_from_right);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!networkManager.isOnline()) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    startActivity(TopAppsActivity.createIntent(currentContext, null));
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            imageView.startAnimation(animation);
        } else if (!isFinishing() && catalog == null) {
            imageView.startAnimation(animation);
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
        imageView.clearAnimation();
        startActivity(TopAppsActivity.createIntent(currentContext, (event.isSuccessful()) ? event.getResponse().raw().toString() : null));
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.splash_activity;
    }
}
