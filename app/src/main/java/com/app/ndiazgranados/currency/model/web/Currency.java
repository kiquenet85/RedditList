package com.app.ndiazgranados.currency.model.web;

/**
 * Created by user on 08/04/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency {

    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("rates")
    @Expose
    private Rate rates;

    /**
     * No args constructor for use in serialization
     */
    public Currency() {
    }

    /**
     * @param base
     * @param rates
     * @param date
     */
    public Currency(String base, String date, Rate rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    /**
     * @return The base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The rates
     */
    public Rate getRates() {
        return rates;
    }

    /**
     * @param rates The rates
     */
    public void setRates(Rate rates) {
        this.rates = rates;
    }
}
