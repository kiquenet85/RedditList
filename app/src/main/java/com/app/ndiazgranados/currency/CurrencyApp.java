package com.app.ndiazgranados.currency;

import android.app.Application;
import android.util.Log;

import com.app.ndiazgranados.currency.di.component.ApplicationComponent;
import com.app.ndiazgranados.currency.di.component.DaggerApplicationComponent;
import com.app.ndiazgranados.currency.di.module.ApplicationModule;
import com.app.ndiazgranados.currency.di.module.NetworkModule;

/**
 * Created by user on 07/04/2016.
 */
public class CurrencyApp extends Application {

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
