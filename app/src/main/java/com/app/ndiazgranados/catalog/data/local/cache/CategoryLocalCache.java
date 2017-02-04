package com.app.ndiazgranados.catalog.data.local.cache;

import android.os.Bundle;

import com.app.ndiazgranados.catalog.model.web.Category;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class CategoryLocalCache extends ActivityLocalCache<String> {

    public static final String CACHE_SELECTED_CATEGORY_ITEM_KEY = "CACHE_SELECTED_CATEGORY_ITEM_KEY";
    private Category category;

    @Inject
    public CategoryLocalCache() {
    }

    @Override
    public void saveToCache(String cacheObject, Bundle saveInstanceState) {
        Long id = cacheId.incrementAndGet();
        cacheMap.put(id, cacheObject);
        saveInstanceState.putSerializable(CACHE_SELECTED_CATEGORY_ITEM_KEY, id);
    }

    @Override
    public String searchInCache(Long cacheId) {
        return cacheMap.get(cacheId);
    }
}
