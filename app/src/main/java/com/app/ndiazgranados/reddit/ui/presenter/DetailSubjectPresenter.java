package com.app.ndiazgranados.reddit.ui.presenter;

import android.os.Bundle;

import com.app.ndiazgranados.reddit.data.local.cache.DetailSubjectLocalCache;
import com.app.ndiazgranados.reddit.data.local.cache.RedditLocalCache;
import com.app.ndiazgranados.reddit.model.web.Child;
import com.app.ndiazgranados.reddit.ui.view.DetailSubjectView;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class DetailSubjectPresenter extends BasePresenter<DetailSubjectView> {

    private DetailSubjectLocalCache detailSubjectLocalCache;
    private RedditLocalCache redditLocalCache;

    @Inject
    public DetailSubjectPresenter(DetailSubjectLocalCache detailSubjectLocalCache, RedditLocalCache redditLocalCache) {
        this.detailSubjectLocalCache = detailSubjectLocalCache;
        this.redditLocalCache = redditLocalCache;
    }

    @Override
    public void attachView(DetailSubjectView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadSelectedSubject(String nameSelectedApp) {
        checkViewAttached();
        getMvpView().showDetailSubject(extractDetailInfoFromSelectedDubject(nameSelectedApp));
    }

    public Child extractDetailInfoFromSelectedDubject(String nameSelectedApp) {
        for (Child entry : redditLocalCache.getRedditCache().getData().getChildren()) {
            if (entry.getData().getTitle().equals(nameSelectedApp)) {
                return entry;
            }
        }
        return null;
    }

    public void saveToCache(String selectedApp, Bundle outState) {
        detailSubjectLocalCache.saveToCache(selectedApp, outState);
    }

    public String searchInCache (Long cacheId) {
        return detailSubjectLocalCache.searchInCache(cacheId);
    }
}
