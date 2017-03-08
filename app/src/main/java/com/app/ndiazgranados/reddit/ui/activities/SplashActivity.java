package com.app.ndiazgranados.reddit.ui.activities;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.app.ndiazgranados.reddit.R;
import com.app.ndiazgranados.reddit.data.local.cache.RedditLocalCache;
import com.app.ndiazgranados.reddit.model.web.RedditList;
import com.app.ndiazgranados.reddit.network.NetworkManager;
import com.app.ndiazgranados.reddit.settings.Settings;
import com.app.ndiazgranados.reddit.ui.interactor.FetchRedditListInteractor;
import com.squareup.otto.Subscribe;

/**
 * @author n.diazgranados
 */
public class SplashActivity extends BaseActivity {

    private FetchRedditListInteractor fetchRedditListInteractor;
    private Settings settings;
    private RedditLocalCache redditLocalCache;
    private NetworkManager networkManager;
    private RedditList redditList;
    private ImageView imageView;
    private Animation animation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchRedditListInteractor = applicationComponent.getCatalogInteractor();
        redditLocalCache = applicationComponent.getRedditLocalCache();
        networkManager = applicationComponent.getNetworkManager();
        settings = applicationComponent.getSettings();

        imageView = (ImageView) findViewById(R.id.splash_image);
        animation = AnimationUtils.loadAnimation(currentContext, R.anim.pull_up_from_bottom);
    }

    @Override
    public void onResume() {
        super.onResume();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (networkManager.isOnline()) {
                    fetchRedditListInteractor.execute();
                } else {
                    startActivity(RedditActivity.createIntent(currentContext, null));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animation);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (redditList != null) {
            redditLocalCache.saveToCache(redditList);
        }
    }

    @Subscribe
    public void onLoadSubjects(FetchRedditListInteractor.FetchRedditListEvent event) {
        if (event.isSuccessful()) {
            redditList = event.getResponse().body();
            redditLocalCache.saveToCache(redditList);
        }
        startActivity(RedditActivity.createIntent(currentContext, (event.isSuccessful()) ? event.getResponse().raw().toString() : null));
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.splash_activity;
    }
}
