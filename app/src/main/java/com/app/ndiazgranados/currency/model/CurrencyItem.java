package com.app.ndiazgranados.currency.model;

/**
 * Created by user on 09/04/2016.
 */
public class CurrencyItem {

    private String acronym;
    private String rate;
    private String value;

    public CurrencyItem (String acronym, String rate, String value){
        this.acronym = acronym;
        this.rate = rate;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
