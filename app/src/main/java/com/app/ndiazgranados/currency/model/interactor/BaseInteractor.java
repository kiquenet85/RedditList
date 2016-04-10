package com.app.ndiazgranados.currency.model.interactor;

/**
 * Created by user on 04/04/2016.
 * Base class used for interactors.
 */
public interface BaseInteractor {

    /**
     * Method to execute interactor in a background thread
     * @param params
     * @return
     */
    void execute(Object... params);
}
