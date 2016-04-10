package com.app.ndiazgranados.currency.ui.activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.app.ndiazgranados.currency.CurrencyApp;
import com.app.ndiazgranados.currency.R;
import com.app.ndiazgranados.currency.di.component.ActivityComponent;
import com.app.ndiazgranados.currency.di.component.ApplicationComponent;
import com.app.ndiazgranados.currency.di.component.DaggerActivityComponent;
import com.app.ndiazgranados.currency.di.module.ActivityModule;
import com.app.ndiazgranados.currency.di.module.CacheModule;
import com.app.ndiazgranados.currency.network.NetworkManager;
import com.app.ndiazgranados.currency.network.WifiReceiver;
import com.squareup.otto.Bus;

/**
 * Created by user on 08/04/2016.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String WIFI_RECEIVER_ACTION= "android.net.conn.CONNECTIVITY_CHANGE";
    protected ActivityComponent activityComponent;
    protected ApplicationComponent applicationComponent;
    protected Bus eventBus;
    private MenuItem activeConnection;
    protected WifiReceiver wifiReceiver;
    protected NetworkManager networkManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationComponent = ((CurrencyApp)getApplication()).getAppComponent();
        eventBus = applicationComponent.getEventBus();
        wifiReceiver = applicationComponent.getWifiReceiver();
        networkManager = applicationComponent.getNetworkManager();
        getActivityComponent().inject(this);
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this))
                    .cacheModule (new CacheModule())
                    .applicationComponent(applicationComponent).build();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
