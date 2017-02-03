package com.app.ndiazgranados.catalog.di.module;

import com.app.ndiazgranados.catalog.data.local.cache.CatalogLocalCache;
import com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractor;
import com.app.ndiazgranados.catalog.ui.presenter.CategoryPresenter;
import com.squareup.otto.Bus;

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
            CatalogLocalCache catalogLocalCache, Bus eventBus) {
        CategoryPresenter categoryPresenter = new CategoryPresenter(fetchCatalogInteractor, categoryLocalCache, catalogLocalCache, eventBus);
        return categoryPresenter;
    }
}
