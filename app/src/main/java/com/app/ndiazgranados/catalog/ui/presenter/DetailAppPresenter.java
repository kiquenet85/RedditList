package com.app.ndiazgranados.catalog.ui.presenter;

import android.os.Bundle;

import com.app.ndiazgranados.catalog.data.local.cache.CatalogLocalCache;
import com.app.ndiazgranados.catalog.data.local.cache.DetailAppLocalCache;
import com.app.ndiazgranados.catalog.model.web.Entry;
import com.app.ndiazgranados.catalog.ui.view.DetailAppView;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class DetailAppPresenter extends BasePresenter<DetailAppView> {

    private DetailAppLocalCache detailAppLocalCache;
    private CatalogLocalCache catalogLocalCache;

    @Inject
    public DetailAppPresenter(DetailAppLocalCache detailAppLocalCache, CatalogLocalCache catalogLocalCache) {
        this.detailAppLocalCache = detailAppLocalCache;
        this.catalogLocalCache = catalogLocalCache;
    }

    @Override
    public void attachView(DetailAppView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadSelectedApp(String nameSelectedApp) {
        checkViewAttached();
        getMvpView().showDetailApp(extractDetailInfoFromSelectedApp(nameSelectedApp));
    }

    public Entry extractDetailInfoFromSelectedApp (String nameSelectedApp) {
        for (Entry entry : catalogLocalCache.getCatalogCache().getFeed().getEntry()) {
            if (entry.getImName().getLabel().equals(nameSelectedApp)) {
                return entry;
            }
        }
        return null;
    }

    public void saveToCache(String selectedApp, Bundle outState) {
        detailAppLocalCache.saveToCache(selectedApp, outState);
    }

    public String searchInCache (Long cacheId) {
        return detailAppLocalCache.searchInCache(cacheId);
    }
}
