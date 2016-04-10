package com.app.ndiazgranados.currency.di.module;

import android.app.Activity;
import android.content.Context;

import com.app.ndiazgranados.currency.di.scope.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 07/04/2016.
 */
@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }
}
