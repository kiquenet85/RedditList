package com.app.ndiazgranados.reddit.data.local.cache;

import android.os.Bundle;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class DetailSubjectLocalCache extends ActivityLocalCache<String> {

    public static final String CACHE_DETAIL_APP_ITEM_KEY = "CACHE_DETAIL_APP_ITEM_KEY";

    @Inject
    public DetailSubjectLocalCache() {
    }

    @Override
    public void saveToCache(String cacheObject, Bundle saveInstanceState) {
        Long id = cacheId.incrementAndGet();
        cacheMap.put(id, cacheObject);
        saveInstanceState.putSerializable(CACHE_DETAIL_APP_ITEM_KEY, id);
    }

    @Override
    public String searchInCache(Long cacheId) {
        return cacheMap.get(cacheId);
    }
}
