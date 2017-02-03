package com.app.ndiazgranados.catalog;

import android.app.Application;
import android.util.Log;

import com.app.ndiazgranados.catalog.di.component.ApplicationComponent;
import com.app.ndiazgranados.catalog.di.component.DaggerApplicationComponent;
import com.app.ndiazgranados.catalog.di.module.ApplicationModule;
import com.app.ndiazgranados.catalog.di.module.CacheModule;
import com.app.ndiazgranados.catalog.di.module.NetworkModule;

/**
 * @author n.diazgranados
 */
public class CatalogApp extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(this.getClass().getSimpleName(),"Initializing App");
    }

    /**
     * To get ApplicationComponent
     * @return ApplicationComponent
     * */
    public ApplicationComponent getAppComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .networkModule(new NetworkModule())
                    .cacheModule (new CacheModule())
                    .build();
        }
        return applicationComponent;
    }

    /**
     * Need to replace the component with a test specific one
     * @param applicationComponent
     * */
    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

}
