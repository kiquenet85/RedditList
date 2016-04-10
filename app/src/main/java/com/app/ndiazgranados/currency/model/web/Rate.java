package com.app.ndiazgranados.currency.model.web;

/**
 * Created by user on 08/04/2016.
 */
public class Rate {

    public static final String BRL_CURRENCY = "BRL";
    public static final String GBP_CURRENCY = "GBP";
    public static final String JPY_CURRENCY = "JPY";
    public static final String EUR_CURRENCY = "EUR";
    public static final String USD_CURRENCY = "USD";

    private Float BRL;
    private Float GBP;
    private Float JPY;
    private Float EUR;
    private Float USD;

    /**
     * No args constructor for use in serialization
     */
    public Rate() {
    }

    /**
     * @param JPY
     * @param BRL
     * @param EUR
     * @param GBP
     */
    public Rate(Float BRL, Float GBP, Float JPY, Float EUR, Float USD) {
        this.BRL = BRL;
        this.GBP = GBP;
        this.JPY = JPY;
        this.EUR = EUR;
        this.USD = USD;
    }

    /**
     * @return The BRL
     */
    public Float getBRL() {
        return BRL;
    }

    /**
     * @param BRL The BRL
     */
    public void setBRL(Float BRL) {
        this.BRL = BRL;
    }

    /**
     * @return The GBP
     */
    public Float getGBP() {
        return GBP;
    }

    /**
     * @param GBP The GBP
     */
    public void setGBP(Float GBP) {
        this.GBP = GBP;
    }

    /**
     * @return The JPY
     */
    public Float getJPY() {
        return JPY;
    }

    /**
     * @param JPY The JPY
     */
    public void setJPY(Float JPY) {
        this.JPY = JPY;
    }

    /**
     * @return The EUR
     */
    public Float getEUR() {
        return EUR;
    }

    /**
     * @param EUR The EUR
     */
    public void setEUR(Float EUR) {
        this.EUR = EUR;
    }

    /**
     * @return The USD
     */
    public Float getUSD() {
        return USD;
    }

    /**
     * @param USD The USD
     */
    public void setUSD(Float USD) {
        this.USD = USD;
    }

}
