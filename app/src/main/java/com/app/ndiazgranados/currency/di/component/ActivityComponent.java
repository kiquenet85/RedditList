package com.app.ndiazgranados.currency.di.component;

import com.app.ndiazgranados.currency.di.module.ActivityModule;
import com.app.ndiazgranados.currency.di.module.CacheModule;
import com.app.ndiazgranados.currency.di.scope.PerActivity;
import com.app.ndiazgranados.currency.ui.activities.BaseActivity;
import com.app.ndiazgranados.currency.ui.activities.ConverterActivity;
import com.app.ndiazgranados.currency.ui.fragment.BaseFragment;
import com.app.ndiazgranados.currency.ui.fragment.ConverterFragment;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 * Created by user on 07/04/2016.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CacheModule.class})
public interface ActivityComponent {

    //Views
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);
    void inject(ConverterActivity converterActivity);
    void inject(ConverterFragment converterFragment);
}
