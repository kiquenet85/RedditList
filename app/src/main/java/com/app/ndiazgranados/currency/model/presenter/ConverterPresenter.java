package com.app.ndiazgranados.currency.model.presenter;

import android.os.Bundle;

import com.app.ndiazgranados.currency.data.local.cache.ConverterLocalCache;
import com.app.ndiazgranados.currency.model.web.Currency;
import com.app.ndiazgranados.currency.model.CurrencyItem;
import com.app.ndiazgranados.currency.model.web.Rate;
import com.app.ndiazgranados.currency.model.interactor.FetchAllCurrenciesInteractor;
import com.app.ndiazgranados.currency.ui.view.ConverterView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 08/04/2016.
 */
public class ConverterPresenter extends BasePresenter<ConverterView> {

    private FetchAllCurrenciesInteractor fetchAllCurrenciesInteractor;
    private ConverterLocalCache converterLocalCache;

    @Inject
    public ConverterPresenter(FetchAllCurrenciesInteractor fetchAllCurrenciesInteractor, ConverterLocalCache converterLocalCache) {
        this.fetchAllCurrenciesInteractor = fetchAllCurrenciesInteractor;
        this.converterLocalCache = converterLocalCache;
    }

    @Override
    public void attachView(ConverterView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void checkCache(Long cacheId) {
        if (cacheId != null) {
            getMvpView().showAllCurrencies(converterLocalCache.searchInCache(cacheId), converterLocalCache.getLastUsdValue(), converterLocalCache.getSelectedCurrency());
        }
    }

    public void saveCache(List<CurrencyItem> currencyItems, Bundle outState) {
        converterLocalCache.saveToCache(currencyItems, outState);
    }

    public void loadCurrencies(int value, Long cacheId, String selectedCurrency) {
        checkViewAttached();
        if (cacheId == null || !isAskingForSameValues(value, selectedCurrency)) {
            fetchAllCurrenciesInteractor.execute(value, selectedCurrency);
        } else if (converterLocalCache.searchInCache(cacheId) != null) {
            getMvpView().showAllCurrencies(converterLocalCache.searchInCache(cacheId), converterLocalCache.getLastUsdValue(), converterLocalCache.getSelectedCurrency());
        }
        converterLocalCache.setLastUsdValue(value);
        converterLocalCache.setSelectedCurrency(selectedCurrency);
    }

    private boolean isAskingForSameValues(int value, String selectedCurrency) {
        return (converterLocalCache.getLastUsdValue() == value) && (converterLocalCache.getSelectedCurrency() == selectedCurrency);
    }

    @Subscribe
    public void onloadCurrencies(FetchAllCurrenciesInteractor.FetchAllCurrenciesEvent event) {
        List<CurrencyItem> currencyItems = new ArrayList<>();
        if (event.getResponse() != null) {
            getMvpView().showAllCurrencies(buildCurrencyItems(event.getResponse().body(), event.getValue()), event.getValue(), event.getSelectedCurrency());
        } else {
            getMvpView().showCurrenciesEmpty(currencyItems);
        }
    }

    private List<CurrencyItem> buildCurrencyItems(Currency currency, int value) {
        List<CurrencyItem> currencyItems = new ArrayList<>();
        Rate rates = currency.getRates();

        //ADDING HEADER
        currencyItems.add(new CurrencyItem("CURRENCY", "RATE", "VALUE"));
        if (rates.getBRL() != null)
            currencyItems.add(new CurrencyItem(Rate.BRL_CURRENCY, rates.getBRL().toString(), (value * rates.getBRL()) + ""));
        if (rates.getEUR() != null)
            currencyItems.add(new CurrencyItem(Rate.EUR_CURRENCY, rates.getEUR().toString(), (value * rates.getEUR()) + ""));
        if (rates.getGBP() != null)
            currencyItems.add(new CurrencyItem(Rate.GBP_CURRENCY, rates.getGBP().toString(), (value * rates.getGBP()) + ""));
        if (rates.getJPY() != null)
            currencyItems.add(new CurrencyItem(Rate.JPY_CURRENCY, rates.getJPY().toString(), (value * rates.getJPY()) + ""));
        if (rates.getUSD() != null)
            currencyItems.add(new CurrencyItem(Rate.USD_CURRENCY, rates.getUSD().toString(), (value * rates.getUSD()) + ""));

        return currencyItems;
    }
}
