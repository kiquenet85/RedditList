package com.app.ndiazgranados.reddit.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class WifiReceiver extends BroadcastReceiver {

    private Bus eventBus;
    private static boolean connectedOnlineStatus;

    @Inject
    public WifiReceiver(Bus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * The Event produced by the interactor
     */
    public class NetworkConnectionChange {
        private boolean isOnline;

        public boolean isOnline() {
            return isOnline;
        }

        public void setOnline(boolean online) {
            isOnline = online;
        }

        public NetworkConnectionChange(){}

    }

    @Override
    @Produce
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        boolean isOnline = (netInfo != null && netInfo.isConnected());

        NetworkConnectionChange networkConnectionChange = new NetworkConnectionChange();
        networkConnectionChange.setOnline(isOnline);
        if (this.connectedOnlineStatus != isOnline) {
            connectedOnlineStatus = isOnline;
            eventBus.post(networkConnectionChange);
        }
    }
}
