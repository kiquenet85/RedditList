package com.app.ndiazgranados.currency.data.local;

import com.app.ndiazgranados.currency.data.api.ConverterApiInterface;
import com.app.ndiazgranados.currency.model.web.Currency;

import retrofit2.Call;

/**
 * Created by user on 08/04/2016.
 */
public class ConverterApiInterfaceLocalImpl implements ConverterApiInterface {
    @Override
    public Call<Currency> fetchAllCurrencies(int value, String selectedCurrency) {
        return null;
    }
}
