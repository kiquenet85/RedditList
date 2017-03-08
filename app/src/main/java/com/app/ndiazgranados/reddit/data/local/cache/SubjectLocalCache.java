package com.app.ndiazgranados.reddit.data.local.cache;

import android.os.Bundle;

import com.app.ndiazgranados.reddit.model.web.Child;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class SubjectLocalCache extends ActivityLocalCache<String> {

    public static final String CACHE_SELECTED_SUBJECT_ITEM_KEY = "CACHE_SELECTED_SUBJECT_ITEM_KEY";
    private Child child;

    @Inject
    public SubjectLocalCache() {
    }

    @Override
    public void saveToCache(String cacheObject, Bundle saveInstanceState) {
        Long id = cacheId.incrementAndGet();
        cacheMap.put(id, cacheObject);
        saveInstanceState.putSerializable(CACHE_SELECTED_SUBJECT_ITEM_KEY, id);
    }

    @Override
    public String searchInCache(Long cacheId) {
        return cacheMap.get(cacheId);
    }
}
