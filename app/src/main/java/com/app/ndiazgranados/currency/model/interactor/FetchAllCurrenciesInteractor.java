package com.app.ndiazgranados.currency.model.interactor;

import com.app.ndiazgranados.currency.model.web.Currency;
import com.app.ndiazgranados.currency.network.ResponseEvent;

/**
 * Created by user on 08/04/2016.
 */
public interface FetchAllCurrenciesInteractor extends BaseInteractor {

    /**
     * The Event produced by the interactor
     */
    class FetchAllCurrenciesEvent extends ResponseEvent<Currency> {
        private int value;

        private String selectedCurrency;

        public void setValue(int usdValue) {
            this.value = usdValue;
        }

        public int getValue() {
            return value;
        }

        public String getSelectedCurrency() {
            return selectedCurrency;
        }

        public void setSelectedCurrency(String selectedCurrency) {
            this.selectedCurrency = selectedCurrency;
        }

    }
}
