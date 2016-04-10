package com.app.ndiazgranados.currency.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.app.ndiazgranados.currency.CurrencyApp;
import com.app.ndiazgranados.currency.settings.Settings;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 08/04/2016.
 */
public class NetworkManager {

    private Retrofit retrofit = null;
    private final Settings settings;
    private final Context contextApp;

    @Inject
    public NetworkManager(Context contextApp, Settings settings) {
        this.settings = settings;
        this.contextApp = contextApp;
        createDefaultRetrofit();
    }

    public void createDefaultRetrofit() {
        if (retrofit == null) {
            CurrencyApp converterApp = (CurrencyApp) contextApp.getApplicationContext();
            System.out.println(settings.getServiceTimeOut() + " " + settings.getBaseUrl());
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(settings.getServiceTimeOut(), TimeUnit.SECONDS)
                    .connectTimeout(settings.getConnectionTimeOut(), TimeUnit.SECONDS)
                    .build();

            // Create a very simple REST adapter which points the getCatalog API.
            retrofit = new Retrofit.Builder()
                    .baseUrl(settings.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) contextApp.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    public Retrofit getDefaultRetrofit() {
        return retrofit;
    }
}
