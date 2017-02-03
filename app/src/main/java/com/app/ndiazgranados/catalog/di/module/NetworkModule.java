package com.app.ndiazgranados.catalog.di.module;

import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.network.WifiReceiver;
import com.app.ndiazgranados.catalog.settings.Settings;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractor;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractorImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author n.diazgranados
 */
@Module
public class NetworkModule {

    public NetworkModule() {
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    FetchCatalogInteractor provideCatalogApi(NetworkManager networkManager, Settings settings, Bus eventBus) {
        return new FetchCatalogInteractorImpl(networkManager, settings, eventBus);
    }

    @Provides
    @Singleton
    WifiReceiver provideWifiReceiver (Bus eventBus) {
        return new WifiReceiver(eventBus);
    }
}
