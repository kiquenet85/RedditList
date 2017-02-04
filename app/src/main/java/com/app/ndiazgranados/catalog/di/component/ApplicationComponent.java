package com.app.ndiazgranados.catalog.di.component;

import android.app.Application;
import android.content.Context;

import com.app.ndiazgranados.catalog.CatalogApp;
import com.app.ndiazgranados.catalog.data.local.cache.CatalogLocalCache;
import com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache;
import com.app.ndiazgranados.catalog.di.module.ApplicationModule;
import com.app.ndiazgranados.catalog.di.module.CacheModule;
import com.app.ndiazgranados.catalog.di.module.InteractorModule;
import com.app.ndiazgranados.catalog.di.module.NetworkModule;
import com.app.ndiazgranados.catalog.di.module.PresenterModule;
import com.app.ndiazgranados.catalog.di.scope.ApplicationContext;
import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.network.WifiReceiver;
import com.app.ndiazgranados.catalog.settings.Settings;
import com.app.ndiazgranados.catalog.ui.activities.BaseActivity;
import com.app.ndiazgranados.catalog.ui.activities.TopAppsActivity;
import com.app.ndiazgranados.catalog.ui.fragment.BaseFragment;
import com.app.ndiazgranados.catalog.ui.fragment.CategoryFragment;
import com.app.ndiazgranados.catalog.ui.fragment.DetailAppFragment;
import com.app.ndiazgranados.catalog.ui.fragment.TopAppsFragment;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractor;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractorImpl;
import com.app.ndiazgranados.catalog.ui.presenter.CategoryPresenter;
import com.app.ndiazgranados.catalog.ui.presenter.DetailAppPresenter;
import com.app.ndiazgranados.catalog.ui.presenter.TopAppsPresenter;
import com.google.gson.Gson;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author n.diazgranados
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, PresenterModule.class,
        InteractorModule.class, CacheModule.class})
public interface ApplicationComponent {

    //App
    void inject(CatalogApp application);

    //Views injection
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);
    void inject(TopAppsActivity topAppsActivity);
    void inject(CategoryFragment categoryFragment);
    void inject(TopAppsFragment topAppsFragment);
    void inject(DetailAppFragment detailAppFragment);
    void inject(WifiReceiver wifiReceiver);

    //Network component
    NetworkManager getNetworkManager();
    FetchCatalogInteractorImpl getCatalogApi();
    WifiReceiver getWifiReceiver();
    Gson getDefaultGson();

    //App component
    Application getApplication();
    @ApplicationContext Context getAppContext();
    Bus getEventBus();

    //Presenter Component
    CategoryPresenter getCategoryPresenter();
    TopAppsPresenter getTopAppsPresenter();
    DetailAppPresenter getDetailAppPresenter();

    //Interactor
    FetchCatalogInteractor getCatalogInteractor();

    //Storage
    CatalogLocalCache getCatalogLocalCache();
    CategoryLocalCache getCategoryLocalCache();

    //Others
    Settings getSettings();
    /*PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    CompositeSubscription compositeSubscription();*/
}
