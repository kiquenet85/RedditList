package com.app.ndiazgranados.currency.data.remote;

import com.app.ndiazgranados.currency.data.api.ConverterApiInterface;
import com.app.ndiazgranados.currency.model.web.Currency;
import com.app.ndiazgranados.currency.network.NetworkManager;
import com.app.ndiazgranados.currency.model.interactor.FetchAllCurrenciesInteractor;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 08/04/2016.
 */
public class ConverterApiInterfaceRemImpl implements ConverterApiInterface {

    private final NetworkManager networkManager;
    private final Bus eventBus;

    @Inject
    public ConverterApiInterfaceRemImpl(NetworkManager networkManager, Bus eventBus){
        this.networkManager = networkManager;
        this.eventBus = eventBus;
    }

    @Override
    @Produce
    public Call<Currency> fetchAllCurrencies(final int value, final String selectedCurrency) {
        Call<Currency> call = (networkManager.getDefaultRetrofit()).create(ConverterApiInterface.class).fetchAllCurrencies(value,selectedCurrency);
        // Fetch and print a list of the catalogs to the library.
        call.enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                FetchAllCurrenciesInteractor.FetchAllCurrenciesEvent event = new FetchAllCurrenciesInteractor.FetchAllCurrenciesEvent();
                event.setResponse(call,response);
                event.setValue(value);
                event.setSelectedCurrency(selectedCurrency);
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {
                FetchAllCurrenciesInteractor.FetchAllCurrenciesEvent event = new FetchAllCurrenciesInteractor.FetchAllCurrenciesEvent();
                event.setBadResponse(call,t);
                event.setValue(value);
                event.setSelectedCurrency(selectedCurrency);
                eventBus.post(event);
            }
        });
        return call;
    }
}
