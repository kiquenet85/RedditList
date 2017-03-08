package com.app.ndiazgranados.reddit.di.component;

import android.app.Activity;
import android.content.Context;

import com.app.ndiazgranados.reddit.di.module.Activity.ActivityModule;
import com.app.ndiazgranados.reddit.di.scope.PerActivity;
import com.app.ndiazgranados.reddit.ui.activities.BaseActivity;

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
