package com.app.ndiazgranados.reddit.di.component;

import android.app.Application;
import android.content.Context;

import com.app.ndiazgranados.reddit.RedditApp;
import com.app.ndiazgranados.reddit.data.local.cache.RedditLocalCache;
import com.app.ndiazgranados.reddit.data.local.cache.SubjectLocalCache;
import com.app.ndiazgranados.reddit.di.module.ApplicationModule;
import com.app.ndiazgranados.reddit.di.module.CacheModule;
import com.app.ndiazgranados.reddit.di.module.InteractorModule;
import com.app.ndiazgranados.reddit.di.module.NetworkModule;
import com.app.ndiazgranados.reddit.di.module.PresenterModule;
import com.app.ndiazgranados.reddit.di.scope.ApplicationContext;
import com.app.ndiazgranados.reddit.network.NetworkManager;
import com.app.ndiazgranados.reddit.network.WifiReceiver;
import com.app.ndiazgranados.reddit.settings.Settings;
import com.app.ndiazgranados.reddit.ui.activities.BaseActivity;
import com.app.ndiazgranados.reddit.ui.activities.RedditActivity;
import com.app.ndiazgranados.reddit.ui.fragment.BaseFragment;
import com.app.ndiazgranados.reddit.ui.fragment.SubjectFragment;
import com.app.ndiazgranados.reddit.ui.fragment.DetailSubjectFragment;
import com.app.ndiazgranados.reddit.ui.interactor.FetchRedditListInteractor;
import com.app.ndiazgranados.reddit.ui.interactor.FetchRedditListInteractorImpl;
import com.app.ndiazgranados.reddit.ui.presenter.DetailSubjectPresenter;
import com.app.ndiazgranados.reddit.ui.presenter.SubjectPresenter;
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
    void inject(RedditApp application);

    //Views injection
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);
    void inject(RedditActivity redditActivity);
    void inject(SubjectFragment subjectFragment);
    void inject(DetailSubjectFragment detailSubjectFragment);
    void inject(WifiReceiver wifiReceiver);

    //Network component
    NetworkManager getNetworkManager();
    FetchRedditListInteractorImpl getCatalogApi();
    WifiReceiver getWifiReceiver();
    Gson getDefaultGson();

    //App component
    Application getApplication();
    @ApplicationContext Context getAppContext();
    Bus getEventBus();

    //Presenter Component
    SubjectPresenter getCategoryPresenter();
    DetailSubjectPresenter getDetailAppPresenter();

    //Interactor
    FetchRedditListInteractor getCatalogInteractor();

    //Storage
    RedditLocalCache getRedditLocalCache();
    SubjectLocalCache getSubjectLocalCache();

    //Others
    Settings getSettings();
    /*PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    CompositeSubscription compositeSubscription();*/
}
