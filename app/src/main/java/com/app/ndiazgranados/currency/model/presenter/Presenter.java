package com.app.ndiazgranados.currency.model.presenter;

import com.app.ndiazgranados.currency.ui.view.MvpView;

/**
 * Created by user on 08/04/2016.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
