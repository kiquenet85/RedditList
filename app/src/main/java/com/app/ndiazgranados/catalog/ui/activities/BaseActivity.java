package com.app.ndiazgranados.catalog.ui.activities;

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

import com.app.ndiazgranados.catalog.CatalogApp;
import com.app.ndiazgranados.catalog.R;
import com.app.ndiazgranados.catalog.di.component.ActivityComponent;
import com.app.ndiazgranados.catalog.di.component.ApplicationComponent;
import com.app.ndiazgranados.catalog.di.component.DaggerActivityComponent;
import com.app.ndiazgranados.catalog.di.module.Activity.ActivityModule;
import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.network.WifiReceiver;
import com.squareup.otto.Bus;

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
        applicationComponent = ((CatalogApp)getApplication()).getAppComponent();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.active_connection) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateActiveConnectionIcon(boolean isOnline){
        if (activeConnection != null)
            activeConnection.setIcon((isOnline) ? R.mipmap.ic_call_white_48dp : R.mipmap.ic_call_end_white_48dp);
    }
}
