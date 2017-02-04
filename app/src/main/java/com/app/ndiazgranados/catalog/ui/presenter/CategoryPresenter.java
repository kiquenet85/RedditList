package com.app.ndiazgranados.catalog.ui.presenter;

import android.os.Bundle;

import com.app.ndiazgranados.catalog.data.local.cache.CatalogLocalCache;
import com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache;
import com.app.ndiazgranados.catalog.model.web.Catalog;
import com.app.ndiazgranados.catalog.model.web.Category;
import com.app.ndiazgranados.catalog.model.web.Entry;
import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.ui.interactor.FetchCatalogInteractor;
import com.app.ndiazgranados.catalog.ui.view.CatalogView;
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
    private NetworkManager networkManager;

    @Inject
    public CategoryPresenter(FetchCatalogInteractor fetchCatalogInteractor,
            CategoryLocalCache categoryLocalCache, CatalogLocalCache catalogLocalCache,
            NetworkManager networkManager) {
        this.fetchCatalogInteractor = fetchCatalogInteractor;
        this.categoryLocalCache = categoryLocalCache;
        this.catalogLocalCache = catalogLocalCache;
        this.networkManager = networkManager;
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
        if (catalogLocalCache.getCatalogCache() != null) {
            getMvpView().showCatalog(extractCategoriesFromCatalog(catalogLocalCache.getCatalogCache()));
        } else {
            if (networkManager.isOnline()) {
                if (limitNumberOfTopApps > MIN_LIMIT_NUMBER_OF_TOP_APPS) {
                    fetchCatalogInteractor.setLimitNumberOftopApps(limitNumberOfTopApps);
                }
                fetchCatalogInteractor.execute();
            } else {
                getMvpView().showCatalogEmpty();
            }
        }
    }

    @Subscribe
    public void onLoadCatalog(FetchCatalogInteractor.FetchCatalogEvent event) {
      //  hideLoader();
        if (event.isSuccessful()) {
            Catalog catalog = event.getResponse().body();
            catalogLocalCache.saveToCache(catalog);
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

    public void saveToCache(String selectedCategory, Bundle outState) {
        categoryLocalCache.saveToCache(selectedCategory, outState);
    }

    public String searchInCache (Long cacheId) {
        return categoryLocalCache.searchInCache(cacheId);
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
