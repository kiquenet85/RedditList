package com.app.ndiazgranados.reddit.di.module;

import com.app.ndiazgranados.reddit.network.WifiReceiver;
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
    WifiReceiver provideWifiReceiver (Bus eventBus) {
        return new WifiReceiver(eventBus);
    }
}
