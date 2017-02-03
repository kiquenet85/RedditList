package com.app.ndiazgranados.catalog.data.local.cache;

import android.os.Bundle;

import com.app.ndiazgranados.catalog.model.web.Catalog;
import com.app.ndiazgranados.catalog.model.web.Category;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class CategoryLocalCache extends ActivityLocalCache<Category> {

    public static final String CACHE_SELECTED_CATEGORY_ITEM_KEY = "CACHE_SELECTED_CATEGORY_ITEM_KEY";
    private Catalog catalog;

    @Inject
    public CategoryLocalCache() {
    }

    @Override
    public void saveToCache(Category cacheObject, Bundle saveInstanceState) {
        Long id = cacheId.incrementAndGet();
        cacheMap.put(id, cacheObject);
        saveInstanceState.putSerializable(CACHE_SELECTED_CATEGORY_ITEM_KEY, id);
    }

    @Override
    public Category searchInCache(Long cacheId) {
        return cacheMap.get(cacheId);
    }
}
