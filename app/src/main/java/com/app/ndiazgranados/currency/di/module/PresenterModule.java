package com.app.ndiazgranados.currency.di.module;

import com.app.ndiazgranados.currency.data.local.ConverterApiInterfaceLocalImpl;
import com.app.ndiazgranados.currency.data.local.cache.ConverterLocalCache;
import com.app.ndiazgranados.currency.data.remote.ConverterApiInterfaceRemImpl;
import com.app.ndiazgranados.currency.network.NetworkManager;
import com.app.ndiazgranados.currency.model.interactor.FetchAllCurrenciesInteractor;
import com.app.ndiazgranados.currency.model.interactor.FetchAllCurrenciesInteractorImpl;
import com.app.ndiazgranados.currency.model.presenter.ConverterPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 09/04/2016.
 */
@Module
public class PresenterModule {

    @Provides
    @Singleton
    ConverterPresenter provideConverterPresenter (FetchAllCurrenciesInteractor fetchAllCurrenciesInteractor, ConverterLocalCache converterLocalCache) {
        return new ConverterPresenter(fetchAllCurrenciesInteractor, converterLocalCache);
    }

    @Provides
    @Singleton
    FetchAllCurrenciesInteractor provideInteractorPresenter (ConverterApiInterfaceRemImpl converterApiInterfaceRem,
                                                             ConverterApiInterfaceLocalImpl converterApiInterfaceLocal,
                                                             NetworkManager networkManager) {
        return new FetchAllCurrenciesInteractorImpl(converterApiInterfaceRem, converterApiInterfaceLocal, networkManager);
    }
}
