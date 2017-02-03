package com.app.ndiazgranados.catalog.ui.presenter;

import com.app.ndiazgranados.catalog.ui.view.MvpView;

/**
 * @author n.diazgranados
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

    void showLoader();

    void hideLoader();
}
