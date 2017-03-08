package com.app.ndiazgranados.reddit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.app.ndiazgranados.reddit.RedditApp;
import com.app.ndiazgranados.reddit.di.component.ActivityComponent;
import com.app.ndiazgranados.reddit.di.component.ApplicationComponent;
import com.app.ndiazgranados.reddit.ui.activities.BaseActivity;
import com.squareup.otto.Bus;

/**
 * @author n.diazgranados
 */
public class BaseFragment extends Fragment {

    protected ActivityComponent activityComponent;
    protected ApplicationComponent appComponent;
    protected Bus eventBus;

    public BaseFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent = ((RedditApp) getActivity().getApplication()).getAppComponent();
        eventBus = appComponent.getEventBus();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityComponent = ((BaseActivity) getActivity()).getActivityComponent();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onResume() {
        eventBus.register(this);
        super.onResume();
    }

    public void onPause() {
        eventBus.unregister(this);
        super.onPause();
    }
}
