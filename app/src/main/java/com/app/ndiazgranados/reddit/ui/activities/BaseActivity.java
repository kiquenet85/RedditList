package com.app.ndiazgranados.reddit.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.app.ndiazgranados.reddit.RedditApp;
import com.app.ndiazgranados.reddit.R;
import com.app.ndiazgranados.reddit.di.component.ActivityComponent;
import com.app.ndiazgranados.reddit.di.component.ApplicationComponent;
import com.app.ndiazgranados.reddit.di.component.DaggerActivityComponent;
import com.app.ndiazgranados.reddit.di.module.Activity.ActivityModule;
import com.app.ndiazgranados.reddit.network.NetworkManager;
import com.app.ndiazgranados.reddit.network.WifiReceiver;
import com.squareup.otto.Bus;

import java.io.Serializable;

/**
 * @author n.diazgranados
 */
public class BaseActivity extends AppCompatActivity {

    public static final String WIFI_RECEIVER_ACTION= "android.net.conn.CONNECTIVITY_CHANGE";
    protected ActivityComponent activityComponent;
    protected ApplicationComponent applicationComponent;
    protected Bus eventBus;
    private MenuItem activeConnection;
    protected WifiReceiver wifiReceiver;
    protected NetworkManager networkManager;
    protected Activity currentActivity;
    protected Context currentContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationComponent = ((RedditApp)getApplication()).getAppComponent();
        eventBus = applicationComponent.getEventBus();
        wifiReceiver = applicationComponent.getWifiReceiver();
        networkManager = applicationComponent.getNetworkManager();
        getActivityComponent();
        setContentView(getLayoutResourceId());
    }

    protected int getLayoutResourceId() {
        return R.layout.base_activity;
    }

    public void addFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public Fragment getFragmentFromActivity(String fragTag){
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag(fragTag);
        if (fragment != null && fragment.isVisible()) {
            return fragment;
        }
        return null;
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(applicationComponent).build();

            currentActivity = activityComponent.getActivity();
            currentContext = activityComponent.getContext();
        }
        return activityComponent;
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
        IntentFilter intentFilter = new IntentFilter(WIFI_RECEIVER_ACTION);
        registerReceiver(wifiReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
        unregisterReceiver(wifiReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_converter, menu);
        activeConnection = menu.findItem(R.id.active_connection);
        updateActiveConnectionIcon(networkManager.isOnline());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.active_connection:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateActiveConnectionIcon(boolean isOnline){
        if (activeConnection != null)
            activeConnection.setIcon((isOnline) ? R.mipmap.ic_call_white_48dp : R.mipmap.ic_call_end_white_48dp);
    }

    protected int pushFragment(int containerId, Fragment fragment, String tag, boolean addToBackStack,
            CustomAnimations animation){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (animation != null) {
            transaction.setCustomAnimations(animation.enter, animation.exit, animation.popEnter, animation.popExit);
        }
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        // use commitAllowingStateLoss per Support Library bug: https://code.google.com/p/android/issues/detail?id=19917
        // (TODO once I get rid of support lib I can remove this)
        return transaction.replace(containerId, fragment, tag).commitAllowingStateLoss();
    }

    /**
     * The custom transition animation configuration class.
     */
    public static class CustomAnimations implements Serializable {
        private static final long serialVersionUID = 1L;
        public final int enter;
        public final int exit;
        public final int popEnter;
        public final int popExit;

        /**
         * The {@link CustomAnimations} constructor.
         *
         * @param enter    the enter animation.
         * @param exit     the exit animation.
         * @param popEnter the pop backstack enter animation (only used for fragment transactions).
         * @param popExit  the pop backstack exit animation (only used for fragment transactions).
         */
        public CustomAnimations(int enter, int exit, int popEnter, int popExit) {
            this.enter = enter;
            this.exit = exit;
            this.popEnter = popEnter;
            this.popExit = popExit;
        }

        /**
         * The {@link CustomAnimations} constructor.
         *
         * @param enter A resource ID of the animation resource to use for
         *              the incoming view.  Use 0 for no animation.
         * @param exit  A resource ID of the animation resource to use for
         *              the outgoing view.  Use 0 for no animation.
         */
        public CustomAnimations(int enter, int exit) {
            this(enter, exit, 0, 0);
        }
    }
}
