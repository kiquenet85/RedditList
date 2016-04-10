package com.app.ndiazgranados.currency.di.module;

import com.app.ndiazgranados.currency.data.local.cache.ConverterLocalCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 07/04/2016.
 */
@Module
public class CacheModule {

    @Provides
    @Singleton
    ConverterLocalCache provideInteractorPresenter () {
        return new ConverterLocalCache();
    }
}
