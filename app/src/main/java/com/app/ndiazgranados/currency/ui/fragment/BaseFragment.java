package com.app.ndiazgranados.currency.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.app.ndiazgranados.currency.CurrencyApp;
import com.app.ndiazgranados.currency.di.component.ActivityComponent;
import com.app.ndiazgranados.currency.di.component.ApplicationComponent;
import com.app.ndiazgranados.currency.ui.activities.BaseActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by user on 07/04/2016.
 */
public class BaseFragment extends Fragment {

    protected ActivityComponent activityComponent;
    protected ApplicationComponent appComponent;
    @Inject
    protected Bus eventBus;

    public BaseFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = ((BaseActivity) getActivity()).getActivityComponent();
        appComponent = ((CurrencyApp) getActivity().getApplication()).getAppComponent();
        eventBus = appComponent.getEventBus();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onResume() {
        super.onResume();
        appComponent.getEventBus().register(this);
    }

    public void onPause() {
        super.onPause();
        appComponent.getEventBus().unregister(this);
    }
}
