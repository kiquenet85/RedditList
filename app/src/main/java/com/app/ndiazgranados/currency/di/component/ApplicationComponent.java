package com.app.ndiazgranados.currency.di.component;

import android.app.Application;
import android.content.Context;

import com.app.ndiazgranados.currency.data.local.ConverterApiInterfaceLocalImpl;
import com.app.ndiazgranados.currency.data.local.cache.ConverterLocalCache;
import com.app.ndiazgranados.currency.data.remote.ConverterApiInterfaceRemImpl;
import com.app.ndiazgranados.currency.di.module.ApplicationModule;
import com.app.ndiazgranados.currency.di.module.NetworkModule;
import com.app.ndiazgranados.currency.di.module.PresenterModule;
import com.app.ndiazgranados.currency.di.scope.ApplicationContext;
import com.app.ndiazgranados.currency.network.NetworkManager;
import com.app.ndiazgranados.currency.network.WifiReceiver;
import com.app.ndiazgranados.currency.ui.activities.BaseActivity;
import com.app.ndiazgranados.currency.ui.fragment.ConverterFragment;
import com.app.ndiazgranados.currency.model.interactor.FetchAllCurrenciesInteractor;
import com.app.ndiazgranados.currency.model.presenter.ConverterPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by user on 07/04/2016.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, PresenterModule.class})
public interface ApplicationComponent {

    //Views injection
    void inject(BaseActivity baseActivity);
    void inject(ConverterFragment converterFragment);
    void inject(WifiReceiver wifiReceiver);

    //Network component
    NetworkManager getNetworkManager();
    ConverterApiInterfaceRemImpl getRemConverterApi();
    ConverterApiInterfaceLocalImpl getLocConverterApi();
    WifiReceiver getWifiReceiver();

    //App component
    Application getApplication();
    @ApplicationContext Context getAppContext();
    Bus getEventBus();

    //Presenter Component
    ConverterPresenter getConverterPresenter();
    FetchAllCurrenciesInteractor getFetchAllCurrenciesInteractor();

    //Storage
    ConverterLocalCache getConverterLocalCache();

    //Others
    /*PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    CompositeSubscription compositeSubscription();*/
}
