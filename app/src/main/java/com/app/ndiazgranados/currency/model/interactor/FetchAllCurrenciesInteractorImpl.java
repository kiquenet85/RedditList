package com.app.ndiazgranados.currency.model.interactor;

import com.app.ndiazgranados.currency.data.local.ConverterApiInterfaceLocalImpl;
import com.app.ndiazgranados.currency.data.remote.ConverterApiInterfaceRemImpl;
import com.app.ndiazgranados.currency.network.NetworkManager;

import javax.inject.Inject;

/**
 * Created by user on 08/04/2016.
 */
public class FetchAllCurrenciesInteractorImpl implements FetchAllCurrenciesInteractor {

    private ConverterApiInterfaceRemImpl converterApiInterfaceRem;
    private ConverterApiInterfaceLocalImpl converterApiInterfaceLocal;
    private NetworkManager networkManager;

    @Inject
    public FetchAllCurrenciesInteractorImpl(ConverterApiInterfaceRemImpl converterApiInterfaceRem,
                                            ConverterApiInterfaceLocalImpl converterApiInterfaceLocal,
                                            NetworkManager networkManager){
        this.converterApiInterfaceLocal = converterApiInterfaceLocal;
        this.converterApiInterfaceRem = converterApiInterfaceRem;
        this.networkManager = networkManager;
    }

    @Override
    public void execute(Object... params) {
        if (networkManager.isOnline()) {
            converterApiInterfaceRem.fetchAllCurrencies((Integer) params[0], (String) params[1]);
        }else {
            converterApiInterfaceLocal.fetchAllCurrencies((Integer) params[0], (String) params[1]);
        }
    }
}
