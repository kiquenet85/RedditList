package com.app.ndiazgranados.catalog.di.module;

import android.app.Application;
import android.content.Context;

import com.app.ndiazgranados.catalog.R;
import com.app.ndiazgranados.catalog.di.scope.ApplicationContext;
import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.settings.Settings;
import com.app.ndiazgranados.catalog.util.FileUtil;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies
 * @author n.diazgranados
 */
@Module
public class ApplicationModule {

    protected final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    Settings settings( @ApplicationContext Context context) {
        return new Gson().fromJson(FileUtil.readFile(context, R.raw.default_config), Settings.class);
    }

    @Provides
    @Singleton
    NetworkManager provideNetworkManger( @ApplicationContext Context context, Settings settings) {
        return new NetworkManager(context, settings);
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus(ThreadEnforcer.ANY);
    }
}
