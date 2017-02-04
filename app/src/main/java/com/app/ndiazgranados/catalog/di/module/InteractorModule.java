package com.app.ndiazgranados.catalog.di.module;

import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.settings.Settings;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractor;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractorImpl;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author n.diazgranados
 */
@Module
public class InteractorModule {

    @Provides
    @Singleton
    FetchCatalogInteractor provideCatalogApi(NetworkManager networkManager, Settings settings, Bus eventBus) {
        return new FetchCatalogInteractorImpl(networkManager, settings, eventBus);
    }
}
