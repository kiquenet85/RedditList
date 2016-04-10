package com.app.ndiazgranados.currency.model.presenter;

import com.app.ndiazgranados.currency.ui.view.MvpView;
import com.squareup.otto.Bus;

/**
 * Base class that implements the Presenter interface and provide a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 * Created by user on 08/04/2016.
 */
public class BasePresenter <T extends MvpView> implements Presenter<T> {

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public void registerOnBus(Bus bus){
        bus.register(this);
    }

    public void unregisterOnBus(Bus bus){
        bus.unregister(this);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
