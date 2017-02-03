package com.app.ndiazgranados.catalog.ui.presenter;

import android.os.Bundle;

import com.app.ndiazgranados.catalog.data.local.cache.CatalogLocalCache;
import com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache;
import com.app.ndiazgranados.catalog.model.web.Catalog;
import com.app.ndiazgranados.catalog.model.web.Category;
import com.app.ndiazgranados.catalog.model.web.Entry;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractor;
import com.app.ndiazgranados.catalog.ui.view.CatalogView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class CategoryPresenter extends BasePresenter<CatalogView> {

    public static final int MIN_LIMIT_NUMBER_OF_TOP_APPS = 10;
    private FetchCatalogInteractor fetchCatalogInteractor;
    private CategoryLocalCache categoryLocalCache;
    private CatalogLocalCache catalogLocalCache;
    private Bus eventBus;

    @Inject
    public CategoryPresenter(FetchCatalogInteractor fetchCatalogInteractor,
            CategoryLocalCache categoryLocalCache, CatalogLocalCache catalogLocalCache,
            Bus eventBus) {
        this.fetchCatalogInteractor = fetchCatalogInteractor;
        this.categoryLocalCache = categoryLocalCache;
        this.catalogLocalCache = catalogLocalCache;
        this.eventBus = eventBus;
    }

    @Override
    public void attachView(CatalogView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadCategories(int limitNumberOfTopApps) {
        //showLoader();
        checkViewAttached();
        if (catalogLocalCache.getCatalogCache() == null) {
            if (limitNumberOfTopApps > MIN_LIMIT_NUMBER_OF_TOP_APPS) {
                fetchCatalogInteractor.setLimitNumberOftopApps(limitNumberOfTopApps);
            }
            fetchCatalogInteractor.execute();
        } else {
            getMvpView().showCatalog(extractCategoriesFromCatalog(catalogLocalCache.getCatalogCache()));
           // hideLoader();
        }
    }

    @Subscribe
    public void onLoadCatalog(FetchCatalogInteractor.FetchCatalogEvent event) {
      //  hideLoader();
        if (event.isSuccessful()) {
            Catalog catalog = event.getResponse().body();
            getMvpView().showCatalog(extractCategoriesFromCatalog(catalog));
        } else {
            getMvpView().showCatalogEmpty();
        }
    }

    public List<Category> extractCategoriesFromCatalog(Catalog catalog) {
        List<Category> categoryList = new ArrayList<>();
        Set<Category> setCategories = new HashSet<>();
        List<Entry> entryList = catalog.getFeed().getEntry();
        for (Entry entry : entryList) {
            setCategories.add(entry.getCategory());
        }
        categoryList.addAll(setCategories);
        return categoryList;
    }

    public void saveToCache(Category selectedCategory, Bundle outState) {
        categoryLocalCache.saveToCache(selectedCategory, outState);
    }

    @Override
    public void showLoader() {
        getMvpView().showLoader();
    }

    @Override
    public void hideLoader() {
        getMvpView().hideLoader();
    }
}
