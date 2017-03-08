package com.app.ndiazgranados.reddit.ui.presenter;

import android.os.Bundle;

import com.app.ndiazgranados.reddit.data.local.cache.RedditLocalCache;
import com.app.ndiazgranados.reddit.data.local.cache.SubjectLocalCache;
import com.app.ndiazgranados.reddit.model.web.Child;
import com.app.ndiazgranados.reddit.model.web.RedditList;
import com.app.ndiazgranados.reddit.network.NetworkManager;
import com.app.ndiazgranados.reddit.ui.interactor.FetchRedditListInteractor;
import com.app.ndiazgranados.reddit.ui.view.SubjectView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author n.diazgranados
 */
public class SubjectPresenter extends BasePresenter<SubjectView> {

    public static final int MIN_LIMIT_NUMBER_OF_TOP_APPS = 10;
    private FetchRedditListInteractor fetchRedditListInteractor;
    private SubjectLocalCache subjectLocalCache;
    private RedditLocalCache redditLocalCache;
    private NetworkManager networkManager;

    @Inject
    public SubjectPresenter(FetchRedditListInteractor fetchRedditListInteractor,
            SubjectLocalCache subjectLocalCache, RedditLocalCache redditLocalCache,
            NetworkManager networkManager) {
        this.fetchRedditListInteractor = fetchRedditListInteractor;
        this.subjectLocalCache = subjectLocalCache;
        this.redditLocalCache = redditLocalCache;
        this.networkManager = networkManager;
    }

    @Override
    public void attachView(SubjectView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadSubjects(int limitNumberOfTopApps) {
        //showLoader();
        checkViewAttached();
        if (redditLocalCache.getRedditCache() != null) {
            getMvpView().showRedditList(extractSubjectsFromRedditList(redditLocalCache.getRedditCache()));
        } else {
            if (networkManager.isOnline()) {
                fetchRedditListInteractor.execute();
            } else {
                getMvpView().showRedditListEmpty();
            }
        }
    }

    @Subscribe
    public void onLoadSubjects(FetchRedditListInteractor.FetchRedditListEvent event) {
        //  hideLoader();
        if (event.isSuccessful()) {
            RedditList redditList = event.getResponse().body();
            redditLocalCache.saveToCache(redditList);
            getMvpView().showRedditList(extractSubjectsFromRedditList(redditList));
        } else {
            getMvpView().showRedditListEmpty();
        }
    }

    public List<Child> extractSubjectsFromRedditList(RedditList redditList) {
        List<Child> categoryList = new ArrayList<>();
        return redditList.getData().getChildren();
    }

    public void saveToCache(String selectedCategory, Bundle outState) {
        subjectLocalCache.saveToCache(selectedCategory, outState);
    }

    public String searchInCache(Long cacheId) {
        return subjectLocalCache.searchInCache(cacheId);
    }

    @Override
    public void showLoader() {
        getMvpView().showLoader();
    }

    @Override
    public void hideLoader() {
        getMvpView().hideLoader();
    }
}
