package com.app.ndiazgranados.reddit.di.module;

import com.app.ndiazgranados.reddit.data.local.cache.RedditLocalCache;
import com.app.ndiazgranados.reddit.data.local.cache.SubjectLocalCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author n.diazgranados
 */
@Module
public class CacheModule {

    @Provides
    @Singleton
    RedditLocalCache provideRedditCache() {
        return new RedditLocalCache();
    }

    @Provides
    @Singleton
    SubjectLocalCache provideSubjectCache() {
        return new SubjectLocalCache();
    }
}
