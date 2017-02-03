package com.app.ndiazgranados.catalog.di.module;

import com.app.ndiazgranados.catalog.data.local.cache.CatalogLocalCache;
import com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author n.diazgranados
 */
@Module
public class CacheModule {

    @Provides
    @Singleton
    CatalogLocalCache provideCatalogCache () {
        return new CatalogLocalCache();
    }

    @Provides
    @Singleton
    CategoryLocalCache provideCategoryCache () {
        return new CategoryLocalCache();
    }
}
