package com.app.ndiazgranados.catalog.ui.presenter;

import android.os.Bundle;

import com.app.ndiazgranados.catalog.data.local.cache.CatalogLocalCache;
import com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache;
import com.app.ndiazgranados.catalog.model.web.Entry;
import com.app.ndiazgranados.catalog.ui.view.TopAppsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class TopAppsPresenter extends BasePresenter<TopAppsView> {

    private CategoryLocalCache categoryLocalCache;
    private CatalogLocalCache catalogLocalCache;

    @Inject
    public TopAppsPresenter(CategoryLocalCache categoryLocalCache, CatalogLocalCache catalogLocalCache) {
        this.categoryLocalCache = categoryLocalCache;
        this.catalogLocalCache = catalogLocalCache;
    }

    @Override
    public void attachView(TopAppsView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadTopApps(String selectedCategory) {
        checkViewAttached();
        getMvpView().showTopApps(extractAppsFromSelectedCategory(selectedCategory));
    }

    public List<Entry> extractAppsFromSelectedCategory(String selectedCategory) {
        List<Entry> listEntry = new ArrayList<>();
        for (Entry entry : catalogLocalCache.getCatalogCache().getFeed().getEntry()) {
            if (entry.getCategory().getAttributes().getTerm().equals(selectedCategory)) {
                listEntry.add(entry);
            }
        }
        return listEntry;
    }

    public void saveToCache(String selectedCategory, Bundle outState) {
        categoryLocalCache.saveToCache(selectedCategory, outState);
    }

    public String searchInCache (Long cacheId) {
        return categoryLocalCache.searchInCache(cacheId);
    }
}
