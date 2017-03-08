package com.app.ndiazgranados.reddit.ui.presenter;

import com.app.ndiazgranados.reddit.ui.view.MvpView;

/**
 * @author n.diazgranados
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

    void showLoader();

    void hideLoader();
}
