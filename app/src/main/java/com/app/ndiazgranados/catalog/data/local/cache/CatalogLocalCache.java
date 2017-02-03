package com.app.ndiazgranados.catalog.data.local.cache;

import com.app.ndiazgranados.catalog.model.web.Catalog;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class CatalogLocalCache {

    private Catalog catalog;

    @Inject
    public CatalogLocalCache() {
    }

    public void saveToCache(Catalog cacheObject) {
        catalog = cacheObject;
    }

    public Catalog getCatalogCache() {
        return catalog;
    }

}
