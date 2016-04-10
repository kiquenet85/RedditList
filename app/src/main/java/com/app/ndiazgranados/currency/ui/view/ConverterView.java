package com.app.ndiazgranados.currency.ui.view;

import com.app.ndiazgranados.currency.model.CurrencyItem;

import java.util.List;

/**
 * Created by user on 08/04/2016.
 */
public interface ConverterView extends MvpView {
    void showAllCurrencies(List<CurrencyItem> currency, int usdValue, String currencyKey);
    void showCurrenciesEmpty(List<CurrencyItem> currency);
}
