package com.app.ndiazgranados.currency.data.local.cache;

import android.os.Bundle;

import com.app.ndiazgranados.currency.model.CurrencyItem;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 09/04/2016.
 */
public class ConverterLocalCache extends ActivityLocalCache<List<CurrencyItem>> {

    public static final String CACHE_CURRENCY_ITEMS_KEY = "CACHE_CURRENCY_ITEMS_KEY";

    public int lastUsdValue;
    public String selectedCurrency;

    @Inject
    public ConverterLocalCache() {
    }

    @Override
    public void saveToCache(List<CurrencyItem> cacheObject, Bundle saveInstanceState) {
        Long id = cacheId.incrementAndGet();
        cacheMap.put(id, cacheObject);
        saveInstanceState.putSerializable(CACHE_CURRENCY_ITEMS_KEY, id);
    }

    @Override
    public List<CurrencyItem> searchInCache(Long cacheId) {
        return cacheMap.get(cacheId);
    }

    public int getLastUsdValue() {
        return lastUsdValue;
    }

    public void setLastUsdValue(int lastUsdValue) {
        this.lastUsdValue = lastUsdValue;
    }

    public String getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

}
