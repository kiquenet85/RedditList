package com.app.ndiazgranados.currency.data.api;

import com.app.ndiazgranados.currency.model.web.Currency;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by user on 08/04/2016.
 */
public interface ConverterApiInterface {
    @GET("/latest")
    Call<Currency> fetchAllCurrencies(@Header("value") int value, @Query("base") String currencyKey);
}
