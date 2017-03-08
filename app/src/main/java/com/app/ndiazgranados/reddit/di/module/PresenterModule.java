package com.app.ndiazgranados.reddit.di.module;

import com.app.ndiazgranados.reddit.data.local.cache.DetailSubjectLocalCache;
import com.app.ndiazgranados.reddit.data.local.cache.RedditLocalCache;
import com.app.ndiazgranados.reddit.data.local.cache.SubjectLocalCache;
import com.app.ndiazgranados.reddit.network.NetworkManager;
import com.app.ndiazgranados.reddit.ui.interactor.FetchRedditListInteractor;
import com.app.ndiazgranados.reddit.ui.presenter.DetailSubjectPresenter;
import com.app.ndiazgranados.reddit.ui.presenter.SubjectPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author n.diazgranados
 */
@Module
public class PresenterModule {

    @Provides
    @Singleton
    SubjectPresenter provideSubjectPresenter(FetchRedditListInteractor fetchRedditListInteractor, SubjectLocalCache subjectLocalCache,
            RedditLocalCache redditLocalCache,
            NetworkManager networkManager) {
        SubjectPresenter subjectPresenter = new SubjectPresenter(fetchRedditListInteractor, subjectLocalCache, redditLocalCache, networkManager);
        return subjectPresenter;
    }

    @Provides
    @Singleton
    DetailSubjectPresenter provideDetailSubjectPresenter(DetailSubjectLocalCache detailSubjectLocalCache, RedditLocalCache redditLocalCache) {
        DetailSubjectPresenter detailSubjectPresenter = new DetailSubjectPresenter(detailSubjectLocalCache, redditLocalCache);
        return detailSubjectPresenter;
    }
}
