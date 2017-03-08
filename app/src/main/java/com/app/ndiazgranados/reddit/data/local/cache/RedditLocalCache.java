package com.app.ndiazgranados.reddit.data.local.cache;

import com.app.ndiazgranados.reddit.model.web.RedditList;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class RedditLocalCache {

    private RedditList redditList;

    @Inject
    public RedditLocalCache() {
    }

    public void saveToCache(RedditList cacheObject) {
        redditList = cacheObject;
    }

    public RedditList getRedditCache() {
        return redditList;
    }

}
