package com.app.ndiazgranados.currency.di.module;

import com.app.ndiazgranados.currency.data.local.ConverterApiInterfaceLocalImpl;
import com.app.ndiazgranados.currency.data.remote.ConverterApiInterfaceRemImpl;
import com.app.ndiazgranados.currency.network.NetworkManager;
import com.app.ndiazgranados.currency.network.WifiReceiver;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 07/04/2016.
 */
@Module
public class NetworkModule {

    public NetworkModule() {
    }

    @Provides
    @Singleton
    ConverterApiInterfaceRemImpl provideConverterRemApi(NetworkManager networkManager, Bus eventBus) {
        return new ConverterApiInterfaceRemImpl(networkManager, eventBus);
    }

    @Provides
    @Singleton
    ConverterApiInterfaceLocalImpl provideConverterLocApi(Bus eventBus) {
        return new ConverterApiInterfaceLocalImpl();
    }

    @Provides
    @Singleton
    WifiReceiver provideWifiReceiver (Bus eventBus) {
        return new WifiReceiver(eventBus);
    }
}
