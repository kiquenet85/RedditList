package com.app.ndiazgranados.catalog.data.local.cache;

import android.os.Bundle;

import com.app.ndiazgranados.catalog.model.web.Category;

import javax.inject.Inject;

import static com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache.CACHE_SELECTED_CATEGORY_ITEM_KEY;

/**
 * @author n.diazgranados
 */
public class DetailAppLocalCache extends ActivityLocalCache<String> {

    public static final String CACHE_DETAIL_APP_ITEM_KEY = "CACHE_DETAIL_APP_ITEM_KEY";
    private Category category;

    @Inject
    public DetailAppLocalCache() {
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
