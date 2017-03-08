package com.app.ndiazgranados.reddit.di.module;

import com.app.ndiazgranados.reddit.network.NetworkManager;
import com.app.ndiazgranados.reddit.settings.Settings;
import com.app.ndiazgranados.reddit.ui.interactor.FetchRedditListInteractor;
import com.app.ndiazgranados.reddit.ui.interactor.FetchRedditListInteractorImpl;
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
    FetchRedditListInteractor provideRedditApi(NetworkManager networkManager, Settings settings, Bus eventBus) {
        return new FetchRedditListInteractorImpl(networkManager, eventBus);
    }
}
