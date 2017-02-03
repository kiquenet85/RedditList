package com.app.ndiazgranados.catalog.di.component;

import android.app.Activity;
import android.content.Context;

import com.app.ndiazgranados.catalog.di.module.Activity.ActivityModule;
import com.app.ndiazgranados.catalog.di.scope.PerActivity;
import com.app.ndiazgranados.catalog.ui.activities.BaseActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 * @author n.diazgranados
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    //Activity Component
    @PerActivity
    Context getContext();

    @PerActivity
    Activity getActivity();

    //Views injection
    void inject(BaseActivity baseActivity);
}
