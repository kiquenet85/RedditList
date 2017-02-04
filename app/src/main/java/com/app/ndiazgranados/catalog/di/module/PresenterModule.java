package com.app.ndiazgranados.catalog.di.module;

import com.app.ndiazgranados.catalog.data.local.cache.CatalogLocalCache;
import com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache;
import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractor;
import com.app.ndiazgranados.catalog.ui.presenter.CategoryPresenter;
import com.app.ndiazgranados.catalog.ui.presenter.DetailAppPresenter;
import com.app.ndiazgranados.catalog.ui.presenter.TopAppsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author n.diazgranados
 */
@Module
public class PresenterModule {

    @Provides
    @Singleton
    CategoryPresenter provideCategoryPresenter(FetchCatalogInteractor fetchCatalogInteractor, CategoryLocalCache categoryLocalCache,
            CatalogLocalCache catalogLocalCache, NetworkManager networkManager) {
        CategoryPresenter categoryPresenter = new CategoryPresenter(fetchCatalogInteractor, categoryLocalCache, catalogLocalCache, networkManager);
        return categoryPresenter;
    }

    @Provides
    @Singleton
    TopAppsPresenter provideTopAppsPresenter(CategoryLocalCache categoryLocalCache, CatalogLocalCache catalogLocalCache) {
        TopAppsPresenter topAppsPresenter = new TopAppsPresenter(categoryLocalCache, catalogLocalCache);
        return topAppsPresenter;
    }

    @Provides
    @Singleton
    DetailAppPresenter provideDetailAppPresenter(CategoryLocalCache categoryLocalCache, CatalogLocalCache catalogLocalCache) {
        DetailAppPresenter detailAppPresenter = new DetailAppPresenter(categoryLocalCache, catalogLocalCache);
        return detailAppPresenter;
    }
}
