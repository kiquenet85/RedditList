package com.app.ndiazgranados.currency.data.local.cache;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by user on 09/04/2016.
 */
public abstract class ActivityLocalCache<T> {

    //Some read/write
    protected  Map<Long, T> cacheMap = new HashMap<>();
    protected AtomicLong cacheId;

    public ActivityLocalCache() {
        cacheId = new AtomicLong();
    }

    public abstract void saveToCache(T cacheObject, Bundle saveInstanceState);

    public abstract T searchInCache(Long cacheId);
}
